package com.incapp.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.incapp.beans.Enquiry;
import com.incapp.beans.Garage;

public interface AdminService {

	List<String> adminLogin(String id,String password);
	List<Enquiry> getAllEnquires();
	String addEnquiry(Enquiry e);
}
