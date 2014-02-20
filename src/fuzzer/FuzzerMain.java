package fuzzer;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import fuzzer.PageDiscover;

public class FuzzerMain {

	private String vectorsOption = "--vectors";
	private String sensitiveOption = "--sensitive";
	private String randomOption = "--random"; 
	private String slowOption = "--slow";
	private String commonWordOption = "--common-words";
	private String customAuthOption = "--custom-auth";
	
	/**
	 * Fuzzer main stuff 
	 * @param args
	 */
	private void runFuzzer(String[] args) { 
		
		String command = ""; 
		String url = "";
		List<String> options = new ArrayList<String>();
		boolean hardCodedAuth = false; 
		
		try { 
			
			WebClient webClient = new WebClient();
			webClient.setJavaScriptEnabled(true);
			
			for (int i = 0; i < args.length; i++) { 
				
				command = args[0];
				url = args[1];
				
				if (i == 2) { 
					
					//Check if options are valid and add to list, otherwise error out.
					if (!checkOptions(args[i]))  { 
						System.out.println("Not valid option command: " + args[i]);
						System.exit(1);
					}
					options.add(args[i]);
				}
			}
			
			//Authentication 
			
			//Page Crawler
			PageDiscover pageDiscover = new PageDiscover();
			
			//Input Crawler
			
		} catch (Exception excep) { 
			
			System.out.println("Exception found: " + excep);
		} 
	}
	
	/**
	 * Check the options provided via the command line for validity. 
	 * @param option
	 * @return
	 */
	private boolean checkOptions(String option) { 
		
		if (option.contains(vectorsOption)) { 
			return true; 
		}
		else if (option.contains(sensitiveOption)) { 
			return true; 
		}
		else if (option.contains(randomOption)) { 
			return true; 
		}
		else if (option.contains(slowOption)) { 
			return true; 
		} 
		else if (option.contains(commonWordOption)) { 
			return true; 
		}
		else if (option.contains(customAuthOption)) { 
			return true; 
		}
		else { 
			return false; 
		}
	}
	
	public static void main(String[] args) { 
		
		FuzzerMain fuzzer = new FuzzerMain();
		fuzzer.runFuzzer(args);
	}
}