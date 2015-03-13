public class PriceFetchTest{
	public static void main(String[] args) throws Exception{
		PriceFetcher p1 = new PriceFetcher();
		p1.setStoreURL("http://store.steampowered.com/app/282210/");
		
		PriceFetcher p2 = new PriceFetcher("http://store.steampowered.com/app/218620/");
		PriceFetcher p3 = new PriceFetcher("http://store.steampowered.com/app/212500/");

		System.out.println(p1.getName() + "\t\t\t" + p1.getPriceString());
		System.out.println(p2.getName() + "\t\t\t\t\t\t" + p2.getPriceString());
		System.out.println(p3.getName() + "\t" + p3.getPriceString());
	}
}