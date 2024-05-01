package com.incapp.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.incapp.beans.Garage;
import com.incapp.beans.User;
import com.incapp.dao.GarageRepository;
import com.incapp.dao.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepo;

	@Override
	public String addUser(User u, MultipartFile photo) {
		return userRepo.addUser(u, photo);
	}

	@Override
	public List<User> userLogin(String email, String password) {
		return userRepo.userLogin(email, password);
	}

	@Override
	public String userPhotoUpload(String email, MultipartFile photo) {
		return userRepo.userPhotoUpload(email, photo);
	}

	@Override
	public String userChangePassword(String email, String o_password, String n_password, String c_password) {
		return userRepo.userChangePassword(email, o_password, n_password, c_password);
	}

	@Override
	public byte[] getPhoto(String email) {
		return userRepo.getPhoto(email);
	}
	

}
