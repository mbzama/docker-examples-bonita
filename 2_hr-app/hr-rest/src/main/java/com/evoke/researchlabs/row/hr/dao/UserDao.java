package com.evoke.researchlabs.row.hr.dao;

import java.util.List;

import com.evoke.researchlabs.row.hr.domain.Transaction;
import com.evoke.researchlabs.row.hr.domain.User;

/**
 * 
 * @author Zama
 *
 */
public interface UserDao {
	public boolean saveTransaction(Transaction transaction);
	public List<Transaction> getTransactions();
	public List<User> getAllUsers();
	public User getUser(Integer id);
	public boolean isUserExist(User user);
	public boolean createUser(User user);
	public boolean updateUser(User user);
	public boolean deleteUser(User user);
	public boolean deleteAllUsers();
}
