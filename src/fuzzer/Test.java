package fuzzer;

import com.gargoylesoftware.htmlunit.WebClient;

public class Test {

	/** 
	 * Test class, for testing individual classes. 
	 * DO NOT RUN FOR RUNNING FUZZER
	 */
	public static void main(String[] args) { 

		String url = "http://anjitrana.com/";
		
		WebClient webClient = new WebClient();
		webClient.setJavaScriptEnabled(false);
		
		PageDiscover pd = new PageDiscover();
		pd.discoverPages(webClient, url);
		
		webClient.closeAllWindows();
	}
}