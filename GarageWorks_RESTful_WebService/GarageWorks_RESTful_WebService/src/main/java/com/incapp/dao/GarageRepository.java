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

@Repository
public class GarageRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public String addGarage(Garage g,MultipartFile photo1,MultipartFile photo2){
		try {
			String query="insert into garage (email,name,gname,phone,password,state,city,sec_vill,shop_no,photo1,photo2,status) values(?,?,?,?,?,?,?,?,?,?,?,'pending')";
			jdbcTemplate.update(query,new Object[] { g.getEmail(),g.getName(),g.getGname(),g.getPhone(),g.getPassword(),g.getState(),g.getCity(),g.getSecVill(),g.getShopNo(), photo1.getInputStream(),photo2.getInputStream()});
			
			return "success";
		}catch(Exception ex) {
			ex.printStackTrace();
			return "failed";
		}
	}
	public String ChangeStatusGarageOwner(String email,String status){
		try {
			String query="update garage set status=? where email=?";
			jdbcTemplate.update(query,new Object[] { status,email});
			
			return "success";
		}catch(Exception ex) {
			ex.printStackTrace();
			return "failed";
		}
	}
	public List<Garage> garageLogin(String email,String password){
		class DataMapper implements RowMapper{
			public Garage mapRow(ResultSet rs,int rowNum)throws SQLException{
				Garage g=new Garage();
				g.setName(rs.getString("name"));
				g.setGname(rs.getString("gname"));
				g.setEmail(rs.getString("email"));
				g.setPhone(rs.getString("phone"));
				g.setState(rs.getString("state"));
				g.setCity( rs.getString("city"));
				g.setSecVill(rs.getString("sec_vill"));
				g.setShopNo(rs.getString("shop_no"));
				g.setStatus(rs.getString("status"));
				return g;
			}
		}
		final String query ="select * from garage where email =? and password=?";
		List<Garage> g=jdbcTemplate.query(query,new DataMapper(),new Object[] {email,password});
		return g;
	}
	
	public List<Garage> getAllGarageByStatus(String status){
		class DataMapper implements RowMapper{
			public Garage mapRow(ResultSet rs,int rowNum)throws SQLException{
				Garage g=new Garage();
				g.setName(rs.getString("name"));
				g.setGname(rs.getString("gname"));
				g.setEmail(rs.getString("email"));
				g.setPhone(rs.getString("phone"));
				g.setState(rs.getString("state"));
				g.setCity( rs.getString("city"));
				g.setSecVill(rs.getString("sec_vill"));
				g.setShopNo(rs.getString("shop_no"));
				g.setStatus(rs.getString("status"));
				return g;
			}
		}
		final String query ="select * from garage where status = ?";
		List<Garage> g=jdbcTemplate.query(query,new DataMapper(),new Object[] {status});
		return g;
	}
	
	public List<Garage> getAllGarageByStateCity(String state,String city){
		class DataMapper implements RowMapper{
			public Garage mapRow(ResultSet rs,int rowNum)throws SQLException{
				Garage g=new Garage();
				g.setName(rs.getString("name"));
				g.setGname(rs.getString("gname"));
				g.setEmail(rs.getString("email"));
				g.setPhone(rs.getString("phone"));
				g.setState(rs.getString("state"));
				g.setCity( rs.getString("city"));
				g.setSecVill(rs.getString("sec_vill"));
				g.setShopNo(rs.getString("shop_no"));
				g.setStatus(rs.getString("status"));
				return g;
			}
		}
		final String query ="select * from garage where state=? and city=? and status='accepted'";
		List<Garage> g=jdbcTemplate.query(query,new DataMapper(),new Object[] {state,city});
		return g;
	}
	
	public Garage getGarageByEmail(String email){
		class DataMapper implements RowMapper{
			public Garage mapRow(ResultSet rs,int rowNum)throws SQLException{
				Garage g=new Garage();
				g.setName(rs.getString("name"));
				g.setGname(rs.getString("gname"));
				g.setEmail(rs.getString("email"));
				g.setPhone(rs.getString("phone"));
				g.setState(rs.getString("state"));
				g.setCity( rs.getString("city"));
				g.setSecVill(rs.getString("sec_vill"));
				g.setShopNo(rs.getString("shop_no"));
				g.setStatus(rs.getString("status"));
				return g;
			}
		}
		try {
			final String query ="select * from garage where email=?";
			Garage g=(Garage)jdbcTemplate.queryForObject(query,new DataMapper(),new Object[] {email});
			return g;
		}catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
	public String addGarageService(String email,String service){
		try {
			String query="insert into services (garage_email,service) values(?,?)";
			jdbcTemplate.update(query,new Object[] { email,service});
			
			return "success";
		}catch(Exception ex) {
			return "failed";
		}
	}
	public List<String> getGarageAllService(String email){
		class DataMapper implements RowMapper{
			public String mapRow(ResultSet rs,int rowNum)throws SQLException{
				return rs.getString("service");
			}
		}
		final String query ="select * from services where garage_email=?";
		List<String> g=jdbcTemplate.query(query,new DataMapper(),new Object[] {email});
		return g;
	}
	
	public String updateGarageStatus(String email,String status){
		try {
			String query="update garage set status=? where email=?";
			jdbcTemplate.update(query,new Object[] { status,email});
			
			return "success";
		}catch(Exception ex) {
			return "failed";
		}
	}
	
	public byte[] getPhoto1(String email){
		class DataMapper implements RowMapper{
			public byte[] mapRow(ResultSet rs,int rowNum)throws SQLException{
				return 	rs.getBytes("photo1");
			}
		}
		try {
			final String query ="select photo1 from garage where email=?";
			byte[] r=(byte[]) jdbcTemplate.queryForObject(query,new DataMapper(),new Object[] {email});
			return r;
		}catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}
	public byte[] getPhoto2(String email){
		class DataMapper implements RowMapper{
			public byte[] mapRow(ResultSet rs,int rowNum)throws SQLException{
				return 	rs.getBytes("photo2");
			}
		}
		try {
			final String query ="select photo2 from garage where email=?";
			byte[] r=(byte[]) jdbcTemplate.queryForObject(query,new DataMapper(),new Object[] {email});
			return r;
		}catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
}
