package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import beans.Article;

public class FileManagerServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2395026077664818575L;
	
	Gson gson;
	
	@Override
	public void init() throws ServletException {
		gson = new Gson();
		Map<String, Article> articles = new HashMap<>();
	
		Article article = new Article();
		
		article.setName("Il signore del bosco");
		article.setText("… Non ricordo come tutto è iniziato (oh-oh)\r\n"
				+ "Forse neanche vorrei ricordarlo (no)\r\n"
				+ "Sai, chi mi ha insegnato a parlare\r\n"
				+ "Oggi è l'unico con cui non parlo (pam-pam)\r\n"
				+ "È il prodotto di un habitat\r\n"
				+ "Qui nel posto in cui abito\r\n"
				+ "La realtà è così tragica (gang shit)\r\n"
				+ "Che subiamo il suo fascino (ah)\r\n"
				+ "… Ma più la vita ti lega ad un posto\r\n"
				+ "Più sarà duro da sciogliere il nodo\r\n"
				+ "Sono cresciuto in 'sto bosco\r\n"
				+ "Sì, come un tronco e metto radici ogni giorno\r\n"
				+ "Dove se arrivi dal niente (gang)\r\n"
				+ "Sarò sempre la prova vivente (yeah)\r\n"
				+ "In tutti i paesini del mondo\r\n"
				+ "Sono al mio posto e prego il signore del bosco\r\n"
				+ "… Signore del bosco\r\n"
				+ "Signore del bosco\r\n"
				+ "Signore del bosco\r\n"
				+ "Gang\r\n"
				+ "… Credo solo in me, non vi ascolto più\r\n"
				+ "Fra', che scelta è sе non scegli tu?\r\n"
				+ "Mi ha salvato il rap, non chi sta lassù\r\n"
				+ "Dico grazie a me ripеtendo il loop\r\n"
				+ "… E se te ne vai, te ne vai, te ne vai\r\n"
				+ "È solo per sentirti vivo\r\n"
				+ "Ma non scordi mai, non scordi mai, non scordi mai\r\n"
				+ "Dove sei stato bambino\r\n"
				+ "… Per chi non capisce sé stesso\r\n"
				+ "E sbaglia da una vita (prego)\r\n"
				+ "Per chi lo capisce lo stesso\r\n"
				+ "E non lo giudica mica (frate')\r\n"
				+ "Per chi si sente diverso\r\n"
				+ "Nella sua stessa famiglia\r\n"
				+ "Per chi non capisce più il senso\r\n"
				+ "E lo trova dentro una bottiglia\r\n"
				+ "… Chi, pur di avere uno scopo\r\n"
				+ "È disposto a scalare la cima\r\n"
				+ "Ma si sente perso di nuovo\r\n"
				+ "Proprio quando ci arriva (rah, rah, rah)\r\n"
				+ "Forse chi arriva dal niente\r\n"
				+ "Quel qualcosa lo cercherà sempre\r\n"
				+ "Fino ai confini del mondo\r\n"
				+ "E finché non torno prego il signore del bosco\r\n"
				+ "… Signore del bosco\r\n"
				+ "Signore del bosco\r\n"
				+ "Signore del bosco\r\n"
				+ "Gang\r\n"
				+ "… Credo solo in me, non vi ascolto più\r\n"
				+ "Fra', che scelta è se non scegli tu?\r\n"
				+ "Mi ha salvato il rap, non chi sta lassù\r\n"
				+ "Dico grazie a me ripetendo il loop\r\n"
				+ "… E se te ne vai, te ne vai, te ne vai\r\n"
				+ "È solo per sentirti vivo\r\n"
				+ "Ma non scordi mai, non scordi mai, non scordi mai\r\n"
				+ "Dove sei stato bambino\r\n"
				+ "… Ricordati da dove vieni\r\n"
				+ "Ma vai verso i tuoi desideri\r\n"
				+ "Se ti guardan con tutto quell'odio\r\n"
				+ "Forse sei solo meglio di loro\r\n"
				+ "Non credere a quello che dice\r\n"
				+ "Chi non ti vorrebbe felice\r\n"
				+ "E se il mondo è rotondo e ti muovi\r\n"
				+ "Sei al centro dovunque ti trovi\r\n"
				+ "… Ovunque ti trovi\r\n"
				+ "Gang\r\n"
				+ "… E se te ne vai, te ne vai, te ne vai\r\n"
				+ "È solo per sentirti vivo\r\n"
				+ "Ma non scordi mai, non scordi mai, non scordi mai\r\n"
				+ "Dove sei stato bambino\r\n"
				+ "… Ricordati da dove vieni\r\n"
				+ "Ma vai verso i tuoi desideri\r\n"
				+ "Se ti guardan con tutto quell'odio\r\n"
				+ "Forse sei solo meglio di loro\r\n"
				+ "Non credere a quello che dice\r\n"
				+ "Chi non ti vorrebbe felice\r\n"
				+ "E se il mondo è rotondo e ti muovi\r\n"
				+ "Sei al centro dovunque ti trovi\r\n"
				+ "… Ovunque ti trovi");
		
		articles.put(article.getName(), article);
		getServletContext().setAttribute("articles", articles);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Content-Type", "application/json");
		
		String fname = req.getParameter("fname");
		Article article = null;
		Map<String, Article> articles = (Map<String, Article>) getServletContext().getAttribute("articles");
		
		if(articles.get(fname) == null) {
			article = new Article();
			article.setName(fname);
			article.setText("");
			articles.put(fname, article);
		} else {
			article = articles.get(fname);
		}
		
		HttpSession session = req.getSession();
		article.getUsers().add(session.getId());
		session.setAttribute("article", article);
		
		resp.getWriter().write(gson.toJson(article));
	}
}
