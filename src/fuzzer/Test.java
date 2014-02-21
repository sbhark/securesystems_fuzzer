package fuzzer;

import com.gargoylesoftware.htmlunit.WebClient;

public class Test {

	/** 
	 * Test class, for testing individual classes. 
	 * DO NOT RUN FOR RUNNING FUZZER
	 */
	public static void main(String[] args) { 

		String url = "http://localhost:8888/dvwa/";
		
		WebClient client = new WebClient();
		client.setJavaScriptEnabled(false);
		
		Authentication auth = new Authentication();
		auth.loginDvwa(client);
		
		client.closeAllWindows();
	}
}