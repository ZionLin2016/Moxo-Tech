package com.moxo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.moxo.model.ClassJson;
import com.moxo.util.DbUtils;

public class ClassListDao {

	public List<ClassJson> findClassById(int id) {
		String sql = "select * from classlist where currentUserId=" + id;
		Statement stat = null;
		ResultSet rs = null;
		Connection conn = new DbUtils().getConn();
		List<ClassJson> list = new ArrayList<ClassJson>();
		try {
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				ClassJson cnbean = new ClassJson();
				// cnbean.setCurrentUserId(rs.getInt("currentUserId"));
				cnbean.setCover_address(rs.getString("cover_address"));
				cnbean.setClass_name(rs.getString("class_name"));
				cnbean.setCourse_name(rs.getString("course_name"));
				cnbean.setClass_type(rs.getString("class_type"));
				cnbean.setClass_book(rs.getString("class_book"));
				cnbean.setStudy_aims(rs.getString("study_aims"));
				cnbean.setSyllabus(rs.getString("syllabus"));
				cnbean.setExam_schedule(rs.getString("exam_schedule"));
				cnbean.setInvite_code(rs.getString("invite_code"));
				list.add(cnbean);
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

	public List<ClassJson> findClassByName(String username) {
		String sql = "select * from classlist where username='" + username + "'";
		Statement stat = null;
		ResultSet rs = null;
		Connection conn = new DbUtils().getConn();
		List<ClassJson> list = new ArrayList<ClassJson>();
		try {
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				ClassJson cnbean = new ClassJson();
				// cnbean.setCurrentUserId(rs.getInt("currentUserId"));
				cnbean.setUsername(rs.getString("username"));
				cnbean.setCover_address(rs.getString("cover_address"));
				cnbean.setClass_name(rs.getString("class_name"));
				cnbean.setCourse_name(rs.getString("course_name"));
				cnbean.setClass_type(rs.getString("class_type"));
				cnbean.setClass_book(rs.getString("class_book"));
				cnbean.setStudy_aims(rs.getString("study_aims"));
				cnbean.setSyllabus(rs.getString("syllabus"));
				cnbean.setExam_schedule(rs.getString("exam_schedule"));
				cnbean.setInvite_code(rs.getString("invite_code"));
				list.add(cnbean);
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

	public boolean saveClass(ClassJson classJson) {
		String sql = "insert into classlist(cover_address,class_name,course_name,class_type,class_book,study_aims,syllabus,exam_schedule, invite_code, username) "
				+ "values('" + classJson.getCover_address() + "','" + classJson.getClass_name() + "','"
				+ classJson.getCourse_name() + "','" + classJson.getClass_type() + "','" + classJson.getClass_book()
				+ "','" + classJson.getStudy_aims() + "','" + classJson.getSyllabus() + "','"
				+ classJson.getExam_schedule() + "','" + classJson.getInvite_code() + "','" + classJson.getUsername()
				+ "');";
		Connection conn = new DbUtils().getConn();
		PreparedStatement ps = null;
		boolean rs = true;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rs;
	}

	public boolean delClassByCode(String code) {
		String sql = "delete from classlist where invite_code='" + code + "';";
		Connection conn = new DbUtils().getConn();
		PreparedStatement ps = null;
		boolean rs = false;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rs;
	}

}
