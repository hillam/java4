import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;


	/*--------------------------------------------------------------------------
		 
	--------------------------------------------------------------------------*/

public class PriceFetcher{

	private double price;
	private String price_string;
	private String name;
	private String store_url;
	private String api_url;

	private String content;

	public PriceFetcher(){}

	public PriceFetcher(String url) throws Exception{
		setStoreURL(url);
	}

	public void setStoreURL(String url) throws Exception{
		store_url = url;
		initialize();
	}

	public String getName(){
		return name;
	}

	public double getPrice(){
		return price;
	}

	public String getPriceString(){
		return price_string;
	}



	/*--------------------------------------------------------------------------
		 PRIVATE MEMBERS
	--------------------------------------------------------------------------*/

	private void initialize() throws Exception{
		content = "";

		Pattern p = Pattern.compile("(\\d+)");
		Matcher m = p.matcher(store_url);

		if(m.find()){
			api_url = "http://store.steampowered.com/api/appdetails?appids=";
			api_url += m.group(1);
		}
		else{
			content = "Bad URL";
			return;
		}

		URL u = new URL(api_url);

		HttpURLConnection connection = (HttpURLConnection) u.openConnection();

		Scanner scanner = new Scanner(connection.getInputStream());
		while(scanner.hasNextLine()){
			content += scanner.nextLine();
		}
		scanner.close();

		setName();
		setPrice();
	}

	private void setName(){
		Pattern p = Pattern.compile("\"name\"\\:\"([^\"]+)");
		Matcher m = p.matcher(content);

		if(m.find())
			name = m.group(1);
		else
			name = "Error parsing JSON";
	}

	private void setPrice(){
		Pattern p = Pattern.compile("\"final\"\\:(\\d+)");
		Matcher m = p.matcher(content);

		if(m.find()){
			price = Double.parseDouble(m.group(1))/100;
			price_string = "" + price;
		}
		else if(content.contains("\"is_free\":true")){
			price = 0;
			price_string = "Free";
		}
		else{
			price = -1;
			price_string = "Error parsing JSON";
		}
	}



	/*--------------------------------------------------------------------------
		 STATIC OPTION (depricated)
	--------------------------------------------------------------------------*/

	public static String getPrice(String url) throws Exception{
		String c = "";

		Pattern p = Pattern.compile("(\\d+)");
		Matcher m = p.matcher(url);

		if(m.find()){
			url = "http://store.steampowered.com/api/appdetails?appids=";
			url += m.group(1);
		}
		else
			return "Bad URL";

		URL u = new URL(url);

		HttpURLConnection connection = (HttpURLConnection) u.openConnection();

		Scanner scanner = new Scanner(connection.getInputStream());
		while(scanner.hasNextLine()){
			c += scanner.nextLine();
		}
		scanner.close();

		p = Pattern.compile("\"final\"\\:(\\d+)");
		m = p.matcher(c);

		//System.out.println(m.find() + " " + m2.find());

		if(m.find())
			return "" + Double.parseDouble(m.group(1))/100;
		else if(c.contains("\"is_free\":true"))
			return "Free";
		return "Error parsing JSON";
	}
}