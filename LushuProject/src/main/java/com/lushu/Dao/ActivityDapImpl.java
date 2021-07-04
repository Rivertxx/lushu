package com.lushu.Dao;

import com.lushu.entity.Activity;
import com.lushu.entity.Participant;

import java.rmi.activation.ActivationID;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ActivityDapImpl implements ActivityDao {
    @Override
    public void addActivity(Activity activity) throws Exception {
        Connection conn = DBUtil.getConnection();
        String sql = "insert into activities " +
                "(activity_id, activity_name, route_id, date, participants_number, introduction, host) " +
                "values (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, activity.getActivityID());
        psmt.setString(2, activity.getActivityName());
        psmt.setString(3, activity.getRouteID());
        psmt.setString(4, activity.getDate());
        psmt.setString(5, String.valueOf(activity.getParticipantsNumber()));
        psmt.setString(6, activity.getIntroduction());
        psmt.setString(7, activity.getHost());

        psmt.execute();
    }

    @Override
    public void deleteActivityByID(String activityID) throws Exception {
        Connection conn = DBUtil.getConnection();
        String sql = "delete from activities where activity_id = ?";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, activityID);
        psmt.execute();
    }

    @Override
    public void modifyActivityByID(Activity activity) throws Exception {
        Connection conn = DBUtil.getConnection();
        String sql = "update activities " +
                "set activity_name = ?, date = ?, " +
                "participants_number = ?, introduction = ?, host = ? " +
                "where activity_id = ?";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, activity.getActivityName());
        psmt.setString(2, activity.getDate());
        psmt.setString(3, String.valueOf(activity.getParticipantsNumber()));
        psmt.setString(4, activity.getIntroduction());
        psmt.setString(5, activity.getHost());
        psmt.setString(6, activity.getActivityID());

        psmt.execute();
    }

    @Override
    public Activity queryActivityByID(String activityID) throws Exception {
        Connection conn = DBUtil.getConnection();
        String sql = "select * from activities where activity_id = ?";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, activityID);
        ResultSet rs = psmt.executeQuery();
        Activity activity = new Activity();
        activity.setActivityID(activityID);
        while (rs.next()) {
            activity.setActivityName(rs.getString("activity_name"));
            activity.setRouteID(rs.getString("route_id"));
            activity.setDate(rs.getString("date"));
            activity.setParticipantsNumber(rs.getInt("participants_number"));
            activity.setIntroduction(rs.getString("introduction"));
            activity.setHost(rs.getString("host"));
        }
        return activity;
    }

    @Override
    public List<Activity> randomlyQueryActivities() throws Exception {
        Connection conn = DBUtil.getConnection();
        String sql = "select * from activities order by rand() limit ?";
        PreparedStatement psmt = conn.prepareStatement(sql);
        // 随机返回6条活动信息
        psmt.setInt(1, 6);
        ResultSet rs = psmt.executeQuery();
        List<Activity> activityList = new ArrayList<>();
        while (rs.next()) {
            Activity activity = new Activity();
            activity.setActivityID(rs.getString("activity_id"));
            activity.setActivityName(rs.getString("activity_name"));
            activity.setRouteID(rs.getString("route_id"));
            activity.setDate(rs.getString("date"));
            activity.setParticipantsNumber(rs.getInt("participants_number"));
            activity.setIntroduction(rs.getString("introduction"));
            activity.setHost(rs.getString("host"));
            activityList.add(activity);
        }
        return activityList;
    }

    @Override
    public List<Activity> fuzzyQueryActivitiesByName(String name) throws Exception {
        Connection conn = DBUtil.getConnection();
        String pattern = "'%" + name + "%'";
        String sql = "select * from activities where activity_name like " + pattern;
        PreparedStatement psmt = conn.prepareStatement(sql);
        ResultSet rs = psmt.executeQuery();
        List<Activity> activityList = new ArrayList<>();
        while (rs.next()) {
            Activity activity = new Activity();
            activity.setActivityID(rs.getString("activity_id"));
            activity.setActivityName(rs.getString("activity_name"));
            activity.setDate(rs.getString("date"));
            activity.setRouteID(rs.getString("route_id"));
            activity.setParticipantsNumber(rs.getInt("participants_number"));
            activity.setIntroduction(rs.getString("introduction"));
            activity.setHost(rs.getString("host"));
            activityList.add(activity);
        }
        return activityList;
    }

    @Override
    public void addParticipant(Participant participant) throws Exception {
        Connection conn = DBUtil.getConnection();
        String sql = "insert into participants " +
                "(activity_id, stu_id, stu_name, stu_sex, stu_age, stu_phone, campus) " +
                "values (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, participant.getActivityID());
        psmt.setString(2, participant.getStudentID());
        psmt.setString(3, participant.getStudentName());
        psmt.setString(4, participant.getStudentSex());
        psmt.setString(5, String.valueOf(participant.getStudentAge()));
        psmt.setString(6, participant.getStudentPhone());
        psmt.setString(7, participant.getCampus());

        psmt.execute();
    }

    @Override
    public void deleteParticipantByID(String activityID, String studentID) throws Exception {
        Connection conn = DBUtil.getConnection();
        String sql = "delete from participants where stu_id = ? and activity_id = ?";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, studentID);
        psmt.setString(2, activityID);
        psmt.execute();
    }

    @Override
    public void modifyParticipantByID(Participant participant) throws Exception {
        Connection conn = DBUtil.getConnection();
        String sql = "update participants " +
                "set stu_name = ?, stu_sex = ?, stu_age = ?, stu_phone = ?, campus = ? " +
                "where stu_id = ? and activity_id = ?";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(7, participant.getActivityID());
        psmt.setString(6, participant.getStudentID());
        psmt.setString(1, participant.getStudentName());
        psmt.setString(2, participant.getStudentSex());
        psmt.setString(3, String.valueOf(participant.getStudentAge()));
        psmt.setString(4, participant.getStudentPhone());
        psmt.setString(5, participant.getCampus());

        psmt.execute();
    }

    @Override
    public List<Participant> queryParticipantsByActivityID(String activityID) throws Exception {
        Connection conn = DBUtil.getConnection();
        String sql = "select * from participants where activity_id = ?";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, activityID);
        ResultSet rs = psmt.executeQuery();
        List<Participant> participantList = new ArrayList<>();

        while (rs.next()) {
            Participant participant = new Participant();
            participant.setActivityID(activityID);
            participant.setStudentName(rs.getString("stu_name"));
            participant.setStudentID(rs.getString("stu_id"));
            participant.setStudentSex(rs.getString("stu_sex"));
            participant.setStudentAge(rs.getInt("stu_age"));
            participant.setStudentPhone(rs.getString("stu_phone"));
            participant.setCampus(rs.getString("campus"));
            participantList.add(participant);
        }
        return participantList;
    }
}
