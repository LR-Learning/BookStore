package com.bookstore.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.*;
import com.bookstore.domain.User;

public class RoleFilter implements Filter{

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		//ǿת
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		//����ҵ��
			//��session�а��û�����õ�
			User user = (User) request.getSession().getAttribute("user");
			//�жϵ�ǰ�û�Ȩ��
			if(user!=null){
				if(!"admin".equals(user.getRole())){
					response.getWriter().write("Ȩ�޲��㣬���޷����ʣ�");
					response.setHeader("refresh", "2;url="+request.getContextPath()+"/index.jsp");
					return;
				}
				//����
				chain.doFilter(request, response);
			}
			
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			
			
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
