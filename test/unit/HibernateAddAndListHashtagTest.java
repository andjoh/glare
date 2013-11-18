package unit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import dal.DatabaseHandler;
import dal.Hashtag;
import dal.PictureData;

public class HibernateAddAndListHashtagTest {

	ArrayList<String> hashtags;
	
	@Test
	public void AddHashtagsToDB_ReturnListWithHashtagsFromDB() {
		Hashtag h1 = new Hashtag("winter");
		Hashtag h2 = new Hashtag("summer");
		Hashtag h3 = new Hashtag("hibernate");
		
		DatabaseHandler.addHashtagToDB(h1);
		DatabaseHandler.addHashtagToDB(h2);
		DatabaseHandler.addHashtagToDB(h3);
		
		hashtags = (ArrayList<String>) DatabaseHandler.listOfHashtagsFromDB();
		
		for(String ht: hashtags){
			System.out.println(ht.toString());
		}
		
		assertThat(hashtags.size(), is(not(nullValue())));
	}

}
