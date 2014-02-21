package fuzzer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;

public class InputDiscover {

	public void discoverInputs(WebClient client, String url) { 
		
		Map<String, List<HtmlElement>> urlToInput = new HashMap<String, List<HtmlElement>>();
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
			
		} catch (Exception excep) { 
			
			System.out.println("Exception found.");
			excep.printStackTrace();
		}
	}
}
