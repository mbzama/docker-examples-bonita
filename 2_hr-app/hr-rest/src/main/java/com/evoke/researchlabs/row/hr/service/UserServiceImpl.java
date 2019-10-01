package com.evoke.researchlabs.row.hr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evoke.researchlabs.row.hr.dao.UserDao;
import com.evoke.researchlabs.row.hr.domain.Transaction;
import com.evoke.researchlabs.row.hr.domain.User;

/**
 * 
 * @author Zama
 *
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	
	@Override
	public List<Transaction> getTransactions() {
		return userDao.getTransactions();
	}
	
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	public User getUser(Integer id) {
		return userDao.getUser(id);
	}

	public boolean isUserExist(User user) {
		return userDao.isUserExist(user);
	}

	public boolean createUser(User user) {
		return userDao.createUser(user);
	}

	public boolean updateUser(User user) {
		return userDao.updateUser(user);
	}

	public boolean deleteUser(User user) {
		return userDao.deleteUser(user);
	}

	public boolean deleteAllUsers() {
		return userDao.deleteAllUsers();
	}

	public boolean saveTransaction(Transaction transaction) {
		return userDao.saveTransaction(transaction);
	}
}
