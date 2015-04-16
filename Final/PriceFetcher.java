import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;


	/*--------------------------------------------------------------------------
		Get and name and price of a game given it's store url
		using steam web api
	--------------------------------------------------------------------------*/

public class PriceFetcher implements Comparable<PriceFetcher>{

	private double price;
	private String price_string;
	private String name;
	private String store_url;
	private String api_url;
	private int id;

	private String content;

	public PriceFetcher(){}

	public PriceFetcher(String url) {
		setStoreURL(url);
	}

	public void setStoreURL(String url) {
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

	public int getId(){
		return id;
	}

	public int compareTo(PriceFetcher p){
		return (new Integer(id)).compareTo(p.getId());
	}

	public void initialize() {
		content = "";

		Pattern p = Pattern.compile("(\\d+)");
		Matcher m = p.matcher(store_url);

		if(m.find()){
			api_url = "http://store.steampowered.com/api/appdetails?appids=";
			api_url += m.group(1);
			id = Integer.parseInt(m.group(1));
		}
		else{
			content = "Bad URL";
			name = "Bad URL";
			api_url = "Bad URL";
			price_string = "Bad URL";
			price = -1;
			return;
		}

		try{
			URL u = new URL(api_url);

			HttpURLConnection connection = (HttpURLConnection) u.openConnection();

			Scanner scanner = new Scanner(connection.getInputStream());
			while(scanner.hasNextLine()){
				content += scanner.nextLine();
			}
			scanner.close();
		} catch (Exception e){
			System.out.println(e);
		}

		setName();
		setPrice();
	}

	/*--------------------------------------------------------------------------
		 PRIVATE MEMBERS
	--------------------------------------------------------------------------*/

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

		//System.out.println("Finding..");
		if(m.find()){
			//System.out.println("1");
			price = Double.parseDouble(m.group(1))/100;
			price_string = "" + price;
		}
		else if(content.contains("\"is_free\":true")){
			//System.out.println("2");
			price = 0;
			price_string = "Free";
		}
		else{
			//System.out.println("3");
			price = -1;
			price_string = "Error parsing JSON";
		}
	}
}
