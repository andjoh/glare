package bll;

/**
 * A thread that runs in the background and gets pictures with the applications hashtags
 * @author Andreas Bjerga & Marius Vasshus
 * 
 */
public class ThreadScheduler implements Runnable {
	private PictureController pc;

	public ThreadScheduler(){
		pc = (PictureController) glare.ClassFactory.getBeanByName("pictureController");
	}

	@Override
	public void run() {
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		while (true) {
			try {
				pc.getNewPictureData();
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
