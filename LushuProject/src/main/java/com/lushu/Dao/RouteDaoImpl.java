package com.lushu.Dao;

import com.lushu.entity.KeyPoint;
import com.lushu.entity.Route;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    @Override
    public void addRoute(Route route) throws Exception {
        Connection conn = DBUtil.getConnection();
        String sql = "insert into routes " +
                "(route_id, route_name, start, destination, mileage, cumulative_climb, road_condition, author)" +
                "values (" +
                "?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, route.getRouteID());
        psmt.setString(2, route.getRouteName());
        psmt.setString(3, route.getStart());
        psmt.setString(4, route.getDestination());
        psmt.setString(5, String.valueOf(route.getMileage()));
        psmt.setString(6, String.valueOf(route.getCumulativeClimb()));
        psmt.setString(7, route.getRoadCondition());
        psmt.setString(8, route.getAuthor());

        psmt.execute();
    }

    @Override
    public void modifyRoute(Route route) throws Exception {
        Connection conn = DBUtil.getConnection();
        String sql = "update routes " +
                "set route_name = ?, start = ?, destination = ?, " +
                "mileage = ?, cumulative_climb = ?, road_condition = ?, author = ? " +
                "where route_id = ?";

        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, route.getRouteName());
        psmt.setString(2, route.getStart());
        psmt.setString(3, route.getDestination());
        psmt.setString(4, String.valueOf(route.getMileage()));
        psmt.setString(5, String.valueOf(route.getCumulativeClimb()));
        psmt.setString(6, route.getRoadCondition());
        psmt.setString(7, route.getAuthor());
        psmt.setString(8, route.getRouteID());

        psmt.execute();
    }

    @Override
    public void deleteRouteByID(String routeID) throws Exception {
        Connection conn = DBUtil.getConnection();
        String sql = "delete from routes where route_id = ?";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, routeID);
        psmt.execute();
    }

    @Override
    public List<Route> fuzzyQueryRouteByName(String name) throws Exception {
        Connection conn = DBUtil.getConnection();
        Statement stmt = conn.createStatement();
        String pattern = "'%" + name + "%'";
        ResultSet rs = stmt.executeQuery("select " +
                "route_id, route_name, start, destination, mileage, cumulative_climb, road_condition, author " +
                "from routes inner join users " +
                "on author = user_id " +
                "where route_name like " + pattern +
                " or user_name like " + pattern);

        List<Route> routeList = new ArrayList<>();
        while (rs.next()) {
            Route route = new Route();
            route.setRouteID(rs.getString("route_id"));
            route.setRouteName(rs.getString("route_name"));
            route.setStart(rs.getString("start"));
            route.setDestination(rs.getString("destination"));
            route.setMileage(rs.getDouble("mileage"));
            route.setCumulativeClimb(rs.getDouble("cumulative_climb"));
            route.setRoadCondition(rs.getString("road_condition"));
            route.setAuthor(rs.getString("author"));
            routeList.add(route);
        }
        return routeList;
    }

    @Override
    public List<Route> randomlyQueryRoutes() throws Exception {
        Connection conn = DBUtil.getConnection();
        String sql = "select * from routes order by rand() limit ?";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setInt(1, 6);
        ResultSet rs = psmt.executeQuery();

        List<Route> routeList = new ArrayList<>();
        while (rs.next()) {
            Route route = new Route();
            System.out.println(rs.getString("route_name"));
            route.setRouteID(rs.getString("route_id"));
            route.setRouteName(rs.getString("route_name"));
            route.setStart(rs.getString("start"));
            route.setDestination(rs.getString("destination"));
            route.setMileage(rs.getDouble("mileage"));
            route.setCumulativeClimb(rs.getDouble("cumulative_climb"));
            route.setRoadCondition(rs.getString("road_condition"));
            route.setAuthor((rs.getString("author")));
            routeList.add(route);
        }
        for (Route route1 : routeList)
            System.out.println(route1.getRouteName());
        return routeList;
    }

    @Override
    public Route queryRouteByID(String routeID) throws Exception {
        Connection conn = DBUtil.getConnection();
        String sql = "select * from routes where route_id = ?";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, routeID);
        ResultSet rs = psmt.executeQuery();
        Route route = new Route();
        route.setRouteID(routeID);
        while (rs.next()) {
            route.setRouteName(rs.getString("route_name"));
            route.setStart(rs.getString("start"));
            route.setDestination(rs.getString("destination"));
            route.setMileage(rs.getDouble("mileage"));
            route.setCumulativeClimb(rs.getDouble("cumulative_climb"));
            route.setRoadCondition(rs.getString("road_condition"));
            route.setAuthor(rs.getString("author"));
        }
        return route;
    }

    @Override
    public void addKeyPoint(KeyPoint keyPoint) throws Exception {
        Connection conn = DBUtil.getConnection();
        String sql = "insert into keypoints " +
                "(route_id, keypoint_1, keypoint_2, keypoint_3, keypoint_4) " +
                "values(" +
                "?, ?, ?, ?, ?)";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, keyPoint.getRouteID());
        psmt.setString(2, keyPoint.getKeyPoint1());
        psmt.setString(3, keyPoint.getKeyPoint2());
        psmt.setString(4, keyPoint.getKeyPoint3());
        psmt.setString(5, keyPoint.getKeyPoint4());

        psmt.execute();
    }

    @Override
    public void deleteKeyPointByID(String routeID) throws Exception {
        Connection conn = DBUtil.getConnection();
        String sql = "delete from keypoints where route_id = ?";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, routeID);
        psmt.execute();
    }

    @Override
    public void modifyKeyPointByID(KeyPoint keyPoint) throws Exception {
        Connection conn = DBUtil.getConnection();
        String sql = "update keypoints set keypoint_1 = ?, keypoint_2 = ?, " +
                "keypoint_3 = ?, keypoint_4 = ? " +
                "where route_id = ?";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, keyPoint.getKeyPoint1());
        psmt.setString(2, keyPoint.getKeyPoint2());
        psmt.setString(3, keyPoint.getKeyPoint3());
        psmt.setString(4, keyPoint.getKeyPoint4());
        psmt.setString(5, keyPoint.getRouteID());

        psmt.execute();
    }

    @Override
    public KeyPoint queryKeyPointByID(String routeID) throws Exception {
        Connection conn = DBUtil.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select " +
                "route_id, keypoint_1, keypoint_2, keypoint_3, keypoint_4 " +
                "from keypoints " +
                "where route_id = '" + routeID + "'");
        KeyPoint keyPoint = new KeyPoint();
        keyPoint.setRouteID(routeID);
        while (rs.next()) {
            keyPoint.setKeyPoint1(rs.getString("keypoint_1"));
            keyPoint.setKeyPoint2(rs.getString("keypoint_2"));
            keyPoint.setKeyPoint3(rs.getString("keypoint_3"));
            keyPoint.setKeyPoint4(rs.getString("keypoint_4"));
        }
        return keyPoint;
    }
}
