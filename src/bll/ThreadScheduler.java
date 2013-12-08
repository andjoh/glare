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
			// System.out.println("Scheduler running");
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (true) {
			try {
				// System.out.println("HENTER NYE BILDER!!!!!!!!!!!!!!!!!!!!!!!!");
				pc.getNewPictureData();
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
