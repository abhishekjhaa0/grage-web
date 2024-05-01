package com.incapp.controllers;


import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.incapp.beans.Garage;
import com.incapp.beans.User;
import com.incapp.service.GarageService;
import com.incapp.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping(value = "/addUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String addUser(@RequestPart("user") User u,@RequestPart("photo") MultipartFile photo) {
		String r=userService.addUser(u, photo);
		return r;
	}
	
	@PostMapping(value = "/userLogin/{email}/{password}")
	public ResponseEntity<User> userLogin(@PathVariable("email") String email,@PathVariable("password") String password) {
		List<User> l= userService.userLogin(email, password);
		User u;
		if(l.isEmpty()) {
			u=null;
		}else {
			u=l.get(0);
		}
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}
	
	@PostMapping(value = "/userPhotoUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String userPhotoUpload(@RequestPart("email") String email,@RequestPart("photo") MultipartFile photo) {
		String r=userService.userPhotoUpload(email, photo);
		return r;
	}
	
	@PostMapping(value = "/userChangePassword/{email}/{o_password}/{n_password}/{c_password}")
	public String userChangePassword(@PathVariable("email") String email,@PathVariable("o_password") String o_password,@PathVariable("n_password") String n_password,@PathVariable("c_password") String c_password) {
		return userService.userChangePassword(email, o_password, n_password, c_password);
	}
	
	@RequestMapping(value = "/getPhoto/{email}")
	public ResponseEntity<byte[]> getPhoto(@PathVariable String email){
		byte[] b=userService.getPhoto(email);
		if(b!=null) {
			return new ResponseEntity<byte[]>(b, HttpStatus.OK);
		}else {
			return null;
		}
	}
	
}
