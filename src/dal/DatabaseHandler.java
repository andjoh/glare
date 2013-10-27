package dal;

import java.util.List;

import org.hibernate.Session;

public class DatabaseHandler {
	
	public static void addPictureToDB(PictureData pic){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		session.beginTransaction();
		
		session.save(pic);
		
		session.getTransaction().commit();
	}
	
	public static List<PictureData> listOfPicturesFromDB(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		session.beginTransaction();
		
		List<PictureData> result = session.createQuery("from PictureData").list();
		
		session.getTransaction().commit();
		
		return result;
	}

}