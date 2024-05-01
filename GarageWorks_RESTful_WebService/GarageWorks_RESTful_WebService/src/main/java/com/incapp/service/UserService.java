package com.incapp.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.incapp.beans.Garage;
import com.incapp.beans.User;

public interface UserService {

	String addUser(User u,MultipartFile photo);
	List<User> userLogin(String email,String password);
	String userPhotoUpload(String email,MultipartFile photo);
	String userChangePassword(String email,String o_password,String n_password,String c_password);
	byte[] getPhoto(String email);
}
