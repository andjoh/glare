package dal;

import java.util.List;

/**
 * @author Andreas Bjerga & Marius Vasshus
 */


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

public class DatabaseHandler {

	public static void addPictureToDB(PictureData pic){
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		try{
			tx = session.beginTransaction();

			session.save(pic);

			session.getTransaction().commit();
		} catch(HibernateException e){
			if (tx!=null) 
				tx.rollback();
			e.printStackTrace();
		}
	}


	public static List<PictureData> listOfPicturesFromDB(){
		Transaction tx = null;
		List<PictureData> result = null;

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		try{
			tx = session.beginTransaction();

			result = session.createQuery("from PictureData").list();

			session.getTransaction().commit();
		} catch(HibernateException e){
			if (tx!= null)
				tx.rollback();
			e.printStackTrace();
		} 
		return result;
	}

	public static void addHashtagToDB(Hashtag hash){
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		try{
			tx = session.beginTransaction();

			session.save(hash);

			session.getTransaction().commit();
		} catch(ConstraintViolationException e){
			if(tx != null)
				tx.rollback();
			e.printStackTrace();
		}
	}

	public static List<String> listOfHashtagsFromDB() {
		Transaction tx = null;
		List<String> result = null;

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		try{
			tx = session.beginTransaction();

			result = session.createQuery("SELECT hashtag FROM Hashtag").list();

			session.getTransaction().commit();
		} catch(HibernateException e){
			if(tx != null)
				tx.rollback();
			e.printStackTrace();
		}

		return result;
	}

	public static void removePictureDataFromDB(){
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		try{
			tx = session.beginTransaction();

			Query q = session.createSQLQuery("DELETE FROM PictureData");
			q.executeUpdate();

			session.getTransaction().commit();
		} catch(HibernateException e){
			if (tx!=null) 
				tx.rollback();
			e.printStackTrace();
		}
	}

	public static void removeHashtagFromDB(String hashName){
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		try{
			tx = session.beginTransaction();

			String s = "DELETE FROM Hashtag WHERE hashtag=\'" + hashName.toLowerCase() + "\'";

			Query q = session.createQuery(s);
			q.executeUpdate();

			session.getTransaction().commit();
		} catch(HibernateException e){
			if (tx!=null) 
				tx.rollback();
			e.printStackTrace();
		}
	}

	public static void removePicturesWithoutHashTagFromDB(){
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		try{
			tx = session.beginTransaction();

			String s = "DELETE FROM PictureData "
					+ "WHERE removeFlag = (0) "
					+ "AND NOT EXISTS ("
					+ "SELECT * "
					+ "FROM Hash_Pics "
					+ "WHERE Hash_Pics.picID=PictureData.id"
					+ ")";

			Query q = session.createSQLQuery(s);
			q.executeUpdate();

			session.getTransaction().commit();
		} catch(HibernateException e){
			if (tx!=null) 
				tx.rollback();
			e.printStackTrace();
		}
	}
	public static void setRemoveFlag(String picID){
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		try{
			tx = session.beginTransaction();

			String s = "update PictureData set removeFlag = :newFlag where id = :picID";
			Query q = session.createQuery(s);
			q.setBoolean("newFlag", true);
			q.setString("picID", picID);

			q.executeUpdate();

			session.getTransaction().commit();
		} catch(HibernateException e){
			if (tx!=null) 
				tx.rollback();
			e.printStackTrace();
		}
	}
}
