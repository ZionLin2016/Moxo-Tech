package com.moxo.servlet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.moxo.dao.UserDao;
import com.moxo.model.Message;
import com.moxo.model.UserInfo;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	UserDao userDao = new UserDao();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json; charset=utf-8");

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (userDao.GetBean(username, password) != null) {
			UserInfo userInfo = userDao.GetBean(username, password);

			Message<UserInfo> msg = new Message<UserInfo>();
			msg.setResults(userInfo);
			msg.setCode(200);
			msg.setMsg("成功");
			Gson gson = new Gson();
			String test = gson.toJson(msg);

			writeToClient(response, test);
		} else {
			Message<UserInfo> msg = new Message<UserInfo>();
			msg.setResults(null);
			msg.setCode(0);
			msg.setMsg("失败");
			Gson gson = new Gson();
			String back = gson.toJson(msg);
			writeToClient(response, back);
		}

	}

	private void writeToClient(HttpServletResponse response, String data)
			throws IOException, UnsupportedEncodingException {
		ServletOutputStream out = response.getOutputStream();

		OutputStreamWriter outWriter = new OutputStreamWriter(out, "utf-8");
		BufferedWriter buffered = new BufferedWriter(outWriter);
		buffered.write(data);
		System.out.println("--------------" + data.toString());
		buffered.flush();
		out.close();
	}

	private StringBuffer readFromClient(HttpServletRequest request) throws IOException, UnsupportedEncodingException {
		ServletInputStream in = request.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(in, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String str = null;
		StringBuffer buffer = new StringBuffer();
		while ((str = bufferedReader.readLine()) != null) {
			buffer.append(str);
		}
		bufferedReader.close();
		inputStreamReader.close();
		in.close();
		return buffer;
	}
}
