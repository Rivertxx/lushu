package com.lushu.Dao;

import com.lushu.entity.Right;
import com.lushu.entity.User;

import java.util.List;

public interface UserDao {
    // 注册时向表中添加新用户
    void addUser(User user) throws Exception;

    // 删除用户
    void deleteUserByID(String userID) throws Exception;

    // 通过用户id修改其他信息
    void modifyUser(User user) throws Exception;

    // 如果用户仅能查询自己的信息的话
    User queryUserByID(String userID) throws Exception;

    // 超级管理员查询所有用户信息
    List<User> queryAllUsers() throws Exception;

    String queryPasswordByPhoneOrEmail(String type, String account) throws Exception;

    String queryUserIDByPhoneOrEmail(String type, String account) throws Exception;

    void addRight(Right right) throws Exception;

    void modifyRightByID(Right right) throws Exception;

    Right queryRightByID(String userID) throws Exception;
}
