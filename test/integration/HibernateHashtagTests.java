package integration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import dal.DatabaseHandler;
import dal.Hashtag;

public class HibernateHashtagTests {
	@Test
	public void AddHashtagToDB_ReturnListOfHashtagsWhenHashtagIsRemovedFromDb() {
		Hashtag h1 = new Hashtag("test");
		
		DatabaseHandler.addHashtagToDB(h1);
		
		DatabaseHandler.removeHashtagFromDB(h1.getHashtag());
		
		ArrayList<String> hashtags = (ArrayList<String>) DatabaseHandler.listOfHashtagsFromDB();
		
		assertThat(hashtags.size(), is(0));
	}
	
	@Test
	public void AddExistingHashtagsToDB_ReturnListWithHashtagsFromDB_CheckThatSizeIsOne() {
		Hashtag hash = new Hashtag("winter");
		
		DatabaseHandler.addHashtagToDB(hash);
		DatabaseHandler.addHashtagToDB(hash);
		
		ArrayList<String> hashtags = (ArrayList<String>) DatabaseHandler.listOfHashtagsFromDB();
		
		assertThat(hashtags.size(), is(1));
	}
}
