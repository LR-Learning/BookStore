package com.bookstore.service;

import com.bookstore.dao.UserDao;
import com.bookstore.domain.User;
import com.bookstore.exception.UserException;
import com.bookstore.util.SendJMail;

import java.sql.SQLException;

public class UserService {
    UserDao ud = new UserDao();

    public void regist(User user) throws UserException {
        try {
            ud.addUser(user);// 用户注册
            
            String emailMsg = "注册成功， 请<a href='http://www.bookstore.com/activeServlet?activeCode="+user.getActiveCode()+"'>激活</a>后登陆";
            SendJMail.sendMail(user.getEmail(), emailMsg);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("注册失败");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public void activeUser(String activeCode) throws UserException {
		// 根据激活码查找用户
		User user;
		try {
			user = ud.findUserByActiveCode(activeCode);
		
		if(user!=null){
			// 激活用户
			ud.activeCode(activeCode);
			return;
		}
		throw new UserException("激活失败");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new UserException("激活失败");
		}
	}

	public User login(String username, String password) throws UserException {
		User user = null;
		try {
			user = ud.findUserByUserNameAndPassword(username, password);
			if(user==null){
				throw new UserException("用户名或密码错误");
			}
			if(user.getState()==0){
				throw new UserException("用户未激活");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("用户名或密码错误");
		}
		return user;
	}

	
}
