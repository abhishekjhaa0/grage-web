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

import com.incapp.beans.Garage;
import com.incapp.beans.User;

@Repository
public class UserRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public String addUser(User u,MultipartFile photo){
		try {
			String query="insert into users (email,name,phone,password,photo) values(?,?,?,?,?)";
			jdbcTemplate.update(query,new Object[] { u.getEmail(),u.getName(),u.getPhone(),u.getPassword(),photo.getInputStream()});
			
			return "success";
		}catch(Exception ex) {
			return "failed";
		}
	}
	public List<User> userLogin(String email,String password){
		class DataMapper implements RowMapper{
			public User mapRow(ResultSet rs,int rowNum)throws SQLException{
				User u=new User();
				u.setName(rs.getString("name"));
				u.setEmail(rs.getString("email"));
				u.setPhone(rs.getString("phone"));
				return u;
			}
		}
		final String query ="select * from users where email = ? and password=?";
		List<User> u=jdbcTemplate.query(query,new DataMapper(),new Object[] {email,password});
		return u;
	}
	
	public String userPhotoUpload(String email,MultipartFile photo){
		try {
			String query="update users set photo=? where email=?";
			jdbcTemplate.update(query,new Object[] { photo.getInputStream(),email});
			
			return "success";
		}catch(Exception ex) {
			return "failed";
		}
	}
	
	public String userChangePassword(String email,String o_password,String n_password,String c_password){
		try {
			if(n_password.equals(c_password)) {
				String query="update users set password=? where email=? and password=?";
				int result=jdbcTemplate.update(query,new Object[] { n_password,email,o_password});
				if(result!=0) {
					return "Password Updated Successfully!";
				}else {
					return "Old Password is Wrong!";
				}
			}else {
				return "Password Mismatch!";
			}
		}catch(Exception ex) {
			return "failed";
		}
	}
	
	public byte[] getPhoto(String email){
		class DataMapper implements RowMapper{
			public byte[] mapRow(ResultSet rs,int rowNum)throws SQLException{
				return 	rs.getBytes("photo");
			}
		}
		try {
			final String query ="select photo from users where email=?";
			byte[] r=(byte[]) jdbcTemplate.queryForObject(query,new DataMapper(),new Object[] {email});
			return r;
		}catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
}
