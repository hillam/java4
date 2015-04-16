public class PriceFetchTest{
	public static void main(String[] args) throws Exception{
		PriceFetcher p1 = new PriceFetcher();
		p1.setStoreURL("http://store.steampowered.com/app/282210/");
		
		PriceFetcher p2 = new PriceFetcher("http://store.steampowered.com/app/218620/");
		PriceFetcher p3 = new PriceFetcher("http://store.steampowered.com/app/333930/");

		System.out.println(p1.getName() + ":\n" + p1.getPriceString());
		System.out.println(p2.getName() + ":\n" + p2.getPriceString());
		System.out.println(p3.getName() + ":\n" + p3.getPriceString());
	}
}