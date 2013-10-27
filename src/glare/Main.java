package glare;

import gui.*;

public class Main 
{
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		Administration admin = (Administration) ClassFactory.getBeanByName("administration");
		
		admin.testFetchPicturesFromSource();
	}
}