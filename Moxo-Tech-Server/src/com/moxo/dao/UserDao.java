package com.moxo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.moxo.model.UserInfo;
import com.moxo.util.DbUtils;

public class UserDao {
	public boolean login(String name, String pwd) {
		String sql = "select * from user where name='" + name + "' and password='" + pwd + "'";
		Connection conn = new DbUtils().getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				flag = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	// 获取指定ID的实体Bean
	public UserInfo GetBean(String name, String pwd) {
		String sql = "select * from user where name='" + name + "' and password='" + pwd + "'";
		Statement stat = null;
		ResultSet rs = null;
		Connection conn = new DbUtils().getConn();
		UserInfo userInfo = new UserInfo();
		try {
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				userInfo.setAccount(rs.getString("account"));
				userInfo.setName(rs.getString("name"));
				userInfo.setPassword(rs.getString("password"));
				userInfo.setGender(rs.getInt("gender"));
				userInfo.setPhoto(rs.getString("photo"));
				userInfo.setTelephone(rs.getString("telephone"));
				userInfo.setLabel(rs.getString("label"));
				userInfo.setNative_place(rs.getString("native_place"));
				userInfo.setBirthday(rs.getString("birthday"));
				userInfo.setEmail(rs.getString("email"));
				userInfo.setType(rs.getInt("type"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (stat != null)
					stat.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userInfo;
	}

	public List<UserInfo> findAllList(String username) {
		String sql = "select * from user";
		Statement stat = null;
		ResultSet rs = null;
		Connection conn = new DbUtils().getConn();
		List<UserInfo> list = new ArrayList<UserInfo>();
		try {
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				UserInfo userInfo = new UserInfo();
				userInfo.setAccount(rs.getString("account"));
				userInfo.setName(rs.getString("name"));
				userInfo.setPassword(rs.getString("password"));
				userInfo.setGender(rs.getInt("gender"));
				userInfo.setPhoto(rs.getString("photo"));
				userInfo.setTelephone(rs.getString("telephone"));
				userInfo.setLabel(rs.getString("label"));
				userInfo.setNative_place(rs.getString("native_place"));
				userInfo.setBirthday(rs.getString("birthday"));
				userInfo.setEmail(rs.getString("email"));
				userInfo.setType(rs.getInt("type"));
				list.add(userInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
