package com.incapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incapp.beans.Enquiry;
import com.incapp.dao.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	AdminRepository adminRepo;

	@Override
	public List<String> adminLogin(String id, String password) {
		return adminRepo.adminLogin(id, password);
	}

	@Override
	public List<Enquiry> getAllEnquires() {
		return adminRepo.getAllEnquires();
	}

	@Override
	public String addEnquiry(Enquiry e) {
		return adminRepo.addEnquiry(e);
	}

}
