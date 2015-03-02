import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;


	/*--------------------------------------------------------------------------
		TODO: CURRENTLY NOT WORKING FOR FREE TO PLAY OR AGECHECK 
	--------------------------------------------------------------------------*/

public class PriceFetcher{

	PriceFetcher(){

	}

	public static String getPrice(String url) throws Exception{
		String content = "";
		url = url;

		URL u = new URL(url);

		HttpURLConnection connection = (HttpURLConnection) u.openConnection();

		Scanner scanner = new Scanner(connection.getInputStream());
		while(scanner.hasNextLine()){
			content += scanner.nextLine();
		}
		scanner.close();

		//System.out.println(content);

		Pattern p1 = Pattern.compile("<div class=\"discount_final_price\">&#36;([\\d\\.]+)");
		Pattern p2 = Pattern.compile("<div class=\"game_purchase_price price\">[^&]+&#36;([\\d\\.]+)");
		
		Matcher m1 = p1.matcher(content);
		Matcher m2 = p2.matcher(content);

		//System.out.println(m1.find() + " " + m2.find());

		if(m1.find())
			return m1.group(1);
		if (m2.find())
			return m2.group(1);
		return "No match found.";
	}

	public static void main(String[] args) throws Exception{
		System.out.println(PriceFetcher.getPrice("http://store.steampowered.com/app/202200/"));
		System.out.println(PriceFetcher.getPrice("http://store.steampowered.com/app/226860/"));
	}
}