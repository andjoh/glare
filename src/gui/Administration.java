package gui;

// TODO For testing purposes using com line
import static java.lang.System.out;
import java.util.Scanner;

import bll.*;

public class Administration 
{
	private AdminController controller;

	public Administration(AdminController controller)
	{
		this.controller = controller;
	}

	public void search(String query, String source) {		

		this.controller.searchPictures(query, source);		
	}
	
	// TODO For testing purposes using com line
	public void testFetchPicturesFromSource() 
	{
		System.out.println("Administration: testFetchPicturesFromSource");
		Scanner input = new Scanner(System.in);
		out.println("");
		out.print("Skriv søkestreng. Uten #! Ved å skrive Test vil alt utenom hent fra source bli testet: ");
		String searchTag = input.next();
		
		out.print("Velg source. 1: Twitter, 2: Instagram: ");
		String choice = input.next();
		
		while (choice.equals("1") || choice.equals("2"))
		{
			if ( choice.equals("1") )
				this.search(searchTag, "twitterReader");
			else if ( choice.equals("2") )
				this.search(searchTag, "instagramReader");

			out.println("");
			out.print("Skriv søkestreng. Uten #! Ved å skrive Test vil alt utenom hent fra source bli testet: ");
			searchTag = input.next();
			
			out.println("");
			out.print("Velg source. 1: Twitter, 2: Instagram: ");
			choice = input.next();
		}
		
		System.out.println("The End...");
	}

}
