package com.geppetto.mailchimp.daoimpl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.geppetto.mailchimp.dao.SubscriberDao;
import com.geppetto.mailchimp.dto.Subscriber;

/**
 * @author Suresh Palanisamy<br>
 *         <p>
 *         Date Created: 28-July-2015
 *         </p>
 *
 */

@Repository
@Transactional
public class SubscriberDaoImpl implements SubscriberDao {

	private static final Logger LOG = LoggerFactory.getLogger(SubscriberDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Value("${findAllSubscribers}")
	private String findAllSubscribers;

	@Value("${findSubscriber}")
	private String findSubscriber;

	@Value("${createSubscriber}")
	private String createSubscriber;

	@Value("${updateSubscriber}")
	private String updateSubscriber;

	@Value("${deleteSubscriber}")
	private String deleteSubscriber;

	@Value("${lastIndex}")
	private String lastIndex;

	@Override
	@Transactional
	public ArrayList<Subscriber> findAllSubscribers() throws Exception {
		try {
			LOG.debug("Find all subscribers method has been initialized in the dao layer!");

			Query sql = entityManager.createNativeQuery(findAllSubscribers, "SubscriberResultSet");
			@SuppressWarnings("unchecked")
			List<Subscriber> subscribersList = sql.getResultList();

			if (subscribersList.size() > 0) {
				LOG.debug("All subscribers are found!. Total no. of subscribers is: " + subscribersList.size());
			} else {
				LOG.debug("Subscribers are not found!");
			}
			return (ArrayList<Subscriber>) subscribersList;
		} catch (Exception e) {
			LOG.debug("Exception in dao layer: " + e.getMessage());
		}
		return null;
	}

	@Override
	@Transactional
	public Subscriber findSubscriber(long subscriberSno) throws Exception {
		try {
			LOG.debug("Find subscriber method has been initialized in the dao layer!");

			Query sql = entityManager.createNativeQuery(findSubscriber, Subscriber.class).setParameter("subscriber_sno",
					subscriberSno);
			Subscriber subscriber = (Subscriber) sql.getSingleResult();

			if (subscriber != null) {
				LOG.debug("The subscriber was found!");
			} else {
				LOG.debug("The subscriber was not found!");
			}

			return subscriber;
		} catch (Exception e) {
			LOG.debug("Exception in dao layer: " + e.getMessage());
		}
		return null;
	}

	@Override
	@Transactional
	public Subscriber createSubscriber(Subscriber subscriber) throws Exception {
		try {
			LOG.debug("Create subscriber method has been initialized in the dao layer!");

			Query sql = entityManager.createNativeQuery(createSubscriber)
					.setParameter("first_name", subscriber.getFirstName())
					.setParameter("last_name", subscriber.getLastName())
					.setParameter("email_address", subscriber.getEmailAddress());
			sql.executeUpdate();

			sql = entityManager.createNativeQuery(lastIndex);
			BigInteger subscriberSno = (BigInteger) sql.getSingleResult();

			subscriber.setSubscriberSno(Long.valueOf(subscriberSno.toString()));

			if (subscriber.getSubscriberSno() != 0L) {
				LOG.debug("Subscriber was created successfully!. Created Subscriber's ID is: "
						+ subscriber.getSubscriberSno());
			} else {
				LOG.debug("Subscriber creation was failed!");
			}
		} catch (Exception e) {
			LOG.debug("Exception in dao layer: " + e.getMessage());
		}
		return subscriber;
	}

	@Override
	@Transactional
	public Subscriber updateSubscriber(Subscriber subscriber) throws Exception {
		try {
			LOG.debug("Update subscriber method has been initialized in the dao layer!");

			Query sql = entityManager.createNativeQuery(updateSubscriber)
					.setParameter("first_name", subscriber.getFirstName())
					.setParameter("last_name", subscriber.getLastName())
					.setParameter("email_address", subscriber.getEmailAddress())
					.setParameter("subscriber_sno", subscriber.getSubscriberSno());
			sql.executeUpdate();

			LOG.debug("Subscriber was updated successfully!. Updated Subscriber's ID is: "
					+ subscriber.getSubscriberSno());
		} catch (Exception e) {
			LOG.debug("Exception in dao layer: " + e.getMessage());
		}
		return subscriber;
	}

	@Override
	@Transactional
	public boolean deleteSubscriber(long subscriberSno) throws Exception {
		try {
			LOG.debug("Delete subscriber method has been initialized in the dao layer!");

			Query sql = entityManager.createNativeQuery(deleteSubscriber).setParameter("subscriber_sno", subscriberSno);

			int result = sql.executeUpdate();

			if (result == 1) {
				LOG.debug("Subscriber was deleted successfully!. Deleted Subscriber's ID is: " + subscriberSno);
				return true;
			} else {
				LOG.debug("Subscriber deletion was failed!");
				return false;
			}
		} catch (Exception e) {
			LOG.debug("Exception in dao layer: " + e.getMessage());
		}
		return false;
	}
}