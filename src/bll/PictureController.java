package bll;

import glare.*;
import dal.*;
import java.util.*;


public class PictureController
{
	
	public boolean searchPictures(String query, String source)
	{
		IReader reader              = (IReader) ClassFactory.getBeanByName(source);	
		PictureGetter pictureGetter = (PictureGetter) ClassFactory.getBeanByName("pictureGetter");
		
		pictureGetter.setReader(reader);
		
		ArrayList<PictureData> pictures = (ArrayList<PictureData>) pictureGetter.getPictures(query);

		//TODO Testing by printing out to com line
		System.out.println("");
		System.out.println("Printing found standard url's:");		
		for (PictureData p : pictures)
			System.out.println(p.getUrlStd());
		
		return true;
	}
}
