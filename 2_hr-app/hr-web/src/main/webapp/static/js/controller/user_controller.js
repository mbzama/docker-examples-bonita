'use strict';

App.controller('UserController', ['$scope', 'UserService', function($scope, UserService) {
	var self = this;
	self.user={id:null,username:'',address:'',email:''};
	self.users=[];
	$scope.transactions=[];
	
	self.refreshResults = function(){
		self.fetchAllTransactions();
		self.fetchAllUsers();
	};
	
	self.fetchAllTransactions = function(){
		UserService.fetchAllTransactions()
		.then(
				function(d) {
					$scope.transactions = d;
				},
				function(errResponse){
					console.error('Error while fetching Transactions: '+errResponse);
				}
		);
	};
	
	self.fetchAllUsers = function(){
		UserService.fetchAllUsers()
		.then(
				function(d) {
					self.users = d;
				},
				function(errResponse){
					console.error('Error while fetching Users: '+errResponse);
				}
		);
	};

	self.createUser = function(user){
		UserService.createUser(user)
		.then(
				self.refreshResults	
		);
		
	};

	self.updateUser = function(user, id){
		UserService.updateUser(user, id)
		.then(
				self.refreshResults
		);
				
		
	};

	self.deleteUser = function(id){
		UserService.deleteUser(id)
		.then(
				self.refreshResults
		);
		
	};

	self.fetchAllUsers();
	self.fetchAllTransactions();

	self.submit = function() {
		if(self.user.id==null){
			self.createUser(self.user);
		}else{
			self.updateUser(self.user, self.user.id);
		}
		self.reset();
	};

	self.edit = function(id){
		for(var i = 0; i < self.users.length; i++){
			if(self.users[i].id === id) {
				self.user = angular.copy(self.users[i]);
				break;
			}
		}
	};

	self.remove = function(id){
		if(self.user.id === id) {
			self.reset();
		}
		self.deleteUser(id);
	};


	self.reset = function(){
		self.user={id:null,username:'',address:'',email:''};
		$scope.myForm.$setPristine(); //reset Form
	};

}]);
