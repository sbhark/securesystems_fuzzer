package fuzzer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class InputDiscover {

	public void discoverInputs(WebClient client, String url) { 
		
		HashMap<String, List<HtmlElement>> urlToInput = new HashMap<String, List<HtmlElement>>();
		List<HtmlElement> inputs = new ArrayList<HtmlElement>();
		
		try { 
			
			HtmlPage page = client.getPage(url);
			List<HtmlElement> elements = page.getTabbableElements();
			
			for (HtmlElement element : elements) { 
				if (element instanceof HtmlInput || element instanceof HtmlTextArea) { 
					inputs.add(element);
				}
			}
			
			urlToInput.put(url, inputs);
			
			System.out.println("----------------------------------------------------------------");
			System.out.println("Inputs Discovered:");
			for (Map.Entry<String, List<HtmlElement>> input : urlToInput.entrySet()) { 
				System.out.println("URL: " + input.getKey());
				for (HtmlElement element : input.getValue()) { 
					System.out.println(" -> Input: " + element.toString());
				}
			}
			
		} catch (Exception excep) { 
			
			System.out.println("Exception found.");
			excep.printStackTrace();
		}
	}
	
	public void discoverCookies(WebClient client, String url) { 
		
		HashSet<Cookie> foundCookies = new HashSet<Cookie>();
		Set<Cookie> cookies = client.getCookieManager().getCookies();
		
		if (cookies != null && cookies.size() > 0) { 
			foundCookies.addAll(cookies); 
		}
		
		System.out.println("--------------------------------");
		System.out.println("Cookies Found");
		for (Cookie cookie : foundCookies) { 
			System.out.println(cookie.toString());
		}
	}
}
