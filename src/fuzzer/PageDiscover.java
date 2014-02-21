package fuzzer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PageDiscover {

	/**
	 * Method to crawl and discover pages and links. 
	 * @param client - web client to use 
	 * @param url - base url to start crawling from. 
	 */
	public ArrayList<String> discoverPages(WebClient client, String url) { 
		
		//Get base url
		ArrayList<String> completedUrls = new ArrayList<String>();

		String baseUrl = baseUrl(url);
		System.out.println("Getting all links on site: " + baseUrl + "...... Please Wait");

		List<String> urls = new ArrayList<String>(); // Store URL of page and source of page
		HashSet<String> crawlCompletedLinks = new HashSet<String>();
				
		try { 
			
			//Go to base url and get all the links.
			Page page = client.getPage(url);
			
			//Check if page is of xml if so don't do anything. 
			String cType = page.getWebResponse().getContentType();
			
			//Check if it is content type of xml
			if (!cType.contains("xml")) { 
			
				HtmlPage crawlPage = client.getPage(url);
				List<HtmlAnchor> links = crawlPage.getAnchors();
				
				for (HtmlAnchor link : links) { 
					
					String crawledLink = crawlPage.getFullyQualifiedUrl(link.getHrefAttribute()).toString();
					//Check if the link is within the same website (same baseurl). 
					//If so then save the link otherwise don't add it to list of URL to crawl. 
					if (crawledLink.contains("http://" + baseUrl)) { 
						//Add the link to the url list
						urls.add(crawledLink.trim());
					}
				}
			}
			
			//Add base url to completed crawl
			crawlCompletedLinks.add(url);
			
			//Go through the urls hashmap and continue until the map is empty
			while(!urls.isEmpty()) { 
				
				String crawlUrl = urls.remove(0);
				
				//Check if link has already been crawled. 
				if (!crawlCompletedLinks.contains(crawlUrl)) { 
					
					System.out.println(crawlUrl);
					Page crawlPage2 = null;
					try { 
						crawlPage2 = client.getPage(crawlUrl);
					} catch (Exception excep) { 
						continue;
					}
						
					//Check if page is of xml if so don't do anything. 
					String contentType = crawlPage2.getWebResponse().getContentType();
					
					if (!contentType.contains("xml")) { 
						
						HtmlPage crawlHTML = client.getPage(crawlUrl);
						List<HtmlAnchor> crawlLinks = crawlHTML.getAnchors();
						
						for (HtmlAnchor link2 : crawlLinks) { 
							
							//Convert URL into fully qualified URL first 
							String crawledLink = crawlHTML.getFullyQualifiedUrl(link2.getHrefAttribute()).toString();
							
							//Check if the link is within the same website (same baseurl). 
							//If so then save the link otherwise don't add it to list of URL to crawl. 
							if (crawledLink.contains(baseUrl) && crawledLink.contains("http://")) { 
								//Add the link to the url list
								urls.add(crawledLink);
							}
						}
						
						urls.remove(crawlUrl);
						crawlCompletedLinks.add(crawlUrl);
					}
				}
			}
			
			//Print all crawled links
			System.out.println("Crawling complete.");
			System.out.println("--------------------------------");
			System.out.println("Total Crawled Links: " + crawlCompletedLinks.size());
			Object[] crawledLinks = crawlCompletedLinks.toArray();
			
			for (Object crawledLink : crawledLinks) { 
				System.out.println("Link: " + crawledLink.toString());
			}
			
			for (String completedLink : crawlCompletedLinks) { 
				completedUrls.add(completedLink);
			}
			
			return completedUrls;
			
		} catch (Exception excep) { 
			System.out.println("Exception found: ");
			excep.printStackTrace();
		}
		
		return completedUrls;
	}
	
	/** 
	 * Method to get base url. 
	 * Asumming the input url is a valid url.
	 * @param url
	 * @return
	 */
	private String baseUrl(String url) { 
		
		String base = "";
		
		try { 
			
			URL link = new URL(url);
			base = link.getProtocol() + "://" + link.getHost();
			base = base.replace("http://", "").trim();
			System.out.println("Base URL is: " + base);
			
		} catch (MalformedURLException excep) { 
			
			System.out.println("Caught exception: " + excep);
		}
		
		return base;
	}
}
