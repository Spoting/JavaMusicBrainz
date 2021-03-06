package db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.parser.ParseException;

import basics.Artist;
import basics.Person;
import files.APIWrapper;

public class HibernateRun {

	public static void main(String[] args)
			throws ParseException, IOException, NullPointerException, java.text.ParseException {
		// TODO Auto-generated method stub
		ArrayList<Artist> tmpArt = new ArrayList();
		tmpArt = APIWrapper.getArtistsFromTags("fred", "");

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		session.beginTransaction();
		System.out.println("Start");
		for (Artist x : tmpArt) {
			System.out.println(x.toString());
			// if (x.getClass().toString().equals("class basics.Person")) {
			// System.out.println("Start");
			// session.beginTransaction();

			session.save(x);

			// session.getTransaction().commit();
			// }
		}
		// session.save(p);

		session.getTransaction().commit();
		session.close();
		///////////////////////////////////////////////////////////
		System.out.println("Query");
		
		session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		try {

			List arts = session.createQuery("from Artist where artype='Group'").list();
			for (Iterator iterator = arts.iterator(); iterator.hasNext();) {
				Artist art = (Artist) iterator.next();
				System.out.println();
				System.out.println(art);
				System.out.println();
				System.out.println();
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		System.out.println("End");
	}

}
