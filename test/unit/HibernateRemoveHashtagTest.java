package unit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import dal.DatabaseHandler;
import dal.Hashtag;

public class HibernateRemoveHashtagTest {
	
	ArrayList<String> hashtags;
	
	@Test
	public void AddTwoHashtagsToDB_ReturnListOfHashtagsWhenOneHashtagIsRemovedFromDb() {
		Hashtag h1 = new Hashtag("test");
		Hashtag h2 = new Hashtag("test2");
		
		DatabaseHandler.addHashtagToDB(h1);
		DatabaseHandler.addHashtagToDB(h2);
		
		DatabaseHandler.removeHashtagFromDB(h1.getHashtag());
		
		hashtags = (ArrayList<String>) DatabaseHandler.listOfHashtagsFromDB();
		
		assertThat(hashtags.size(), is(1));
	}
}
