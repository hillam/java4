import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PriceFetcher{

	PriceFetcher(){

	}

	public static String getPrice(String url){
		String content = null;
		URLConnection connection = null;
		try {
			connection =  new URL(url).openConnection();
			Scanner scanner = new Scanner(connection.getInputStream());
			scanner.useDelimiter("\\Z");
			content = scanner.next();
		}catch ( Exception ex ) {
		    ex.printStackTrace();
		}
		System.out.println(content);

		//regex: <div class\=\"game_purchase_action_bg\">\s+<div class\=\"game_purchase_price price\">\s+&#36;(\d\d\.\d\d)\s+<\/div>
		Pattern p = Pattern.compile("<div class=\"game_purchase_action_bg\">[\\s\\n]+<div class=\"game_purchase_price price\">[\\s\\n]+&#36;(\\d\\d\\.\\d\\d)[\\s\\n]+</div>");
		Matcher m = p.matcher(content);

		return m.group(1);
	}

	public static void main(String[] args){
		System.out.println(PriceFetcher.getPrice("http://store.steampowered.com/app/322330/"));
	}
}