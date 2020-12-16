package com.evoke.researchlabs.row.hr.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.evoke.researchlabs.row.hr.domain.Transaction;
import com.evoke.researchlabs.row.hr.domain.User;
import com.evoke.researchlabs.row.hr.service.UserService;
import com.evoke.researchlabs.row.hr.util.BonitaUtil;

/**
 * 
 * @author Zama
 *
 */
@RestController
@RequestMapping("/hrrest")
public class UserRestController {
	@Autowired
	private UserService userService; 
	private static final Logger logger = LogManager.getLogger(UserRestController.class);
	
	@RequestMapping(value = "/welcome" , method = RequestMethod.GET,headers="Accept=application/json")
	public String ping() {
		String message = "Welcome to Docker Training - {USER_ID}_{NAME}";
		logger.info("/welcome: "+message);
		return message;
	}
	
	@RequestMapping(value = "/transactions" , method = RequestMethod.GET,headers="Accept=application/json")
	public List<Transaction> getAllTransactions() {
		List<Transaction> transactions = userService.getTransactions();
		logger.info(transactions);
		return transactions;
	}

	@RequestMapping(value = "/users" , method = RequestMethod.GET,headers="Accept=application/json")
	public List<User> getAllUsers() {
		List<User> users = userService.getAllUsers();
		logger.info(users);
		return users;
	}

	@RequestMapping(value = "/user/{id}" , method = RequestMethod.GET,headers="Accept=application/json")
	public User getUser(@PathVariable("id") int id) {
		logger.info("Fetching User with id " + id);
		User user = userService.getUser(id);
		logger.info("User with id " + id + " not found");
		return user;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		logTransaction(user, "CREATE_CASE");

		logger.info("Creating Case in Bonita for: " + user);

		String response = BonitaUtil.createCase(user);

		logger.info("createUser: "+response);

		return getStatusMessage("success");
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		String status = "fail";
		logTransaction(user, "UPDATE_CASE");

		logger.info("Updating User: " + user);
		if (userService.updateUser(user)) {
			status = "success";
		}
		return getStatusMessage(status);
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String deleteUser(@RequestBody String id){
		String status = "fail";
		logTransaction(userService.getUser(Integer.valueOf(id)), "REMOVE_CASE");

		logger.info("Deleting User with Id: " + id);
		if (userService.deleteUser(new User(Integer.valueOf(id),null,null,null))) {
			status = "success";
		}
		return getStatusMessage(status);
	}

	private void logTransaction(User user, String requestType) {
		logger.info("Saving Transaction: " + user);
		Transaction transaction = new Transaction();
		transaction.setUsername(user.getUsername());
		transaction.setEmail(user.getEmail());
		List<String> names = Arrays.asList("zama","admin","manager");
		transaction.setRequestedBy(names.get(new Random().nextInt(names.size())));
		transaction.setAddress(user.getAddress());
		transaction.setRequestType(requestType);
		userService.saveTransaction(transaction);
	}

	private String getStatusMessage(String status) {
		return "[{\"status\":\""+ status+"\"}]";
	}
}
