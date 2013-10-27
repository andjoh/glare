package bll;

public class AdminController 
{
	PictureController picCtrl;
	
	public AdminController(PictureController picCtrl) 
	{
		this.picCtrl = picCtrl;		
	}
	
	public boolean searchPictures(String query, String source)
	{
		return picCtrl.searchPictures(query, source);
	}
}