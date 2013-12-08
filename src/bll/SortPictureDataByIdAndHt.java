package bll;

import java.util.Comparator;

import dal.PictureData;

public class SortPictureDataByIdAndHt implements Comparator<PictureData> {
    public int compare(PictureData pd1, PictureData pd2) {
    	int value = (int) (pd1.getId().compareTo(pd2.getId()));
    	if ( value == 0 ) {
    		value+= (int) (pd1.getHashtags().iterator().next().getHashtag().compareTo(pd2.getHashtags().iterator().next().getHashtag()));
    	}
    	return value;
    }
}
