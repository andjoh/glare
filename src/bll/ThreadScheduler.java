package bll;

import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.ClassFactory;

import dal.DatabaseHandler;
import dal.DatabaseManager;
import dal.PictureData;

public class ThreadScheduler implements Runnable{
	private PictureController pc;
	
	public ThreadScheduler(){
		pc = (PictureController) glare.ClassFactory.getBeanByName("pictureController");
	}

	@Override
	public void run() {
		try {
			Thread.sleep(120000);
			pc.getNewPictureData();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
