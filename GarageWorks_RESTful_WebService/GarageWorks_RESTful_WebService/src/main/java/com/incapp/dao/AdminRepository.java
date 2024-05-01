package com.incapp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.incapp.beans.Enquiry;
import com.incapp.beans.Garage;

@Repository
public class AdminRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public List<String> adminLogin(String id,String password){
		class DataMapper implements RowMapper{
			public String mapRow(ResultSet rs,int rowNum)throws SQLException{
				return rs.getString("id");
			}
		}
		final String query ="select * from admin where id = ? and password=?";
		List<String> r=jdbcTemplate.query(query,new DataMapper(),new Object[] {id,password});
		return r;
	}
	
	public List<Enquiry> getAllEnquires(){
		class DataMapper implements RowMapper{
			public Enquiry mapRow(ResultSet rs,int rowNum)throws SQLException{
				Enquiry e=new Enquiry();
				e.setName(rs.getString("name"));
				e.setEmail(rs.getString("email"));
				e.setPhone(rs.getString("phone"));
				return e;
			}
		}
		final String query ="select * from enquries";
		List<Enquiry> e=jdbcTemplate.query(query,new DataMapper());
		return e;
	}
	
	public String addEnquiry(Enquiry e){
		try {
			String query="insert into enquries (email,name,phone) values(?,?,?)";
			jdbcTemplate.update(query,new Object[] { e.getEmail(),e.getName(),e.getPhone()});
			
			return "success";
		}catch(Exception ex) {
			return "failed";
		}
	}
}
