package com.bookstore.web.servlet;

import com.bookstore.domain.User;
import com.bookstore.exception.UserException;
import com.bookstore.service.UserService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

//@WebServlet(name = "register", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 处理验证码
        String ckcode = request.getParameter("ckcode");
        String checkcode_session = (String) request.getSession().getAttribute("checkcode_session");
        // 判断验证码是否一致
        if (!checkcode_session.equals(ckcode)) {
            request.setAttribute("ckcode_msg", "验证码错误");
            // 如果不一致，则回到注册页面
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        // 获取表单数据
        User user = new User();
        try {
            // 将请求的map封装到user
            BeanUtils.populate(user, request.getParameterMap());
            user.setActiveCode(UUID.randomUUID().toString()); // 手动设置激活码
            // 调用业务逻辑
            UserService us = new UserService();
            us.regist(user);
            // 分发转向
//            request.getSession().setAttribute("user", user); // 将用户信息封装到Session中
            request.getRequestDispatcher("/registersuccess.jsp").forward(request, response);
        }catch (UserException e) {
        	request.setAttribute("user_msg", e.getMessage());
        	request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
