package glare;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dal.DatabaseHandler;
import dal.Hashtag;
import dal.PictureData;

import resources.DatabaseManagerDummy;

import bll.*;
import gui.*;

public class Main {
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		DatabaseHandler dbHandler           = new DatabaseHandler();
		DatabaseManagerDummy dbManagerDummy = new DatabaseManagerDummy(dbHandler);
		PictureController picCtrl           = new PictureController(dbManagerDummy);
				
		// Do some dummy stuff
		List<String> sourcesDummy;
		Set<String> hashtagsDummy;

		// Set dummy sources
		sourcesDummy = new ArrayList<String>();
		sourcesDummy.add("dummyinstagram");
		sourcesDummy.add("dummytwitter");
		
		// Set test data for source and hashtag
		hashtagsDummy = new HashSet<String>();
		hashtagsDummy.add("twittermotjavatesting");
		hashtagsDummy.add("raskebriller");

		// Set dummy sources and hashtag
		dbManagerDummy.setSources(sourcesDummy);
		dbManagerDummy.setHashtags(hashtagsDummy);
		
		// Run test
		picCtrl.searchPictureData();
		picCtrl.processPictureData();	
			
		DisplayController dc        = new DisplayController(picCtrl);
		
		ShowInterface showInterface = new ShowInterface(dc);
		
		
//		// Set dummy picturedata from db
//		List<PictureData> pictureDataFromDbDummy = new ArrayList<PictureData>();
//
//		PictureData p = new PictureData();
//		p.setId("picID 1");
//		p.setCreatedTime(1);
//		p.setUrlStd("http://distilleryimage3.s3.amazonaws.com/ef1236282adb11e3a9b322000a9e5afc_6.jpg");
//		p.setUrlThumb("http://distilleryimage3.s3.amazonaws.com/ef1236282adb11e3a9b322000a9e5afc_5.jpg");
//		p.addHashtag(new Hashtag("hashtag 1"));
//		pictureDataFromDbDummy.add(p);
//		
//		p = new PictureData();
//		p.setId("picID 3");
//		p.setCreatedTime(3);
//		p.setUrlStd("http://distilleryimage3.s3.amazonaws.com/ef1236282adb11e3a9b322000a9e5afc_8.jpg");
//		p.setUrlThumb("http://images.ak.instagram.com/profiles/profile_186344368_75sq_1380238572.jpg");
//		p.addHashtag(new Hashtag("hashtag 3"));
//		pictureDataFromDbDummy.add(p);
//		
//		p = new PictureData();
//		p.setId("picID 4");
//		p.setCreatedTime(4);
//		p.setUrlStd("http://distilleryimage3.s3.amazonaws.com/ef1236282adb11e3a9b322000a9e5afc_8.jpg");
//		p.setUrlThumb("http://images.ak.instagram.com/profiles/profile_186344368_75sq_1380238572.jpg");
//		p.addHashtag(new Hashtag("hashtag 4"));
//		pictureDataFromDbDummy.add(p);	

	
		

	}
}