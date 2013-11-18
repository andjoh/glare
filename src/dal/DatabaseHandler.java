package dal;

import java.util.List;

/**
 * @author Andreas Bjerga & Marius Vasshus
 */

import org.hibernate.Query;
import org.hibernate.Session;

import com.sun.org.apache.bcel.internal.generic.Select;

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
		
		List<String> result = session.createQuery("SELECT hashtag FROM Hashtag").list();
		
		session.getTransaction().commit();
		
		return result;
	}
	
	public static void removeHashtagFromDB(String hashName){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		session.beginTransaction();
		
		String s = "DELETE FROM Hashtag WHERE hashtag=\'" + hashName.toLowerCase() + "\'";
		
		Query q = session.createQuery(s);
		q.executeUpdate();

		session.getTransaction().commit();
	}
	
	//NOTE TO SELF!! PICS WITH REMOVEFLAG SHOULD NOT BE REMOVED! FIX THIS LATER
	public static void removePicturesWithoutHashTagFromDB(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		session.beginTransaction();
		
		String s = "DELETE FROM PictureData "
				+ "WHERE NOT EXISTS ("
					+ "SELECT * "
					+ "FROM Hash_Pics "
					+ "WHERE Hash_Pics.picID=PictureData.id"
					+ ")";
		
		Query q = session.createSQLQuery(s);
		q.executeUpdate();

		session.getTransaction().commit();
	}
}