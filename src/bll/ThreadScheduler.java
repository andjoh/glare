package bll;

import java.util.ArrayList;

import dal.DatabaseManager;
import dal.PictureData;

public class ThreadScheduler implements Runnable{
	private ArrayList<String> hashtagList;
	PictureController pc;
	
	public ThreadScheduler(ArrayList<String> hashtagList){
		pc = new PictureController(new DatabaseManager());
		this.hashtagList = hashtagList;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(120000);
			for(int i = 0; i < hashtagList.size(); i++){
				ArrayList<PictureData> pictureDataFromSource = (ArrayList<PictureData>) pc.getPictureDataFromSources();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
