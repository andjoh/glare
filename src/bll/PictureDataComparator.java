package bll;

import java.util.Comparator;

import dal.PictureData;

public class PictureDataComparator implements Comparator<PictureData> {
    public int compare(PictureData pd1, PictureData pd2) {
    	return (int) (pd2.getCreatedTime() - pd1.getCreatedTime());
    }	
}