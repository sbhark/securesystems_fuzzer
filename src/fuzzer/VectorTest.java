package fuzzer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

public class VectorTest {

	@SuppressWarnings("unchecked")
	public void attack(WebClient client, String url, ArrayList<String> attackVectors, ArrayList<String> sensitiveLists) { 
		
		List<HtmlInput> inputs; 
		HtmlSubmitInput inputSubmit; 
		
		try { 
			
			HtmlPage page = goToPage(client, url, sensitiveLists);
			
			inputs = (List<HtmlInput>) page.getByXPath("/html/body//form//input[@type='text']");
			inputSubmit = (HtmlSubmitInput) page.getFirstByXPath("/html/body//form//input[@type='submit']");
			
			for (HtmlInput input : inputs) { 
				for(String vector : attackVectors) { 

					input.setValueAttribute(vector);

				}
			}
			
		} catch(Exception excep) { 
			
		}
		//Add timeout catch exception here Adam. 
	}
	
	/** 
	 * Private method to check every page for if sensitive is leaked. 
	 * @param url
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws FailingHttpStatusCodeException 
	 */
	private HtmlPage goToPage(WebClient client, String url, ArrayList<String> sensitiveData) throws FailingHttpStatusCodeException, MalformedURLException, IOException { 
		
		HtmlPage page = client.getPage(url);
		String pageContents = page.getWebResponse().getContentAsString();
		
		for (String sensitive : sensitiveData) { 
			if (pageContents.contains(sensitive)) { 
				System.out.println("----------------------------------------------------------------");
				System.out.println("WARNING :: Sensitive Data found on page" + url);
				System.out.println("Sensitive Data leaked was: " + sensitive);
				System.out.println("----------------------------------------------------------------");
			}
		}
		
		return page;
	}
	
	private void checkSanitize(HtmlInput input, HtmlSubmitInput submitButton) { 

	}
}
