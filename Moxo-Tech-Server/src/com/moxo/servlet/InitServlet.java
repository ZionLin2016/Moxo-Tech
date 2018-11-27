package com.moxo.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.chat.protocol.StartClientTcpDeamon;

@WebServlet("/InitServlet")
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
		System.out.println("自动加载启动.");

		Thread thread = new Thread(new StartClientTcpDeamon());

		thread.start();
	}

}
