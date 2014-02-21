package fuzzer;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

public class Authentication {

	private final String dvwaUser = "admin"; 
	private final String dvwaPass = "password"; 
	
	private final String dvwaLogin = "http://localhost:8888/dvwa/login.php"; 
	private final String bodgeItLogin = "http://localhost:8080/bodgeit/login.jsp"; 
	
	/** 
	 * Log into DVWA
	 */
	public void loginDvwa(WebClient client) { 
		
		try { 
			
			login(client, dvwaLogin, dvwaUser, "username", dvwaPass, "password");
			
		} catch (Exception excep) { 
			
			System.out.println("Exception found.");
			excep.printStackTrace();
		}
	}
	
	/**
	 * Log into Bodge it 
	 */
	public void loginBodgeIt(WebClient client, String user, String password) { 
		
		try { 
			
			login(client, bodgeItLogin, user, "username", password, "password");
			
		} catch (Exception excep) { 
			
			System.out.println("Exception found.");
			excep.printStackTrace();
		}
	}
	
	public void login(WebClient client, String url, String username, 
			String usernameField, String password, String passwordField) { 
		
		try { 
			
			HtmlPage page = client.getPage(url);
			List<HtmlForm> forms = page.getForms();
			
			for(HtmlForm form: forms) { 
				
				form.getInputByName(usernameField).setValueAttribute(username);
				form.getInputByName(passwordField).setValueAttribute(password);
				HtmlSubmitInput loginButton = form.getInputByName("Login");
				String loginSuccessPage = loginButton.click().getWebResponse().getContentAsString();
				
				if (loginSuccessPage.contains("You have logged in")) { 
					System.out.println("");
					System.out.println("Login Success at: " + url);
				} 
			}
			
		} catch (Exception excep) { 
			System.out.println("Exception found");
			excep.printStackTrace();
		}
	}
}