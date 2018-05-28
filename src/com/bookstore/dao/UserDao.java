package com.bookstore.dao;

import com.bookstore.domain.User;
import com.bookstore.util.C3P0Util;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDao {
	QueryRunner qr = new QueryRunner(C3P0Util.getDataSource()); 
    public void addUser(User user) throws SQLException {
    	
       String sql = "INSERT INTO USER(username,password,gender,email,telephone,introduce,activecode,state,registtime) "
                + "VALUES(?,?,?,?,?,?,?,?,?)";
        qr.update(sql, user.getUsername(), user.getPassword(),
                user.getGender(), user.getEmail(), user.getTelephone(),
                user.getIntroduce(), user.getActiveCode(), user.getState(),
               user.getRegistTime());

    }
    
    // 根据激活码查找用户
	public User findUserByActiveCode(String activeCode) throws SQLException {
//		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource()); 
		return qr.query("select * from user where activeCode=?", new BeanHandler<User>(User.class),activeCode);
	}
	
	// 修改用户激活状态
	public void activeCode(String activeCode) throws SQLException {
//		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource()); 
		qr.update("update user set state=1 where activeCode=?", activeCode);
		
	}
	
	// 用户登录
	public User findUserByUserNameAndPassword(String username, String password) throws SQLException {
		return qr.query("select * from user where username=? and password=?", new BeanHandler<User>(User.class), username, password);
	}

	
}
