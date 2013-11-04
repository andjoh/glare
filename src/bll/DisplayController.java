package bll;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import dal.PictureData;

/**
 * @author Simen Sollie & Kristine Svaboe
 * @since 2013-11-04
 */

public class DisplayController {

	//getSortedPictures from PictureController:
	//public PictureViewModel[] GetPicturesToDisplay() {
	//PictureGetter picGetter = new PictureGetter(currentContext); cC=IReader?
	//Picture[] pictures = picGetter.GetPictures(); 

	//create list of PictureViewModel objects:
	//List<PictureViewModel> pictureVms = new List<PictureViewModel>();
	//for (for each PictureData)
	//	if !flag
	//		Lag bufImg
	//		PictureViewModel pVm = new PictureViewModel(url, id);
	//		pVm.PictureUrl = picture.Url; <-- ?
	//		pVm.PictureId = picture.Id;	  <-- ?
	//		pictureVms.Add(pVm);
	//	when pictureVms.length is 100 --> break
	//return pictureVms;

	//method to return to GUI
	//getPicturesToDisplay(viewMode String)
	//if viewMode.equals(rand)
	//	Collections.shuffle(pictureVms)
	//	return pictureVms;
	//else if viewMode.equals(seq)
	//	Collections.sort(pictureVms) - Dunno if it is already sorted??
	//	return pictureVms;

	public List<PictureViewModel> getPictureObjects(){
		ArrayList<PictureViewModel> po = new ArrayList<PictureViewModel>();
		List<PictureData> sortedPictureList = PictureController.getSortedPictureDataFromDb();
		int i = 1;
		for (PictureData p : sortedPictureList){
			if (!p.isRemoveFlag()){
				PictureViewModel pvm = new PictureViewModel(getBufImage(p), p.getId());
				po.add(pvm);
				i++;
			}
			if (i > 99){
				break;
			}
		}
		return po;
	}
	private BufferedImage getBufImage(PictureData p) throws IOException{
//		URL testUrl = new URL("http://pbs.twimg.com/media/BXrietbIgAAiroP.jpg");
		URL imageUrl = new URL(p.getUrlStd());
		InputStream in = imageUrl.openStream();;
		BufferedImage image = ImageIO.read(in);
		in.close();
		return image;
	}
}
