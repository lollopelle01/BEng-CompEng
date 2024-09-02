#!/usr/bin/env python
import BaseHTTPServer, cgi, cStringIO, glob, httplib, json, os, pickle, random, re, SocketServer, sqlite3, string, sys, subprocess, time, traceback, urllib, xml.etree.ElementTree
try:
    import lxml.etree
except ImportError:
    print "[!] Esecuzione avvenuta con successo collegati a http://localhost:8000 \n Se si vuole runnarlo in locale installare 'python-lxml' (e.g. '%s')\n " % ("apt install python-lxml" if not subprocess.mswindows else "https://pypi.python.org/pypi/lxml")

NAME, VERSION, GITHUB, AUTHOR, LICENSE = "Sec Lab Esame 25 Giugno (2021) < Path Traversal Challenge", "0.01", "https://gitlab.com/wild_boar/labsec_course", "@wild_boar", "GPLv3"
LISTEN_ADDRESS, LISTEN_PORT = "0.0.0.0", 8000
HTML_PREFIX, HTML_POSTFIX = "<!DOCTYPE html>\n<html>\n<head>\n<style>a {font-weight: bold; text-decoration: none; visited: blue; color: blue;} ul {display: inline-block;} .disabled {text-decoration: line-through; color: gray} .disabled a {visited: gray; color: gray; pointer-events: none; cursor: default} table {border-collapse: collapse; margin: 12px; border: 2px solid black} th, td {border: 1px solid black; padding: 3px} span {font-size: larger; font-weight: bold}</style>\n<title>%s</title>\n</head>\n<body style='font: 12px monospace'>\n<script>function process(data) {alert(\"Surname(s) from JSON results: \" + Object.keys(data).map(function(k) {return data[k]}));}; var index=document.location.hash.indexOf('lang='); if (index != -1) document.write('<div style=\"position: absolute; top: 5px; right: 5px;\">Chosen language: <b>' + decodeURIComponent(document.location.hash.substring(index + 5)) + '</b></div>');</script>\n" % cgi.escape(NAME), "<div style=\"position: fixed; bottom: 5px; text-align: center; width: 100%%;\">Powered by <a href=\"%s\" style=\"font-weight: bold; text-decoration: none; visited: blue; color: blue\" target=\"_blank\">%s</a> (v<b>%s</b>)</div>\n</body>\n</html>" % (GITHUB, "@wild_boar", VERSION)
USERS_XML = """<?xml version="1.0" encoding="utf-8"?><users><user id="0"><username>admin</username><name>admin</name><surname>admin</surname><password>7en8aiDoh!</password></user></users>"""

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
                if "path" in params:
                    paths=str(params["path"])
                    if re.search("^\.\./",paths):
                        content = "Errore! Non e' possibile invocare un path che inizia per ../, e' sicuro sia un path traversal?"
                    elif re.search("^/\.\.",paths):
                        content = "Errore! Non e' possibile invocare un path che iniza per /.., e' sicuro sia un path traversal?"
                    elif re.search("^/[a-zA-Z0-9]+",paths):
                        content = "Errore! Abbiamo disabilitato la possibilita' di invocare direttamente il file /etc/passwd, il parametro path non puo' iniziare con / seguito da un qualsiasi carattere alfanumerico. HINT: / + carattere alfanumerico, cosa rimane fuori?"                        
                    elif re.search("wd$",paths):
                        content = "Errore! Il path non puo' terminare per \"wd\", cosa puo' usare per scavalcare questo +wild+ filtro?"
                    else:
                        print glob.glob(paths)
                        final = glob.glob(paths)[0]
                        print final
                        content = (open(os.path.abspath(final), "rb") if not "://" in final else urllib.urlopen(final)).read()
                elif "redir" in params:
                    content = content.replace("<head>", "<head><meta http-equiv=\"refresh\" content=\"0; url=%s\"/>" % params["redir"])
                if HTML_PREFIX in content and HTML_POSTFIX not in content:
                    content +="<div><span>Web Challenge Esame 25 Giugno 2021:</span></div>\n"
                    content +="<p> La challenge consiste nello sfruttare una LFI (Local File Inclusion) </p>"
                    content +="<p> Attraverso una richiesta GET alla root / con il parametro path, e' possibile aprire un file locale. e.g. /?path=file_locale.txt</p>"
                    content +="<p> Scopo dell'esercitazione e' quello di sfruttare la LFI e recuperare/leggere il file /etc/passwd</p>"
                    content +="<p> Sono presenti alcuni filtri sui caratteri che e' possibile inviare come percorso</p>"
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
    print "%s #v%s\n by: %s\n\n[i] L'esecuzione e' avvenuta con successo puoi collegarti via browser a'%s:%d'..." % (NAME, VERSION, AUTHOR, LISTEN_ADDRESS, LISTEN_PORT)
    try:
        ThreadingServer((LISTEN_ADDRESS, LISTEN_PORT), ReqHandler).serve_forever()
    except KeyboardInterrupt:
        os._exit(1)