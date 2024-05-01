package com.incapp.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.incapp.beans.Garage;
import com.incapp.dao.GarageRepository;

@Service
public class GarageServiceImpl implements GarageService{

	@Autowired
	GarageRepository garageRepo;
	
	@Override
	public String addGarage(Garage g,MultipartFile photo1,MultipartFile photo2) {
		return garageRepo.addGarage(g, photo1, photo2);
	}

	@Override
	public List<Garage> garageLogin(String email, String password) {
		return garageRepo.garageLogin(email, password);
	}

	@Override
	public List<Garage> getAllGarageByStatus(String status) {
		return garageRepo.getAllGarageByStatus(status);
	}

	@Override
	public List<Garage> getAllGarageByStateCity(String state, String city) {
		return garageRepo.getAllGarageByStateCity(state, city);
	}

	@Override
	public String addGarageService(String email, String service) {
		return garageRepo.addGarageService(email, service);
	}

	@Override
	public List<String> getGarageAllService(String email) {
		return garageRepo.getGarageAllService(email);
	}

	@Override
	public String updateGarageStatus(String email, String status) {
		return garageRepo.updateGarageStatus(email, status);
	}

	@Override
	public byte[] getPhoto1(String email) {
		return garageRepo.getPhoto1(email);
	}

	@Override
	public byte[] getPhoto2(String email) {
		return garageRepo.getPhoto2(email);
	}

	@Override
	public Garage getGarageByEmail(String email) {
		return garageRepo.getGarageByEmail(email);
	}

	@Override
	public String ChangeStatusGarageOwner(String email, String status) {
		return garageRepo.ChangeStatusGarageOwner(email, status);
	}
	
	
}
