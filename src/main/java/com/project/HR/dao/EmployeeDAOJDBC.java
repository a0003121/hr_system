package com.project.HR.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.project.HR.vo.EmployeeJDBCVO;

public class EmployeeDAOJDBC {
	
	public List<EmployeeJDBCVO> getAll(){
		List<EmployeeJDBCVO> list = new ArrayList<EmployeeJDBCVO>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/HR?serverTimezone=Asia/Taipei", "root", "password");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from employee");
			
			while (rs.next()) {
				EmployeeJDBCVO employeeVO = new EmployeeJDBCVO();
				employeeVO.setId(rs.getInt(1));
				employeeVO.setName(rs.getString(2));
				employeeVO.setPosition(rs.getString(3));
				
				list.add(employeeVO);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
