package bll;

/**
 * A thread that runs in the background and gets pictures with the applications hashtags
 * @author Andreas Bjerga & Marius Vasshus
 *
 */
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
