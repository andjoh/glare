package dal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

/**
 * A class that handles all communication with DB through Hibernate.
 * @author Andreas Bjerga & Marius Vasshus
 */
public class DatabaseHandler {

	/**
	 * Method to add and save a picture to DB. Checks for duplicate pictures and saves changes to the relevant picture to DB.
	 * @param pic
	 */
	public static void addPictureToDB(PictureData pic){
		List<PictureData> resultPic = null;
		List<Hashtag> resultHash = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
			tx = session.beginTransaction();
			
			//Checks if a hashtag, connected to the picture, is already saved in the DB
			Set<Hashtag> hashtagsFromPic = pic.getHashtags();
			List<Hashtag> hashtagList = new ArrayList<Hashtag>();
			hashtagList.addAll(hashtagsFromPic);
			for(int i = 0; i < hashtagList.size(); i++){
				resultHash = DatabaseHandler.returnHashtagIfAlreadyExists(hashtagList.get(i));
				if(resultHash.size() == 1){
					pic.remHashtag(hashtagList.get(i));
					pic.addHashtag(resultHash.get(0));
				}
			}

			//Checks if a picture with the same ID is in DB, and updates the hashtags if exists
			hashtagsFromPic = pic.getHashtags();
			resultPic = DatabaseHandler.returnPictureIfAlreadyExists(pic);
			if(resultPic.size() == 1){
				Set<Hashtag> hashtagsFromDB = resultPic.get(0).getHashtags();
				hashtagsFromPic.addAll(hashtagsFromDB);
				pic.setHashtags(hashtagsFromPic);
				session.merge(pic);
			}else
				session.saveOrUpdate(pic);

			tx.commit();
		} catch(HibernateException e){
			if (tx!=null) 
				tx.rollback();
			e.printStackTrace();
		} finally{
			session.close();
		}
	}
	
	/**
	 * Checks if picture with given ID is already in DB, and returns this picture as List
	 * @param pic
	 * @return List<PictureData>
	 */
	@SuppressWarnings("unchecked")
	public static List<PictureData> returnPictureIfAlreadyExists(PictureData pic){
		List<PictureData> result = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
			tx = session.beginTransaction();

			result = session.createQuery("from PictureData where id=\'" + pic.getId() + "\'").list();
			tx.commit();
		} catch(HibernateException e){
			if (tx!=null) 
				tx.rollback();
			e.printStackTrace();
		} finally{
			session.close();
		}
		return result;
	}

	/**
	 * Returns a list of all pictures saved in DB
	 * @return List<PictureData>
	 */
	@SuppressWarnings("unchecked")
	public static List<PictureData> listOfPicturesFromDB(){
		Transaction tx = null;
		List<PictureData> result = null;

		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
			tx = session.beginTransaction();

			result = session.createQuery("from PictureData").list();

			tx.commit();
		} catch(HibernateException e){
			if (tx!= null)
				tx.rollback();
			e.printStackTrace();
		} finally{
			session.close();
		}
		return result;
	}

	/**
	 * Checks if hashtag is already saved to DB. If not, saves hashtag to DB.
	 * @param hash
	 */
	public static void addHashtagToDB(Hashtag hash){
		List<Hashtag> result = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
			tx = session.beginTransaction();
			
			result = DatabaseHandler.returnHashtagIfAlreadyExists(hash);
			if(result.size() == 0){
				session.saveOrUpdate(hash);
			}

			tx.commit();
		} catch(ConstraintViolationException e){
			if(tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally{
			session.close();
		}
	}
	
	/**
	 * Checks if given hashtag exists in DB, returns as List
	 * @param h
	 * @return List<PictureData>
	 */
	@SuppressWarnings("unchecked")
	public static List<Hashtag> returnHashtagIfAlreadyExists(Hashtag h){
		List<Hashtag> result = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
			tx = session.beginTransaction();

			result = session.createQuery("select distinct h from Hashtag h left join fetch h.pictures where hashtag=\'" + h.getHashtag() + "\'").list();

			tx.commit();
		} catch(HibernateException e){
			if (tx!=null) 
				tx.rollback();
			e.printStackTrace();
		} finally{
			session.close();
		}
		return result;
	}

	/**
	 * Returns a list of all hashtags from DB
	 * @return List<String>
	 */
	@SuppressWarnings("unchecked")
	public static List<String> listOfHashtagsFromDB() {
		Transaction tx = null;
		List<String> result = null;

		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
			tx = session.beginTransaction();

			result = session.createQuery("SELECT hashtag FROM Hashtag").list();

			tx.commit();
		} catch(HibernateException e){
			if(tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally{
			session.close();
		}

		return result;
	}

	/**
	 * Deletes a picture with the given ID from DB
	 * @param id
	 */
	public static void removePictureDataFromDB(String id){
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
			tx = session.beginTransaction();

			Query q = session.createQuery("DELETE FROM PictureData WHERE id=\'" + id + "\'");
			q.executeUpdate();

			tx.commit();
		} catch(HibernateException e){
			if (tx!=null) 
				tx.rollback();
			e.printStackTrace();
		} finally{
			session.close();
		}
	}

	/**
	 * Delete Hashtags from DB with a given name
	 * @param hashName
	 */
	public static void removeHashtagFromDB(String hashName){
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
			tx = session.beginTransaction();

			String s = "DELETE FROM Hashtag WHERE hashtag=\'" + hashName.toLowerCase() + "\'";

			Query q = session.createQuery(s);
			q.executeUpdate();

			tx.commit();
		} catch(HibernateException e){
			if (tx!=null) 
				tx.rollback();
			e.printStackTrace();
		} finally{
			session.close();
		}
	}

	/**
	 * Deletes pictures without any hashtags from DB, given that they are not flagged.
	 */
	public static void removePicturesWithoutHashTagFromDB(){
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

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

			tx.commit();
		} catch(HibernateException e){
			if (tx!=null) 
				tx.rollback();
			e.printStackTrace();
		} finally{
			session.close();
		}
	}
	
	/**
	 * Flags a picture with the given ID
	 * @param picID
	 */
	public static void setRemoveFlag(String picID){
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
			tx = session.beginTransaction();

			String s = "update PictureData set removeFlag = :newFlag where id = :picID";
			Query q = session.createQuery(s);
			q.setBoolean("newFlag", true);
			q.setString("picID", picID);

			q.executeUpdate();

			tx.commit();
		} catch(HibernateException e){
			if (tx!=null) 
				tx.rollback();
			e.printStackTrace();
		} finally{
			session.close();
		}
	}
}
