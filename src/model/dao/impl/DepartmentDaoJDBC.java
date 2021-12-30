package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{
	
	private Connection conn;
	private PreparedStatement st;
	private ResultSet rs;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		try {
			st = conn.prepareStatement("INSERT INTO Department (Name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
		}catch(SQLException e) {
			throw new DbException("Error INSERT"+ e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Department obj) {
		try {
			st = conn.prepareStatement("UPDATE department SET Name = ? WHERE Id = ?");
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			st.executeUpdate();
		}catch(SQLException e) {
			throw new DbException("Error updade " + e.getMessage());
		}
		finally {
			DB.closeStatement(st);;
		}
	}

	@Override
	public void deleteById(Integer id) {
		try {			
			
			st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
			st.setInt(1, id);
			st.executeUpdate();		
						
		}catch(SQLException e) {
			throw new DbException("Error Delete: " + e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Department findById(Integer id) {
		try {
			st = conn.prepareStatement("SELECT * from department WHERE id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Department dep = new Department(rs.getInt("Id"), rs.getString("Name"));
				return dep;
			}
			return null;
		}catch(SQLException e) {
			throw new DbException("Error select by id" + e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}
	
	@Override
	public List<Department> findAll() {
		try {
			st = conn.prepareStatement("Select * from department");
			rs = st.executeQuery();
			List<Department> list = new ArrayList<>();
			
			while(rs.next()) {
				Department dep = new Department(rs.getInt("Id"), rs.getString("Name"));				
				list.add(dep);
			}
			
			return list;
		}catch(SQLException e) {
			throw new DbException("Error findAll " + e.getMessage());
		}
	}

}
