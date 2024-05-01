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
import com.incapp.beans.User;

@Controller
public class UserController {
	String URL = "http://localhost:5555/";
	RestTemplate restTemplate = new RestTemplate();
	
	@PostMapping("/addUser")
	public String addUser(@ModelAttribute User u,@RequestPart("photo") MultipartFile photo, Model m) {
		String API="/addUser";
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		LinkedMultiValueMap<String, Object> data=new LinkedMultiValueMap<>();
		data.add("photo", convert(photo));
		data.add("user", u);
		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity=new HttpEntity(data,header);
		
		ResponseEntity<String> result=restTemplate.postForEntity(URL+API,requestEntity, String.class);
		String r=result.getBody();
		if(r.equalsIgnoreCase("success")) {
			m.addAttribute("userResult", "User Added Successfully!");
		}else {
			m.addAttribute("userResult", "User Already Exist!");
		}
		return "User";
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
	
	
	
	@PostMapping("/userLogin")
	public String userLogin(String email, String password, Model m, HttpSession session) {
		String API = "/userLogin" + "/" + email + "/" + password;
		HttpEntity requestEntity = new HttpEntity(null);
		ResponseEntity<User> result = restTemplate.postForEntity(URL + API, requestEntity, User.class);
		User u = result.getBody();
		if (u != null) {
			session.setAttribute("uname", u.getName());
			session.setAttribute("email", email);
			return "UserHome";
		} else {
			m.addAttribute("userResult", "Invalid Credentials!");
			return "User";
		}
	}
	
	
	@GetMapping("/UserHome")
	public String UserHome(Model m, HttpSession session) {
		String name = (String) session.getAttribute("uname");
		if (name != null) {
			return "UserHome";
		} else {
			m.addAttribute("userResult", "Plz Login First!");
			return "User";
		}
	}
	
	@RequestMapping("/getPhoto")
	public void getPhoto(String email,HttpServletResponse response) {
		try {
			String API="/getPhoto"+"/" + email;
			byte[] b=restTemplate.getForObject(URL+API,byte[].class);
			response.getOutputStream().write(b);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/getAllGarageByStateCity")
	public String getAllGarageByStateCity(String state,String city,Model m, HttpSession session) {
		String name = (String) session.getAttribute("uname");
		if (name != null) {
			String API = "/getAllGarageByStateCity" +  "/" + state + "/" + city;
			Garage[] allGarages = restTemplate.getForObject(URL + API, Garage[].class);
			m.addAttribute("allGarages", allGarages);
			return "UserHome";
		} else {
			m.addAttribute("userResult", "Plz Login First!");
			return "User";
		}
	}
	
	@GetMapping("/GarageDetails")
	public String GarageDetails(String email,Model m, HttpSession session) {
		String name = (String) session.getAttribute("uname");
		if (name != null) {
			String API = "/getGarageByEmail" +  "/" + email ;
			Garage garages = restTemplate.getForObject(URL + API, Garage.class);
			m.addAttribute("g", garages);
			return "GarageDetails";
		} else {
			m.addAttribute("userResult", "Plz Login First!");
			return "User";
		}
	}
}
