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
 * @author Simen Sollie & Kristine Svaboe
 * @since 2013-10-13
 */

public class TwitterReader  implements IReader {

	/**
	 * Search for tweets with a specific hashtag.
	 * @param args
	 */

	private ConfigurationReader confReader;
	private ConfigurationBuilder cb;
	private Twitter twitter;
	
	public TwitterReader(ConfigurationReader confReader){
		this.confReader = confReader;
	}

	public void TwitterAuth() throws TwitterException{
//		ConfigurationReader reader = new ConfigurationReader("src/resource/conf.ini");
		confReader.setPath("src/resource/conf.ini");
		cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey(confReader.read("consumer_key"))
		.setOAuthConsumerSecret(confReader.read("consumer_secret"))
		.setOAuthAccessToken(confReader.read("access_token"))
		.setOAuthAccessTokenSecret(confReader.read("access_token_secret"));
	}

	private long DateConvert(Date time) throws ParseException{
		DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String sDate = sdf.format(time);
		long timestamp = (long)sdf.parse(sDate).getTime();
		return (timestamp/1000);
	}

	public List<PictureData> getPictures(String searchTag) throws Exception {

		TwitterAuth();
		twitter = new TwitterFactory(cb.build()).getInstance();
		ArrayList<PictureData> pictures = new ArrayList<PictureData>();

		try {
			Query query = new Query("#" + searchTag);
			QueryResult result = twitter.search(query);
			List<Status> tweets = result.getTweets();
			for (Status tweet : tweets){
				PictureData pd = new PictureData();
				MediaEntity[] media = tweet.getMediaEntities();
				Date date = tweet.getCreatedAt();

				for (int i=0; i<media.length; i++){
					if (media[0].getType().equals("photo")){
						String id = String.valueOf(media[0].getId());
						pd.setId(id);
//						System.out.println(pd.getId());
						pd.setUrlStd(media[0].getMediaURL() + ":large");
//						System.out.println(pd.getUrlStd());
						pd.setUrlThumb(media[0].getMediaURL() + ":thumb");
//						System.out.println(pd.getUrlThumb());
						pd.setCreatedTime(DateConvert(date));
//						System.out.println(pd.getCreatedTime());
						pd.setRemoveFlag(false);
						pd.addHashtag(new Hashtag(searchTag));
//						System.out.println(pd.getHashtags());
						pictures.add(pd);
					}
				}
			}
		} catch (TwitterException te){
			te.printStackTrace();
		}
		return pictures;
	}
}
