package dal;

import java.util.List;

/**
 * @author Andreas Bjerga & Marius Vasshus
 */


import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

public class DatabaseHandler {

	public static void addPictureToDB(PictureData pic){
		List<PictureData> resultPic = null;
		List<Hashtag> resultHash = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		try{
			tx = session.beginTransaction();
			
			Set<Hashtag> newHashSet = pic.getHashtags();
			for(Hashtag h : newHashSet){
				resultHash = DatabaseHandler.returnHashtagIfAlreadyExists(h);
//				Hibernate.initialize(resultHash.get(0).getPictures());
				if(resultHash.size() == 1){
					System.out.println("HAAAALLLOOOOOOO" + resultHash.get(0));
					pic.remHashtag(h);
//					pic.addHashtag(resultHash.get(0));
					resultHash.get(0).addPicToHashtag(pic);
					session.saveOrUpdate(resultHash);
				}
			}

			resultPic = DatabaseHandler.returnPictureIfAlreadyExists(pic);
			if(resultPic.size() == 1){
				Set<Hashtag> hashtagsFromDB = resultPic.get(0).getHashtags();
				newHashSet.addAll(hashtagsFromDB);
				pic.setHashtags(newHashSet);
			}
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
	
	public static List<PictureData> returnPictureIfAlreadyExists(PictureData pic){
		List<PictureData> result = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		try{
			tx = session.beginTransaction();

			result = session.createQuery("from PictureData where id=\'" + pic.getId() + "\'").list();
			tx.commit();
		} catch(HibernateException e){
			if (tx!=null) 
				tx.rollback();
			e.printStackTrace();
//		} finally{
//			session.close();
		}
		return result;
	}

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
	
	public static List<Hashtag> returnHashtagIfAlreadyExists(Hashtag h){
		List<Hashtag> result = null;
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		try{
			tx = session.beginTransaction();

			result = session.createQuery("from Hashtag where hashtag=\'" + h.getHashtag() + "\'").list();

			tx.commit();
		} catch(HibernateException e){
			if (tx!=null) 
				tx.rollback();
			e.printStackTrace();
//		} finally{
//			session.close();
		}
		return result;
	}

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
