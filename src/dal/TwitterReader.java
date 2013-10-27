package dal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author Simen & Kristine
 * @since 2013-10-13
 */

public class TwitterReader  implements IReader {

	/**
	 * Search for tweets with a specific hashtag.
	 * @param args
	 */

	private static ConfigurationBuilder cb;
	private static Twitter twitter;
	
	public void TwitterAuth() throws TwitterException{
		cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey("")
		.setOAuthConsumerSecret("")
		.setOAuthAccessToken("")
		.setOAuthAccessTokenSecret((""));
	}
	
	private int DateConvert(Date time) throws ParseException{
		DateFormat sdf = new SimpleDateFormat("yyyMMddHHmmss");
		String sDate = sdf.format(time);
		int timestamp = (int)sdf.parse(sDate).getTime();
		return (timestamp/1000);
	}

	public List<PictureData> getPictures(String searchTag) throws Exception {

		TwitterAuth();
		twitter = new TwitterFactory(cb.build()).getInstance();
		ArrayList<PictureData> pictures = new ArrayList<PictureData>();

		try {
			Query query = new Query(searchTag);
			QueryResult result = twitter.search(query);
			List<Status> tweets = result.getTweets();
			for (Status tweet : tweets){
				PictureData pd = new PictureData();
				MediaEntity[] media = tweet.getMediaEntities();
				Date date = tweet.getCreatedAt();

				if (media[0].getType().equals("photo")){
					String id = String.valueOf(media[0].getId());
					pd.setId(id);
					pd.setUrlStd(media[0].getMediaURL() + ":large");
					pd.setUrlThumb(media[0].getMediaURL() + ":thumb");
					pd.setTime(DateConvert(date));
				}
				pictures.add(pd);
			}
		} catch (TwitterException te){
			te.printStackTrace();
		}
		return pictures;
	}
}