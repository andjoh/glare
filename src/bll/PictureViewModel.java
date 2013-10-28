package bll;

import java.util.ArrayList;

public class PictureViewModel {
	private ArrayList<String> pictures;
	
	public PictureViewModel(ArrayList<String> pictures) {
		this.pictures = pictures;
	}
	
	public ArrayList<String> getPictures() {
		return pictures;
	}
}