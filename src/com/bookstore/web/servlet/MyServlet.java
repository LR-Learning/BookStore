package com.bookstore.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.domain.User;

public class MyServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 从Session 中取出User对象
		User u = (User) request.getSession().getAttribute("user");
		//判断User是否为null
		if(u==null){
			response.sendRedirect(request.getContextPath()+"/login.jsp");
		}else{
			// 普通用户
			String path = "/myAccount.jsp";
			if("admin".equals(u.getRole())){
				// 管理员页面
				path = "/admin/login/home.jsp";	
			}
			request.getRequestDispatcher(path).forward(request, response);
		}
	
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
