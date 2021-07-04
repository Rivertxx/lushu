package com.lushu.Dao;

import com.lushu.entity.Right;
import com.lushu.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    static final String SUCCESS = "Success";
    static final String FAIL = "Fail";

    @Override
    public void addUser(User user) throws Exception{
        // 首先获取数据库连接
        Connection conn = DBUtil.getConnection();
        String sql = "insert into users " +
                "(user_id, user_name, sex, age, email, phone)" +
                "values (" +
                "?, ?, ?, ?, ?, ?)";
        PreparedStatement psmt = conn.prepareStatement(sql);

        psmt.setString(1, user.getUserID());
        psmt.setString(2, user.getUserName());
        psmt.setString(3, user.getSex());
        psmt.setString(4, String.valueOf(user.getAge()));
        psmt.setString(5, user.getEmail());
        psmt.setString(6,user.getPhone());

        psmt.execute();
    }

    @Override
    public void deleteUserByID(String userID) throws Exception {
        Connection conn = DBUtil.getConnection();
        String sql = "delete from users where user_id = ?";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, userID);
        psmt.execute();
    }

    @Override
    public void modifyUser(User user) throws Exception{
        Connection conn = DBUtil.getConnection();
        String sql = "update users " +
                "set user_name = ?, sex = ?," +
                "age = ?, email = ?, phone = ?" +
                "where user_id = ?";

        PreparedStatement psmt = conn.prepareStatement(sql);

        psmt.setString(1, user.getUserName());
        psmt.setString(2, user.getSex());
        psmt.setString(3, String.valueOf(user.getAge()));
        psmt.setString(4, user.getEmail());
        psmt.setString(5, user.getPhone());
        psmt.setString(6, user.getUserID());

        psmt.execute();
    }

    @Override
    public User queryUserByID(String userID) throws Exception{
        Connection conn = DBUtil.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select " +
                "user_name, sex, age, email, phone " +
                "from users " +
                "where user_id = '" + userID + "'");

        User user = new User();
        user.setUserID(userID);
        while (rs.next()) {
            user.setUserName(rs.getString("user_name"));
            user.setSex(rs.getString("sex"));
            user.setAge(rs.getInt("age"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
        }

        return user;
    }

    @Override
    public List<User> queryAllUsers() throws Exception {
        Connection conn = DBUtil.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from users");
        List<User> userList = new ArrayList<User>();
        User user = null;

        while (rs.next()) {
            user = new User();
            user.setUserID(rs.getString("user_id"));
            user.setUserName(rs.getString("user_name"));
            user.setSex(rs.getString("sex"));
            user.setAge(rs.getInt("age"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            userList.add(user);
        }
        return userList;
    }

    @Override
    public String queryPasswordByPhoneOrEmail(String type, String account) throws Exception{
        Connection conn = DBUtil.getConnection();
        Statement stmt = conn.createStatement();
        String querySQL = "select password from rights inner join users " +
                "on rights.user_id = users.user_id ";
        ResultSet rs;
        if (type.equals("phone"))
        {
            // 使用电话号码查询（登录）
            rs = stmt.executeQuery(querySQL +
                    "where users.phone = '" + account + "'");
        }
        else
        {
            // 使用邮箱查询
            rs = stmt.executeQuery(querySQL +
                    "where users.email = '" + account + "'");
        }

        String password = null;
        while(rs.next()) {
            password = rs.getString("password");
        }
        // 如果用户的password就设置为"Fail"，那么无法通过返回FAIL来区分
        return password;
    }

    @Override
    public String queryUserIDByPhoneOrEmail(String type, String account) throws Exception {
        Connection conn = DBUtil.getConnection();
        Statement stmt = conn.createStatement();
        String querySQL = "select user_id from users ";
        ResultSet rs;
        if (type.equals("phone")) {
            rs = stmt.executeQuery(querySQL +
                    "where phone = '" + account + "'");
        }
        else {
            rs = stmt.executeQuery(querySQL + "where email = '" + account + "'");
        }

        String userID = null;
        while (rs.next()) {
            userID = rs.getString("user_id");
        }
        return userID;
    }

    @Override
    public void addRight(Right right) throws Exception{
        Connection conn = DBUtil.getConnection();
        String sql = "insert into rights " +
                "(user_id, password, role)" +
                "values (" +
                "?, ?, ?)";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, right.getUserID());
        psmt.setString(2, right.getPassword());
        psmt.setString(3, right.getRole());

        psmt.execute();
    }

    @Override
    public void modifyRightByID(Right right) throws Exception {
        Connection conn = DBUtil.getConnection();
        String sql = "update rights set " +
                "password = ? where user_id = ?";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, right.getPassword());
        psmt.setString(2, right.getUserID());
        psmt.execute();
    }

    @Override
    public Right queryRightByID(String userID) throws Exception{
        Connection conn = DBUtil.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select password, role " +
                "from rights " +
                "where user_id = '" + userID + "'");
        Right right = new Right();
        while (rs.next()) {
            right.setPassword(rs.getString("password"));
            right.setRole(rs.getString("role"));
        }
        right.setUserID(userID);
        return right;
    }
}
