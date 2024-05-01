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
import com.incapp.service.GarageService;

@RestController
public class GarageController {
	
	@Autowired
	GarageService garageService;
	
	@PostMapping(value = "/addGarage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String addGarage(@RequestPart("garage") Garage g,@RequestPart("photo1") MultipartFile photo1,@RequestPart("photo2") MultipartFile photo2) {
		String r=garageService.addGarage(g, photo1, photo2);
		return r;
	}
	
	@PostMapping(value = "/garageLogin/{email}/{password}")
	public ResponseEntity<Garage> garageLogin(@PathVariable("email") String email,@PathVariable("password") String password) {
		List<Garage> l=garageService.garageLogin(email, password);
		Garage g;
		if(l.isEmpty()) {
			g=null;
		}else {
			g=l.get(0);
		}
		return new ResponseEntity<Garage>(g, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAllGarageByStatus/{status}")
	public List<Garage> getAllGarageByStatus(@PathVariable("status") String status) {
		return garageService.getAllGarageByStatus(status);
	}
	@GetMapping(value = "/getAllGarageByStateCity/{state}/{city}")
	public List<Garage> getAllGarageByStateCity(@PathVariable("state") String state,@PathVariable("city") String city) {
		return garageService.getAllGarageByStateCity(state, city);
	}
	@GetMapping(value = "/getGarageByEmail/{email}")
	public Garage getGarageByEmail(@PathVariable("email") String email) {
		return garageService.getGarageByEmail(email);
	}
	@GetMapping(value = "/getGarageAllService/{email}")
	public List<String> getGarageAllService(@PathVariable("email") String email) {
		return garageService.getGarageAllService(email);
	}
	@PostMapping(value = "/addGarageService/{email}/{service}")
	public String addGarageService(@PathVariable String email,@PathVariable String service) {
		String r=garageService.addGarageService(email, service);
		return r;
	}
	@PostMapping(value = "/updateGarageStatus/{email}/{status}")
	public String updateGarageStatus(@PathVariable String email,@PathVariable String status) {
		String r=garageService.updateGarageStatus(email, status);
		return r;
	}
	
	@RequestMapping(value = "/getGaragePhoto1/{email}")
	public ResponseEntity<byte[]> getPhoto1(@PathVariable String email){
		byte[] b=garageService.getPhoto1(email);
		if(b!=null) {
			return new ResponseEntity<byte[]>(b, HttpStatus.OK);
		}else {
			return null;
		}
	}
	@RequestMapping(value = "/getGaragePhoto2/{email}")
	public ResponseEntity<byte[]> getPhoto2(@PathVariable String email){
		byte[] b=garageService.getPhoto2(email);
		if(b!=null) {
			return new ResponseEntity<byte[]>(b, HttpStatus.OK);
		}else {
			return null;
		}
	}
	
	@GetMapping(value = "/ChangeStatusGarageOwner/{email}/{status}")
	public String ChangeStatusGarageOwner(@PathVariable String email,@PathVariable String status) {
		String r=garageService.ChangeStatusGarageOwner(email, status);
		return r;
	}
	
}
