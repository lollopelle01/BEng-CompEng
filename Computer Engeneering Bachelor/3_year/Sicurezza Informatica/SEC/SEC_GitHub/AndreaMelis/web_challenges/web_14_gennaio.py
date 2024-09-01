#!/usr/bin/env python
import BaseHTTPServer, cgi, cStringIO, glob, httplib, json, os, pickle, random, re, SocketServer, sqlite3, string, sys, subprocess, time, traceback, urllib, xml.etree.ElementTree
try:
    import lxml.etree
except ImportError:
    print "[!] Esecuzione avvenuta con successo collegati a http://localhost:8000 \n Se si vuole runnarlo in locale installare 'python-lxml' (e.g. '%s')\n " % ("apt install python-lxml" if not subprocess.mswindows else "https://pypi.python.org/pypi/lxml")

NAME, VERSION, GITHUB, AUTHOR, LICENSE = "Sec Lab Esame 14 Gennaio (2022) < Command Execution Challenge", "0.01", "https://gitlab.com/wild_boar/labsec_course", "@wild_boar", "GPLv3"
LISTEN_ADDRESS, LISTEN_PORT = "0.0.0.0", 8000
HTML_PREFIX, HTML_POSTFIX = "<!DOCTYPE html>\n<html>\n<head>\n<style>a {font-weight: bold; text-decoration: none; visited: blue; color: blue;} ul {display: inline-block;} .disabled {text-decoration: line-through; color: gray} .disabled a {visited: gray; color: gray; pointer-events: none; cursor: default} table {border-collapse: collapse; margin: 12px; border: 2px solid black} th, td {border: 1px solid black; padding: 3px} span {font-size: larger; font-weight: bold}</style>\n<title>%s</title>\n</head>\n<body style='font: 12px monospace'>\n<script>function process(data) {alert(\"Surname(s) from JSON results: \" + Object.keys(data).map(function(k) {return data[k]}));}; var index=document.location.hash.indexOf('lang='); if (index != -1) document.write('<div style=\"position: absolute; top: 5px; right: 5px;\">Chosen language: <b>' + decodeURIComponent(document.location.hash.substring(index + 5)) + '</b></div>');</script>\n" % cgi.escape(NAME), "<div style=\"position: fixed; bottom: 5px; text-align: center; width: 100%%;\">Powered by <a href=\"%s\" style=\"font-weight: bold; text-decoration: none; visited: blue; color: blue\" target=\"_blank\">%s</a> (v<b>%s</b>)</div>\n</body>\n</html>" % (GITHUB, "@wild_boar", VERSION)
USERS_XML = """<?xml version="1.0" encoding="utf-8"?><users><user id="0"><username>admin</username><name>admin</name><surname>admin</surname><password>7en8aiDoh!</password></user></users>"""
CASES = (("Blind SQL Injection (<i>boolean</i>)", "?id=2", "/?id=2%20AND%20SUBSTR((SELECT%20password%20FROM%20users%20WHERE%20name%3D%27admin%27)%2C1%2C1)%3D%277%27\" onclick=\"alert('checking if the first character for admin\\'s password is digit \\'7\\' (true in case of same result(s) as for \\'vulnerable\\')')", "https://owasp.org/www-community/attacks/Blind_SQL_Injection"), ("Blind SQL Injection (<i>time</i>)", "?id=2", "/?id=(SELECT%20(CASE%20WHEN%20(SUBSTR((SELECT%20password%20FROM%20users%20WHERE%20name%3D%27admin%27)%2C2%2C1)%3D%27e%27)%20THEN%20(LIKE(%27ABCDEFG%27%2CUPPER(HEX(RANDOMBLOB(300000000)))))%20ELSE%200%20END))\" onclick=\"alert('checking if the second character for admin\\'s password is letter \\'e\\' (true in case of delayed response)')", "https://owasp.org/www-community/attacks/Blind_SQL_Injection"), ("UNION SQL Injection", "?id=2", "/?id=2%20UNION%20ALL%20SELECT%20NULL%2C%20NULL%2C%20NULL%2C%20(SELECT%20id%7C%7C%27%2C%27%7C%7Cusername%7C%7C%27%2C%27%7C%7Cpassword%20FROM%20users%20WHERE%20username%3D%27admin%27)", "https://owasp.org/www-chapter-belgium/assets/2010/2010-06-16/Advanced_SQL_InjectionV2.pdf"), ("Login Bypass", "/login?username=&amp;password=", "/login?username=admin&amp;password=%27%20OR%20%271%27%20LIKE%20%271", "https://owasp.org/www-community/attacks/SQL_Injection_Bypassing_WAF"),("Server Side Request Forgery", "/?path=", "/?path=http%3A%2F%2F127.0.0.1%3A631" if not subprocess.mswindows else "/?path=%5C%5C127.0.0.1%5CC%24%5CWindows%5Cwin.ini", "http://www.bishopfox.com/blog/2015/04/vulnerable-by-design-understanding-server-side-request-forgery/"), ("Cross Site Request Forgery", "/?comment=", "/?v=%3Cimg%20src%3D%22%2F%3Fcomment%3D%253Cdiv%2520style%253D%2522color%253Ared%253B%2520font-weight%253A%2520bold%2522%253EI%2520quit%2520the%2520job%253C%252Fdiv%253E%22%3E\" onclick=\"alert('please visit \\'vulnerable\\' page to see what this click has caused')", "https://owasp.org/www-community/attacks/csrf"), ("Arbitrary Code Execution", "/?domain=www.google.com", "/?domain=www.google.com%3B%20ifconfig" if not subprocess.mswindows else "/?domain=www.google.com%26%20ipconfig", "https://en.wikipedia.org/wiki/Arbitrary_code_execution"), ("Path Traversal", "/?path=", "/?path=..%2F..%2F..%2F..%2F..%2F..%2Fetc%2Fpasswd" if not subprocess.mswindows else "/?path=..%5C..%5C..%5C..%5C..%5C..%5CWindows%5Cwin.ini", "https://www.owasp.org/index.php/Path_Traversal"))

def init():
    global connection
    connection = sqlite3.connect(":memory:", isolation_level=None, check_same_thread=False)
    cursor = connection.cursor()
    cursor.execute("CREATE TABLE users(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, name TEXT, surname TEXT, password TEXT)")
    cursor.executemany("INSERT INTO users(id, username, name, surname, password) VALUES(NULL, ?, ?, ?, ?)", ((_.findtext("username"), _.findtext("name"), _.findtext("surname"), _.findtext("password")) for _ in xml.etree.ElementTree.fromstring(USERS_XML).findall("user")))
    cursor.execute("CREATE TABLE comments(id INTEGER PRIMARY KEY AUTOINCREMENT, comment TEXT, time TEXT)")

class ReqHandler(BaseHTTPServer.BaseHTTPRequestHandler):
    def do_GET(self):
        path, query = self.path.split('?', 1) if '?' in self.path else (self.path, "")
        code, content, params, cursor = httplib.OK, HTML_PREFIX, dict((match.group("parameter"), urllib.unquote(','.join(re.findall(r"(?:\A|[?&])%s=([^&]+)" % match.group("parameter"), query)))) for match in re.finditer(r"((\A|[?&])(?P<parameter>[\w\[\]]+)=)([^&]+)", query)), connection.cursor()
        try:
            if path == '/':
                if "domain" in params:
                    paths=str(params["domain"])
                    if re.search(";",paths):
                        content = "Abbiamo filtrato il carattere ';'.. try harder!"
                    elif re.search("cat",paths):
                        content = "Abbiamo disabilitato il comando 'cat'.. esistono altri comandi!"                        
                    elif re.search("wd$",paths):
                        content = "Errore! Il path non puo' terminare per \"wd\", cosa puo' usare per scavalcare questo filtro?"
                    else:
                        # print glob.glob(paths)
                        # final = glob.glob(paths)[0]
                        # print final
                        content = subprocess.check_output("nslookup " + params["domain"], shell=True, stderr=subprocess.STDOUT, stdin=subprocess.PIPE)
                elif "redir" in params:
                    content = content.replace("<head>", "<head><meta http-equiv=\"refresh\" content=\"0; url=%s\"/>" % params["redir"])
                if HTML_PREFIX in content and HTML_POSTFIX not in content:
                    content +="<div><span>Web Challenge Esame 14 Gennaio 2022:</span></div>\n"
                    content +="<p> La challenge consiste nello sfruttare una command injection </p>"
                    content +="<p> Attraverso una richiesta GET alla root / con il parametro domain, e' possibile fare la richiesta dns ad un sito. e.g. /?domain=www.ulisse.unibo.it</p>"
                    content +="<p> Scopo dell'esercitazione e' quello di sfruttare la command injection e recuperare/leggere il file /etc/passwd</p>"
                    content +="<p> Sono presenti alcuni filtri sui caratteri che e' possibile utilizzare</p>"
                    content +="<p> Per ogni filtro \"matched\" verra' proposto un piccolo suggerimento</p>"
                    content +="<p> Lo studente dovra' quindi consegnare 3 file </p>"
                    content +="<p> 1) Il file report.txt dove dovra' spiegare in maniera comprensibile il concetto dietro alla vulnerabilita' di questa challenge.</p>"
                    content +="<p> Lo studente dovra' inoltre elencare i vari passaggi e tentativi eseguiti per sfruttare la vulnerabilita', con i ragionamenti effettuati per \"bypassare\" i filtri, incluso ovviamente il payload finale</p>"
                    content +="<p> 2) Lo screenshot dove e' possibile vedere chiaramente la chiamata all'applicazione con il payload e il risultato finale. </p>"
                    content +="<p> 3) Il file etc/passwd </p>"
                    content +="<p> La qualita' del report incidera' sulla valutazione della parte pratica.</p>"
            else:
                code = httplib.NOT_FOUND
        except Exception, ex:
            content = ex.output if isinstance(ex, subprocess.CalledProcessError) else traceback.format_exc()
            code = httplib.INTERNAL_SERVER_ERROR
        finally:
            self.send_response(code)
            self.send_header("Connection", "close")
            self.send_header("X-XSS-Protection", "0")
            self.send_header("Content-Type", "%s%s" % ("text/html" if content.startswith("<!DOCTYPE html>") else "text/plain", "; charset=%s" % params.get("charset", "utf8")))
            self.end_headers()
            self.wfile.write("%s%s" % (content, HTML_POSTFIX if HTML_PREFIX in content and GITHUB not in content else ""))
            self.wfile.flush()
            self.wfile.close()

class ThreadingServer(SocketServer.ThreadingMixIn, BaseHTTPServer.HTTPServer):
    pass

if __name__ == "__main__":
    init()
    print "Web Application eseguita correttamente, collegarsi a http://localhost:8000"
    try:
        ThreadingServer((LISTEN_ADDRESS, LISTEN_PORT), ReqHandler).serve_forever()
    except KeyboardInterrupt:
        os._exit(1)
