package com.incapp.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.incapp.beans.Enquiry;
import com.incapp.beans.Garage;

@Controller
public class GarageController {
	String URL = "http://localhost:5555/";
	RestTemplate restTemplate = new RestTemplate();
	
	@PostMapping("/AddGarageOwner")
	public String AddGarageOwner(@ModelAttribute Garage g,@RequestPart("photo1") MultipartFile photo1,@RequestPart("photo2") MultipartFile photo2, Model m) {
		String API="/addGarage";
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		LinkedMultiValueMap<String, Object> data=new LinkedMultiValueMap<>();
		data.add("photo1", convert(photo1));
		data.add("photo2", convert(photo2));
		data.add("garage", g);
		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity=new HttpEntity(data,header);
		
		ResponseEntity<String> result=restTemplate.postForEntity(URL+API,requestEntity, String.class);
		String r=result.getBody();
		if(r.equalsIgnoreCase("success")) {
			m.addAttribute("garageResult", "Garage Added Successfully!");
		}else {
			m.addAttribute("garageResult", "Garage Already Exist!");
		}
		return "GarageOwner";
	}
	public static FileSystemResource convert(MultipartFile file) {
		File convFile=new File(file.getOriginalFilename());
		try {
			convFile.createNewFile();
			FileOutputStream fos=new FileOutputStream(convFile);
			fos.write(file.getBytes());
			fos.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return new FileSystemResource(convFile);
	}
	
	
	
	@PostMapping("/garageLogin")
	public String garageLogin(String email, String password, Model m, HttpSession session) {
		String API = "/garageLogin" + "/" + email + "/" + password;
		HttpEntity requestEntity = new HttpEntity(null);
		ResponseEntity<Garage> result = restTemplate.postForEntity(URL + API, requestEntity, Garage.class);
		Garage g = result.getBody();
		if (g != null) {
			session.setAttribute("name", g.getName());
			session.setAttribute("status", g.getStatus());
			session.setAttribute("email", email);
			return "GarageOwnerHome";
		} else {
			m.addAttribute("garageResult", "Invalid Credentials!");
			return "GarageOwner";
		}
	}
	@GetMapping("/GarageOwnerHome")
	public String GarageOwnerHome(Model m, HttpSession session) {
		String name = (String) session.getAttribute("name");
		if (name != null) {
			return "GarageOwnerHome";
		} else {
			m.addAttribute("garageResult", "Plz Login First!");
			return "GarageOwner";
		}
	}
	
	@PostMapping("/addGarageService")
	public String addGarageService(String email, String service, Model m) {
		String API = "/addGarageService"+ "/" + email + "/" + service;

		HttpHeaders h = new HttpHeaders();
		HttpEntity requestEntity = new HttpEntity(null);
		ResponseEntity<String> result = restTemplate.postForEntity(URL + API, requestEntity, String.class);
		String r = result.getBody();
		m.addAttribute("Result", r);
		return "GarageOwnerHome";
	}
	
	@GetMapping("/GarageOwnerServices")
	public String GarageOwnerServices(Model m, HttpSession session) {
		String name = (String) session.getAttribute("name");
		String email = (String) session.getAttribute("email");
		if (name != null) {
			
			String API = "/getGarageAllService"+ "/" + email;
			String[] allServices = restTemplate.getForObject(URL + API, String[].class);
			m.addAttribute("allServices", allServices);
			return "GarageOwnerServices";
		} else {
			m.addAttribute("garageResult", "Plz Login First!");
			return "GarageOwner";
		}
	}
	
	
	
	
	@RequestMapping("/getGaragePhoto1")
	public void getGaragePhoto1(String email,HttpServletResponse response) {
		try {
			String API="/getGaragePhoto1"+"/" + email;
			byte[] b=restTemplate.getForObject(URL+API,byte[].class);
			response.getOutputStream().write(b);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/getGaragePhoto2")
	public void getGaragePhoto2(String email,HttpServletResponse response) {
		try {
			String API="/getGaragePhoto2"+"/" + email;
			byte[] b=restTemplate.getForObject(URL+API,byte[].class);
			response.getOutputStream().write(b);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
