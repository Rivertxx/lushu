package com.lushu.Service;

import com.lushu.Dao.UserDao;
import com.lushu.Dao.UserDaoImpl;
import com.lushu.entity.Right;
import com.lushu.entity.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

@RestController
@CrossOrigin
public class UserController {
    static final String SUCCESS = "Success";
    static final String FAIL = "Fail";

    // 注册操作，在用户表中新增一个用户表项
    @RequestMapping("/register")
    public String register(@RequestBody Map<String, Object> map) throws Exception {
        User newUser = new User();
        // 当前用手机号作为id，因为这样可以保证id唯一
        // 考虑使用当前时间作为ID，这也是唯一的，而且在用户修改手机或邮箱时，不必受到牵连
        // 这是个13位的数字
        Date date = new Date();
        String userID = "U" + String.valueOf(date.getTime());
        newUser.setUserID(userID);
        newUser.setUserName(map.get("user_name").toString());
        newUser.setSex(map.get("sex").toString());
        newUser.setAge(Integer.parseInt(map.get("age").toString()));
        newUser.setEmail(map.get("email").toString());
        newUser.setPhone(map.get("phone").toString());
        System.out.println("Success!");

        Right right = new Right();
        right.setUserID(userID);
        right.setPassword(map.get("password").toString());
        // 默认设定用户的role为"normal user"
        right.setRole("normal_user");

        UserDao userDao = new UserDaoImpl();
        try {
            userDao.addUser(newUser);
            userDao.addRight(right);
        } catch (SQLException se) {
            // 该账户已被注册
            String[] message = se.getMessage().split("'");
            return "{\"state\" : \"fail\", \"cause\" : \"" + message[3].split("[.]")[1] + "\"}";
            //return FAIL;
        }

        return "{\"state\" : \"success\", \"cause\" : \"\"}";
    }

    @RequestMapping("/login")
    public String login(@RequestBody Map<String, Object> map) throws Exception {
        // 注意，当前的登录功能仅查询密码是否正确，前提是保证了账户存在，后续应优化为先检测账户是否存在
        String loginType = map.get("login_type").toString();
        String account = map.get("account").toString();
        String password = map.get("password").toString();
        UserDao userDao = new UserDaoImpl();
        String truePassword = userDao.queryPasswordByPhoneOrEmail(loginType, account);
        if (truePassword == null)
        {
            // 账户不存在，应细化为向前端发送详细信息
            // return FAIL;
            return "{\"State\":\"fail\", \"cause\":\"account_not_exist\"}";
        }
        if (password.equals(truePassword))
        {
            String userID = userDao.queryUserIDByPhoneOrEmail(loginType, account);
            // return SUCCESS;
            return "{\"State\":\"success\", \"user_id\":\"" + userID + "\"}";
        }

        // 密码错误
        // return FAIL;
        return "{\"State\":\"fail\", \"cause\":\"wrong_password\"}";
    }

    @RequestMapping("/queryPersonalInformation")
    public String queryPersonalInformation(@RequestBody Map<String, Object> map) throws Exception {
        // 约定好前端通过什么来告知后端当前的账户
        String userID = map.get("user_id").toString();
        UserDao userDao = new UserDaoImpl();
        User user = userDao.queryUserByID(userID);
        // 考虑通过一个函数来实现对象信息转json格式信息
        // 可以考虑在前面增加一个state告知是否查询成功
        return userObjectToJsonString(user);
    }

    private String userObjectToJsonString(User user) {
        return "{\"user_name\" : \"" + user.getUserName() + "\", " +
                "\"sex\" : \"" + user.getSex() + "\", " +
                "\"age\" : \"" + user.getAge() + "\", " +
                "\"phone\" : \"" + user.getPhone() + "\", " +
                "\"email\" : \"" + user.getEmail() + "\"}";
    }

    @RequestMapping("/modifyPersonalInformation")
    public String modifyPersonalInformation(@RequestBody Map<String, Object> map) throws Exception {
        // 数据库应保证当修改个人信息的邮箱或手机号时，若出现与已有其他表项相等的情况即出错
        User user = new User();
        user.setUserID(map.get("user_id").toString());
        user.setUserName(map.get("user_name").toString());
        user.setSex(map.get("sex").toString());
        user.setAge(Integer.parseInt(map.get("age").toString()));
        user.setEmail(map.get("email").toString());
        user.setPhone(map.get("phone").toString());

        UserDao userDao = new UserDaoImpl();
        try {
            userDao.modifyUser(user);
        } catch (SQLException se) {
            // 出现信息重复？即手机号或邮箱
            String[] message = se.getMessage().split("'");
            return "{\"state\" : \"fail\", \"cause\" : \"" + message[3].split("[.]")[1] + "\"}";
        }

        return "{\"state\" : \"success\", \"cause\" : \"\"}";
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(@RequestBody Map<String, Object> map) throws Exception {
        String userID = map.get("user_id").toString();
        UserDao userDao = new UserDaoImpl();
        userDao.deleteUserByID(userID);
        return "{\"state\" : \"success\"}";
    }

    @RequestMapping("/modifyPassword")
    public String modifyPassword(@RequestBody Map<String, Object> map) throws Exception {
        Right right = new Right();
        right.setUserID(map.get("user_id").toString());
        right.setPassword(map.get("password").toString());
        UserDao userDao = new UserDaoImpl();
        userDao.modifyRightByID(right);
        return "{\"state\" : \"success\"}";
    }
}
