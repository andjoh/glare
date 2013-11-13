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
	
	public static void addHashtagToDB(Hashtag hash){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		session.beginTransaction();
		
		session.save(hash);
		
		session.getTransaction().commit();
	}

	public static List<String> listOfHashtagsFromDB() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		session.beginTransaction();
		
		List<String> result = session.createQuery("select hashtag from Hashtag").list();
		
		session.getTransaction().commit();
		
		return result;
	}
}