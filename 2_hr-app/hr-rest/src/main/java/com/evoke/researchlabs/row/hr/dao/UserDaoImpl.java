package com.evoke.researchlabs.row.hr.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Repository;

import com.evoke.researchlabs.row.hr.domain.Transaction;
import com.evoke.researchlabs.row.hr.domain.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

/**
 * 
 * @author Zama
 *
 */

@Repository("userDao")
public class UserDaoImpl extends AbstractDao implements UserDao{
	private static final Logger logger = Logger.getLogger(UserDaoImpl.class);
	
	public boolean saveTransaction(Transaction transaction){
		try {
			DB db = getMongoDB();
			DBCollection table = db.getCollection("transaction");
			BasicDBObject document = new BasicDBObject();
			document.put("username", transaction.getUsername());
			document.put("email", transaction.getEmail());
			document.put("requestedBy", transaction.getRequestedBy());
			document.put("requestType", transaction.getRequestType());
			document.put("requestTime", new Date());
			document.put("address", transaction.getAddress());
			table.insert(document);
		} catch (IOException e) {
			logger.error("Error while inserting data in MongoDB.", e);
		}
		
		return true;
	}
	
	public List<Transaction> getTransactions() {
		List<Transaction> transactions = new ArrayList<>();
		try {
			DBCollection collection = getMongoDB().getCollection("transaction");
			DBCursor dbCursor = collection.find();
			
			while(dbCursor.hasNext()) {
				DBObject nextElement = dbCursor.next();
				Transaction t = new Transaction();
				t.setId(String.valueOf(nextElement.get("_id")));
				t.setRequestType(String.valueOf(nextElement.get("requestType")));
				t.setUsername(String.valueOf(nextElement.get("username")));
				t.setEmail(String.valueOf(nextElement.get("email")));
				t.setRequestedBy(String.valueOf(nextElement.get("requestedBy")));
				t.setRequestTime(String.valueOf(nextElement.get("requestTime")));
				t.setAddress(String.valueOf(nextElement.get("address")));
				transactions.add(t);
			}
		} catch (IOException e) {
			logger.error("Error while getting transactions data from MongoDB.", e);
		}
		return transactions;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.addOrder(Order.desc("id"));
		return (List<User>) criteria.list();
	}

	public User getUser(Integer id) {
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("id",id));
		return (User) criteria.uniqueResult();
	}

	public boolean createUser(User user) {
		user.setId(getMaxId());
		getSession().save(user);
		
		return true;
	}

	private Integer getMaxId(){
		Integer id = 1;
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.addOrder(Order.desc("id"));
		List list = criteria.list();

		if(list.size()>0){
			id = ((User)list.get(0)).getId()+1;
		}
		return id;
	}
	
	public boolean updateUser(User user) {
		getSession().update(user);
		return true;	
	}

	public boolean deleteUser(User user) {
		getSession().delete(user);
		return true;
	}

	public boolean isUserExist(User user) {
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("username", user.getUsername()));
		if(criteria.list().size()>0){
			return true;
		}else{
			return false;
		}
	}

	public boolean deleteAllUsers() {
		return false;
	}
	
	private DB getMongoDB() throws IOException {
		Resource resource = new ClassPathResource("dev.properties");
		Properties props = PropertiesLoaderUtils.loadProperties(resource);
		MongoClient mongo = new MongoClient(props.getProperty("database.mongo.url"), Integer.valueOf(props.getProperty("database.mongo.port")));
		return mongo.getDB("transactionData");
	}
}
