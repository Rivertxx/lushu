package com.lushu.Dao;

import com.lushu.entity.KeyPoint;
import com.lushu.entity.Route;

import java.util.List;

public interface RouteDao {
    void addRoute(Route route) throws Exception;

    void modifyRoute(Route route) throws Exception;

    void deleteRouteByID(String routeID) throws Exception;

    List<Route> fuzzyQueryRouteByName(String name) throws Exception;

    List<Route> randomlyQueryRoutes() throws Exception;

    Route queryRouteByID(String routeID) throws Exception;

    void addKeyPoint(KeyPoint keyPoint) throws Exception;

    void deleteKeyPointByID(String RouteID) throws Exception;

    void modifyKeyPointByID(KeyPoint keyPoint) throws Exception;

    KeyPoint queryKeyPointByID(String routeID) throws Exception;
}
