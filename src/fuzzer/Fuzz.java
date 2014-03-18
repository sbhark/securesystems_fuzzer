package fuzzer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
	private WebClient client = new WebClient();
	private String slowValue = "";
	private ArrayList<String> attackVectors = new ArrayList<String>();
	private ArrayList<String> sensitiveList = new ArrayList<String>();
	private boolean random = false; 
	
	/**
	 * Fuzzer main stuff 
	 * @param args
	 */
	private void runFuzzer(String[] args) { 
		
		String command = ""; 
		String url = "";
		List<String> options = new ArrayList<String>();
		
		try { 
			
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
			
			//Vector Test 
			VectorTest vectorAttack = new VectorTest(); 
			
			for (String link : links) { 
				vectorAttack.attack(client, link, attackVectors, sensitiveList);
			}
			
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
			
			String file = split[1];
			String line = ""; 
			
			try {
				
				BufferedReader br = new BufferedReader(new FileReader(file));
				
				while((line = br.readLine()) != null) { 
					
					attackVectors.add(line);
				}
				br.close();
				
			} catch (FileNotFoundException e) {
				
				System.out.println("ERROR: Could not find vectors file.");
			} catch (IOException e) {
				
				System.out.println("ERROR: IOException while processing vectors file.");
			}
			
		}
		else if (optionCommand.contains(sensitiveOption)) { 
			
			try {
				
				String file = split[1];
				String line = ""; 
				
				BufferedReader br = new BufferedReader(new FileReader(file));
				
				while((line = br.readLine()) != null) { 
					
					sensitiveList.add(line);
				}
				br.close();
				
			} catch (FileNotFoundException e) {
				
				System.out.println("ERROR: Could not find sensitive info file.");
			} catch (IOException e) {
				
				System.out.println("ERROR: IOException while processing sensitive info file.");
			}
		}
		else if (optionCommand.contains(randomOption)) { 

			String randomOption = split[1];
			random = Boolean.parseBoolean(randomOption); 
		}
		else if (optionCommand.contains(slowOption)) { 
			
			slowValue = split[1];
			client.setTimeout(Integer.parseInt(slowValue));
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