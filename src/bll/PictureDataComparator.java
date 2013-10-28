package bll;

import java.util.Comparator;

import dal.PictureData;

public class PictureDataComparator implements Comparator<PictureData> {
    public int compare(PictureData pd1, PictureData pd2) {
        return pd1.getId().compareTo(pd2.getId());
    }	
}