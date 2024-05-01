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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.incapp.beans.Enquiry;
import com.incapp.beans.Garage;
import com.incapp.service.AdminService;
import com.incapp.service.GarageService;

@RestController
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@PostMapping(value = "/addEnquiry")
	public String addEnquiry(@RequestBody Enquiry e) {
		String r=adminService.addEnquiry(e);
		return r;
	}
	
	@PostMapping(value = "/adminLogin/{id}/{password}")
	public List<String> adminLogin(@PathVariable("id") String id,@PathVariable("password") String password) {
		return adminService.adminLogin(id, password);
	}
	
	@GetMapping(value = "/getAllEnquires")
	public List<Enquiry> getAllEnquires() {
		return adminService.getAllEnquires();
	}
}
