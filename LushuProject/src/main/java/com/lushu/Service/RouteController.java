package com.lushu.Service;

import com.lushu.Dao.*;
import com.lushu.entity.KeyPoint;
import com.lushu.entity.Right;
import com.lushu.entity.Route;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class RouteController {
    static final String SUCCESS = "Success";
    static final String FAIL = "Fail";

    // 上传一条新路线
    @RequestMapping("/uploadRoute")
    public String uploadRoute(@RequestBody Map<String, Object> map) throws Exception {
        Route newRoute = new Route();
        Date date = new Date();
        String routeID = "R" + String.valueOf(date.getTime());
        newRoute.setRouteID(routeID);
        newRoute.setRouteName(map.get("route_name").toString());
        newRoute.setStart(map.get("start").toString());
        newRoute.setDestination(map.get("destination").toString());
        newRoute.setMileage(Double.parseDouble(map.get("mileage").toString()));
        newRoute.setCumulativeClimb(Double.parseDouble(map.get("cumulative_climb").toString()));
        newRoute.setRoadCondition(map.get("road_condition").toString());
        newRoute.setAuthor(map.get("author").toString());

        RouteDao routeDao = new RouteDaoImpl();
        routeDao.addRoute(newRoute);
        return "{\"state\" : \"success\"}";
    }
    
    @RequestMapping("/modifyRoute")
    public String modifyRoute(@RequestBody Map<String, Object> map) throws Exception {
        // 重要修改！！！
        // 前端告知要修改路线的用户ID，跟删除时的操作一样
        String userID = map.get("user_id").toString();
        String routeID = map.get("route_id").toString();
        UserDao userDao = new UserDaoImpl();
        String role = userDao.queryRightByID(userID).getRole();
        RouteDao routeDao = new RouteDaoImpl();
        String author = routeDao.queryRouteByID(routeID).getAuthor();
        if (role.equals("normal_user") && !author.equals(userID))
            return "{\"state\" : \"fail\"}";
        Route route = new Route();
        route.setRouteID(routeID);
        route.setRouteName(map.get("route_name").toString());
        route.setStart(map.get("start").toString());
        route.setDestination(map.get("destination").toString());
        route.setMileage(Double.parseDouble(map.get("mileage").toString()));
        route.setCumulativeClimb(Double.parseDouble(map.get("cumulative_climb").toString()));
        route.setRoadCondition(map.get("road_condition").toString());
        route.setAuthor(map.get("author").toString());

        try {
            routeDao.modifyRoute(route);
        } catch (Exception e) {
            return "{\"state\" : \"fail\"}";
        }
        return "{\"state\" : \"success\"}";
    }

    @RequestMapping("/queryRoute")
    public String queryRoute(@RequestBody Map<String, Object> map) throws Exception {
        RouteDao routeDao = new RouteDaoImpl();
        String routeID = map.get("route_id").toString();
        Route route = routeDao.queryRouteByID(routeID);
        return "{\"state\" : \"success\", \"route\" : " + routeObjectToJsonString(route) + "}";
    }

    @RequestMapping("/fuzzyQueryRoutes")
    public String fuzzyQueryRoutes(@RequestBody Map<String, Object> map) throws Exception {
        // 由于是模糊搜索，这个名字既要搜索路线名，又要搜索作者名
        String name = map.get("name").toString();
        RouteDao routeDao = new RouteDaoImpl();
        List<Route> routeList = routeDao.fuzzyQueryRouteByName(name);
        if (routeList.isEmpty())
            return "{\"state\" : \"empty\", \"routes\" : \"\"}";
        return "{\"state\" : \"success\", \"routes\" : " + routesToJsonString(routeList) + "}";
    }
    
    @RequestMapping("/initialRoutes")
    public String initialRoutes(@RequestBody Map<String, Object> map) throws Exception {
        RouteDao routeDao = new RouteDaoImpl();
        List<Route> routeList = new ArrayList<>();
        routeList = routeDao.randomlyQueryRoutes();
        return "{\"state\" : \"success\", \"routes\" : " + routesToJsonString(routeList) + "}";
    }

    private String routeObjectToJsonString(Route route) {
        return "{\"route_id\" : \"" + route.getRouteID() + "\", " +
                "\"route_name\" : \"" + route.getRouteName() + "\", " +
                "\"start\" : \"" + route.getStart() + "\", " +
                "\"destination\" : \"" + route.getDestination() + "\", " +
                "\"mileage\" : \"" + route.getMileage() + "\", " +
                "\"cumulative_climb\" : \"" + route.getCumulativeClimb() + "\", " +
                "\"road_condition\" : \"" + route.getRoadCondition() + "\", " +
                "\"author\" : \"" + route.getAuthor() + "\"}";
    }
    
    private String routesToJsonString(List<Route> routeList) {
        StringBuilder routesJsonString = new StringBuilder();
        for (Route route : routeList) {
            routesJsonString.append(", ").append(routeObjectToJsonString(route));
        }
        if (routesJsonString.toString().length() > 2)
            return "[" + routesJsonString.toString().substring(2) + "]";
        else
            return "";
    }

    @RequestMapping("/deleteRoute")
    public String deleteRoute(@RequestBody Map<String, Object> map) throws Exception {
        String routeID = map.get("route_id").toString();
        String userID = map.get("user_id").toString();
        UserDao userDao = new UserDaoImpl();
        Right right = userDao.queryRightByID(userID);
        RouteDao routeDao = new RouteDaoImpl();
        // 普通用户无法删除路线（是不是考虑检测上传者，如果是本人就能删除？）
        if (right.getRole().equals("normal_user"))
        {
            Route route = routeDao.queryRouteByID(routeID);
            if (!route.getAuthor().equals(userID))
                return "{\"state\" : \"fail\"}";
        }
        // 执行删除操作
        routeDao.deleteRouteByID(routeID);
        return "{\"state\" : \"success\"}";
    }

    @RequestMapping("/uploadKeypoint")
    public String uploadKeyPoint(@RequestBody Map<String, Object> map) throws Exception {
        KeyPoint keyPoint = new KeyPoint();
        keyPoint.setRouteID(map.get("route_id").toString());
        keyPoint.setKeyPoint1(map.get("keypoint_1").toString());
        keyPoint.setKeyPoint2(map.get("keypoint_2").toString());
        keyPoint.setKeyPoint3(map.get("keypoint_3").toString());
        keyPoint.setKeyPoint4(map.get("keypoint_4").toString());
        RouteDao routeDao = new RouteDaoImpl();
        routeDao.addKeyPoint(keyPoint);
        return "{\"state\" : \"success\"}";
    }

    @RequestMapping("/deleteKeypoint")
    public String deleteKeyPoint(@RequestBody Map<String, Object> map) throws Exception {
        String routeID = map.get("route_id").toString();
        String userID = map.get("user_id").toString();
        UserDao userDao = new UserDaoImpl();
        Right right = userDao.queryRightByID(userID);
        RouteDao routeDao = new RouteDaoImpl();
        String author = routeDao.queryRouteByID(routeID).getAuthor();
        if (right.getRole().equals("normal_user") && !(userID.equals(author)))
            // 普通用户且不是作者本人不允许删除
            return "{\"state\" : \"fail\"}";

        routeDao.deleteKeyPointByID(routeID);
        return "{\"state\" : \"success\"}";
    }

    @RequestMapping("/modifyKeypoint")
    public String modifyKeypoint(@RequestBody Map<String, Object> map) throws Exception {
        String userID = map.get("user_id").toString();
        String routeID = map.get("route_id").toString();
        UserDao userDao = new UserDaoImpl();
        String role = userDao.queryRightByID(userID).getRole();
        RouteDao routeDao = new RouteDaoImpl();
        String author = routeDao.queryRouteByID(routeID).getAuthor();
        if (role.equals("normal_user") && !(author.equals(userID)))
            return "{\"state\" : \"fail\"}";
        KeyPoint keyPoint = new KeyPoint();
        keyPoint.setRouteID(routeID);
        keyPoint.setKeyPoint1(map.get("keypoint_1").toString());
        keyPoint.setKeyPoint2(map.get("keypoint_2").toString());
        keyPoint.setKeyPoint3(map.get("keypoint_3").toString());
        keyPoint.setKeyPoint4(map.get("keypoint_4").toString());
        routeDao.modifyKeyPointByID(keyPoint);
        return "{\"state\" : \"success\"}";
    }
    
    @RequestMapping("/queryKeypoint")
    public String queryKeyPoint(@RequestBody Map<String, Object> map) throws Exception {
        String routeID = map.get("route_id").toString();
        RouteDao routeDao = new RouteDaoImpl();
        KeyPoint keyPoint = routeDao.queryKeyPointByID(routeID);
        return keyPointObjectToJsonString(keyPoint);
    }
    
    private String keyPointObjectToJsonString(KeyPoint keyPoint) {
        return "{\"route_id\" : \"" + keyPoint.getRouteID() + "\"," +
                "\"keypoint_1\" : \"" + keyPoint.getKeyPoint1() + "\"," +
                "\"keypoint_2\" : \"" + keyPoint.getKeyPoint2() + "\"," +
                "\"keypoint_3\" : \"" + keyPoint.getKeyPoint3() + "\"," +
                "\"keypoint_4\" : \"" + keyPoint.getKeyPoint4() + "\"}";
    }
}
