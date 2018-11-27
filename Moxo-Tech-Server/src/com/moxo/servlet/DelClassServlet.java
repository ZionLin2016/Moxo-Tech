package com.moxo.servlet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.moxo.dao.ClassListDao;
import com.moxo.model.ClassJson;
import com.moxo.model.Message;

/**
 * Servlet implementation class DelClass
 */
@WebServlet("/DelClassServlet")
public class DelClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ClassListDao classListDao = new ClassListDao();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.setContentType("application/json; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		System.out.println("请求删除------------------");
		String code = request.getParameter("invite_code");
		boolean flag = classListDao.delClassByCode(code);
		Message<List<ClassJson>> msg = new Message<List<ClassJson>>();
		if (flag) {
			msg.setResults(null);
			msg.setCode(200);
			msg.setMsg("删除成功");
		} else {
			msg.setResults(null);
			msg.setCode(000);
			msg.setMsg("删除失败");
		}

		Gson gson = new Gson();
		String test = gson.toJson(msg);
		System.out.println("-----" + test);
		try {
			writeToClient(response, test);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);

	}

	private void writeToClient(HttpServletResponse response, String data)
			throws IOException, UnsupportedEncodingException {
		ServletOutputStream out = response.getOutputStream();

		OutputStreamWriter outWriter = new OutputStreamWriter(out, "utf-8");
		BufferedWriter buffered = new BufferedWriter(outWriter);
		buffered.write(data);
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
