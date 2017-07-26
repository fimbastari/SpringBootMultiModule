package com.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.domain.entity.User;
import com.domain.exception.CustomErrorType;
import com.service.UserService;


@RestController
@RequestMapping("/api")
public class UserApiRestController {

	@Autowired
	UserService userService; 

	// -------------------Retrieve All Users -----------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.GET)
//    @GetMapping("/user")
	public ResponseEntity<?> listAllUsers() {
		List<User> users = userService.findAllUsers();
		if (users.isEmpty()) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Users not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	// -------------------Retrieve Single User -----------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") long id) {
		User user = userService.findById(id);
		if (user == null) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("User with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// -------------------Create a User -----------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		userService.saveUser(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getIdUser()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a User -----------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {

		User currentUser = userService.findById(id);

		if (currentUser == null) {
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentUser.setName(user.getName());
		currentUser.setEmail(user.getEmail());
		currentUser.setSurname(user.getSurname());
		currentUser.setUsername(user.getUsername());
		currentUser.setRole(user.getRole());
		currentUser.setPhone(user.getPhone());
		currentUser.setPassword(user.getPassword());

		userService.updateUser(currentUser);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}

	// ------------------- Delete a User -----------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {

		User user = userService.findById(id);
		if (user == null) {
			// ResponseEntity<CustomErrorType> provare con questo!s
			return new ResponseEntity<CustomErrorType>(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		userService.deleteUserById(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Users -----------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteAllUsers() {

		userService.deleteAllUsers();
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}
}