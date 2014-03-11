package fuzzer;

import com.gargoylesoftware.htmlunit.*;
import java.net.*;
import java.util.ArrayList;

public class ResponseCodes {
	
	//not sure if this should be looking through the HTML Anchors ur just at urls
	//Feel free to change this obviously
	public void getResponseCode(ArrayList<HtmlAnchor> links){
		for (HtmlAnchor link : links){
			if (getStatusCode(link)!= 200) {
				//Report it?
				//Not exactly sure what he wants us to do when he says "Report it."
			}
		}
	}
	
}