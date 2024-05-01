package com.incapp.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.incapp.beans.Garage;

public interface GarageService {

	String addGarage(Garage g,MultipartFile photo1,MultipartFile photo2);
	List<Garage> garageLogin(String email,String password);
	List<Garage> getAllGarageByStatus(String status);
	List<Garage> getAllGarageByStateCity(String state,String city);
	String addGarageService(String email,String service);
	List<String> getGarageAllService(String email);
	String updateGarageStatus(String email,String status);
	byte[] getPhoto1(String email);
	byte[] getPhoto2(String email);
	
	Garage getGarageByEmail(String email);
	String ChangeStatusGarageOwner(String email,String status);
}
