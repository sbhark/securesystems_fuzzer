package fuzzer;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;

import fuzzer.Authentication;
import fuzzer.PageDiscover;

public class Fuzz {

	private String vectorsOption = "--vectors";
	private String sensitiveOption = "--sensitive";
	private String randomOption = "--random"; 
	private String slowOption = "--slow";
	private String commonWordOption = "--common-words";
	private String customAuthOption = "--custom-auth";
	private String customAuthSite = "";
	
	private boolean customAuth = false; 

	/**
	 * Fuzzer main stuff 
	 * @param args
	 */
	private void runFuzzer(String[] args) { 
		
		String command = ""; 
		String url = "";
		List<String> options = new ArrayList<String>();
		
		try { 
			
			WebClient client = new WebClient();
			client.setJavaScriptEnabled(false);
			
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
			
			for (String option : options) { 
				parseOptions(option);
			}
			
			//Authentication 
			Authentication auth = new Authentication();
			if (customAuth) { 
				
				if (customAuthSite.equals("dvwa")) { 
					auth.loginDvwa(client);
				}
				if (customAuthSite.equals("bodgeit")) { 
					auth.loginBodgeIt(client, "", "");
				}
			}
					
			//Page Crawler
			PageDiscover pageDiscover = new PageDiscover();
			ArrayList<String> links = pageDiscover.discoverPages(client, url);
			
			//Input Crawler
			InputDiscover inputDiscover = new InputDiscover();

			//Get All Inputs 
			for (String link : links) { 
				inputDiscover.discoverInputs(client, link);
			}
			
			//Get Cookies 
			inputDiscover.discoverCookies(client, url);
			
			client.closeAllWindows();
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
	
	private void parseOptions(String option) { 
		
		String[] split = option.split("=");
		String optionCommand = split[0];
		if (optionCommand.contains(vectorsOption)) {

		}
		else if (optionCommand.contains(sensitiveOption)) { 
			
		}
		else if (optionCommand.contains(randomOption)) { 

		}
		else if (optionCommand.contains(slowOption)) { 
			
		} 
		else if (optionCommand.contains(commonWordOption)) { 
			
		}
		else if (optionCommand.contains(customAuthOption)) { 
			
			customAuth = true;
			String site = split[1];
			if (site.contains("dvwa")) { 
				customAuthSite = "dvwa";
			} 
			if (site.contains("bodgeit")) { 
				customAuthSite = "bodgeit";
			}
		}
		else { 
			//Don't do anything. 
		}
	}
	
	public static void main(String[] args) { 
		
		Fuzz fuzzer = new Fuzz();
		fuzzer.runFuzzer(args);
	}
}