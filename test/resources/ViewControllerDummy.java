package resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import bll.PictureController;
import bll.SettingsPicture;
import bll.ViewController;
import dal.DatabaseManager;
import dal.PictureData;

/**
 * @author Andreas J
 * @since 2013-11-04
 */

public class ViewControllerDummy extends ViewController {

	/**
	 * Manage list with pictures
	 */

	private List<PictureData> sortedPictureList;
	private List<PictureData> randomPictureList;
	private List<PictureData> pictureDataList;
	private boolean isRandom;
	private int displayTime;

	public ViewControllerDummy(PictureController picCtrl, DatabaseManager dbMan) {
		super(null);
		sortedPictureList = new ArrayList<PictureData>();

		// Default settings
		isRandom = false;
		displayTime = 1000;
	}


	@Override
	public BufferedImage getCurrentPicture() {
		if (sortedPictureList.isEmpty()) {
			System.out.println("Henter ny liste");
			getSortedList();
			System.out.println("Ferdig å hente liste");
		}

		PictureData p;

		if (isRandom) {
			p = randomPictureList.remove(0);
			sortedPictureList.remove(p);
		} else {
			p = sortedPictureList.remove(0);
			randomPictureList.remove(p);
		}

		return getBufImage(p.getUrlStd());
	}


	@Override
	public void getSortedList() {
		sortedPictureList = new ArrayList<PictureData>(pictureDataList);
		randomPictureList = new ArrayList<PictureData>(sortedPictureList);
		pictureDataList = new ArrayList<PictureData>(sortedPictureList);
		Collections.shuffle(randomPictureList);
	}

	/**
	 * Create buffered image from picture in source folder
	 * 
	 * @param path
	 * @return
	 */

	private BufferedImage getBufImage(String path) {
		URL url = this.getClass().getResource(path);
		BufferedImage tmp = null;
		try {

			tmp = ImageIO.read(url);

		} catch (IOException ex) {

		}
		return tmp;
	}

	/**
	 * list of list which were formerly used as data-holder by test to get
	 * pictures to table by loading local images into a rowsthe abstract table
	 * model
	 * 
	 * @param cols
	 * @return
	 */
	public List<List<SettingsPicture>> getSettingsPictures(int rows, int cols) {
		List<List<SettingsPicture>> settingsPictures = null;
		List<PictureData> picd = null;
		if (rows * cols == 100) {
			picd = getPictureData(rows, cols);
			settingsPictures = new ArrayList<List<SettingsPicture>>();

			PictureData pic = null;
			String id = "", url;
			int s = 0;
			for (int r = 0; r < 100 / cols; r++) {
				List<SettingsPicture> tmp = new ArrayList<SettingsPicture>();
				for (int c = 0; c < cols; c++) {

					pic = picd.get(s);
					url = pic.getUrlThumb();
					id = pic.getId();
					tmp.add(new SettingsPicture(id, this.getBufImage(url)));
					s++;
				}
				settingsPictures.add(tmp);

			}
		}
		return settingsPictures;
	}

	/**
	 * Create picture data without accessing internet or DB Creates PictureDatas
	 * with increasing id (for tests), and Url as the location to test images.
	 * The images had numbers from 1-10 for testing of table functionality
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public List<PictureData> getPictureData(int row, int col) {
		List<PictureData> picd = new ArrayList<PictureData>();
		for (int r = 0; r < 100; r++) {

			picd.add(new PictureData("idnum" + r, "/resource/img/" + r % 10
					+ ".png", "/resource/img/" + r % 10 + ".png", 12345, false));
		}
		return picd;

	}

	@Override
	public boolean isRandom() {
		return isRandom;

	}

	@Override
	public int getDisplayTime() {
		return displayTime / 1000;
	}

	/**
	 * @param displayTime
	 */
	@Override
	public void setDisplayTime(int displayTime) {
		this.displayTime = displayTime * 1000;
	}
}
