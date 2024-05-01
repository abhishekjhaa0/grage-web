package com.incapp.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.incapp.beans.Enquiry;
import com.incapp.beans.Garage;

@Controller
public class AdminController {
	String URL = "http://localhost:5555/";
	RestTemplate restTemplate = new RestTemplate();

//	@ModelAttribute
//	public void getAllBookNames(Model m) {
//		String API= "bookNames";
//		List<String> bn=restTemplate.getForObject(URL+API, List.class);
//		m.addAttribute("bookNames",bn );
//	}

	@RequestMapping("/")
	public String home() {
		return "index";
	}

	@RequestMapping("/Logout")
	public String Logout(HttpSession session) {
		session.invalidate();
		return "index";
	}

	@PostMapping("/addEnquiry")
	public String addEnquiry(@ModelAttribute Enquiry e, Model m) {
		String API = "/addEnquiry";

		HttpHeaders h = new HttpHeaders();
		HttpEntity<Enquiry> requestEntity = new HttpEntity(e, h);
		ResponseEntity<String> result = restTemplate.postForEntity(URL + API, requestEntity, String.class);
		String r = result.getBody();
		m.addAttribute("Result", r);
		return "index";
	}

	@PostMapping("/adminLogin")
	public String adminLogin(String id, String password, Model m, HttpSession session) {
		String API = "/adminLogin" + "/" + id + "/" + password;
		HttpEntity requestEntity = new HttpEntity(null);
		ResponseEntity<String> result = restTemplate.postForEntity(URL + API, requestEntity, String.class);
		String r = result.getBody();
		if (r != null) {
			session.setAttribute("id", r);

			API = "/getAllGarageByStatus" + "/pending";
			Garage[] allGarages = restTemplate.getForObject(URL + API, Garage[].class);
			m.addAttribute("allGarages", allGarages);
			return "AdminHome";
		} else {
			m.addAttribute("adminLoginResult", "Invalid Credentials!");
			return "Admin";
		}
	}

	@GetMapping("/AdminHome")
	public String AdminHome(Model m, HttpSession session) {
		String id = (String) session.getAttribute("id");
		if (id != null) {
			String API = "/getAllGarageByStatus" + "/pending";
			Garage[] allGarages = restTemplate.getForObject(URL + API, Garage[].class);
			m.addAttribute("allGarages", allGarages);
			return "AdminHome";
		} else {
			m.addAttribute("Result", "Plz Login First");
			return "index";
		}
	}

	@GetMapping("/AdminAcceptedGarage")
	public String AdminAcceptedGarage(Model m, HttpSession session) {
		String id = (String) session.getAttribute("id");
		if (id != null) {
			String API = "/getAllGarageByStatus" + "/accepted";
			Garage[] allGarages = restTemplate.getForObject(URL + API, Garage[].class);
			m.addAttribute("allGarages", allGarages);
			return "AdminAcceptedGarage";
		} else {
			m.addAttribute("Result", "Plz Login First");
			return "index";
		}
	}

	@GetMapping("/AdminRejectedGarage")
	public String AdminRejectedGarage(Model m, HttpSession session) {
		String id = (String) session.getAttribute("id");
		if (id != null) {
			String API = "/getAllGarageByStatus" + "/rejected";
			Garage[] allGarages = restTemplate.getForObject(URL + API, Garage[].class);
			m.addAttribute("allGarages", allGarages);
			return "AdminRejectedGarage";
		} else {
			m.addAttribute("Result", "Plz Login First");
			return "index";
		}
	}
	
	@GetMapping("/AdminEnquiry")
	public String AdminEnquiry(Model m, HttpSession session) {
		String id = (String) session.getAttribute("id");
		if (id != null) {
			String API = "/getAllEnquires";
			Enquiry[] allEnquiry = restTemplate.getForObject(URL + API, Enquiry[].class);
			m.addAttribute("allEnquiry", allEnquiry);
			return "AdminEnquiry";
		} else {
			m.addAttribute("Result", "Plz Login First");
			return "index";
		}
	}
	
	@GetMapping("/AdminGarageDetails")
	public String AdminGarageDetails(String email,Model m, HttpSession session) {
		String id = (String) session.getAttribute("id");
		if (id != null) {
			String API = "/getGarageByEmail" +  "/" + email ;
			Garage garages = restTemplate.getForObject(URL + API, Garage.class);
			m.addAttribute("g", garages);
			return "AdminGarageDetails";
		} else {
			m.addAttribute("Result", "Plz Login First!");
			return "index";
		}
	}
	@GetMapping("/ChangeStatusGarageOwner")
	public String ChangeStatusGarageOwner(String email,String status,Model m, HttpSession session) {
		String id = (String) session.getAttribute("id");
		if (id != null) {
			String API = "/ChangeStatusGarageOwner" +  "/" + email+  "/" + status ;
			String result = restTemplate.getForObject(URL + API, String.class);
			m.addAttribute("Result", result);
			return "AdminHome";
		} else {
			m.addAttribute("Result", "Plz Login First!");
			return "index";
		}
	}
}
