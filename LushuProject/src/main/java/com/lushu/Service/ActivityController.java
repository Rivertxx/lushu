package com.lushu.Service;

import com.lushu.Dao.ActivityDao;
import com.lushu.Dao.ActivityDapImpl;
import com.lushu.Dao.UserDao;
import com.lushu.Dao.UserDaoImpl;
import com.lushu.entity.Activity;
import com.lushu.entity.Participant;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ActivityController {
    static final String SUCCESS = "Success";
    static final String FAIL = "Fail";

    @RequestMapping("/uploadActivity")
    public String uploadActivity(@RequestBody Map<String, Object> map) throws Exception {
        Activity activity = new Activity();
        Date date = new Date();
        String activityID = "A" + String.valueOf(date.getTime());
        activity.setActivityID(activityID);
        activity.setActivityName(map.get("activity_name").toString());
        activity.setRouteID(map.get("route_id").toString());
        String formalDate = map.get("date").toString();
        System.out.println(formalDate);
        String[] pieces = formalDate.split("T");
        //StringBuilder stringBuilder = new StringBuilder();
        //stringBuilder.append(pieces[3]).append(convertMonthToNumber(pieces[1])).append(pieces[2]);

        activity.setDate(pieces[0]);
        activity.setParticipantsNumber(Integer.parseInt(map.get("participants_number").toString()));
        activity.setIntroduction(map.get("introduction").toString());
        activity.setHost(map.get("host").toString());

        ActivityDao activityDao = new ActivityDapImpl();
        activityDao.addActivity(activity);
        return "{\"state\" : \"success\"}";
    }

    private String convertMonthToNumber(String month) {
        switch (month) {
            case "Jan": return "1";
            case "Feb": return "2";
            case "Mar" : return "3";
            case "Apr" : return "4";
            case "May" : return "5";
            case "June" : return "6";
            case "July" : return "7";
            case "Aug" : return "8";
            case "Sept" : return "9";
            case "Oct" : return "10";
            case "Nov" : return "11";
            default: return "12";
        }
    }

    @RequestMapping("/deleteActivity")
    public String deleteActivity(@RequestBody Map<String, Object> map) throws Exception {
        String activityID = map.get("activity_id").toString();
        String userID = map.get("user_id").toString();
        UserDao userDao = new UserDaoImpl();
        String role = userDao.queryRightByID(userID).getRole();
        ActivityDao activityDao = new ActivityDapImpl();
        String host = activityDao.queryActivityByID(activityID).getHost();
        System.out.println(userID);
        System.out.println(host);
        System.out.println(role);
        if (role.equals("normal_user") && !(host.equals(userID)))
            return "{\"state\" : \"fail\"}";
        activityDao.deleteActivityByID(activityID);
        return "{\"state\" : \"success\"}";
    }

    @RequestMapping("/modifyActivity")
    public String modifyActivity(@RequestBody Map<String, Object> map) throws Exception {
        String userID = map.get("user_id").toString();
        String activityID = map.get("activity_id").toString();
        UserDao userDao = new UserDaoImpl();
        String role = userDao.queryRightByID(userID).getRole();
        ActivityDao activityDao = new ActivityDapImpl();
        String host = activityDao.queryActivityByID(activityID).getHost();
        if (role.equals("normal_user") && !host.equals(userID))
            return "{\"state\" : \"fail\"}";

        Activity activity = new Activity();
        activity.setActivityID(map.get("activity_id").toString());
        activity.setActivityName(map.get("activity_name").toString());
        // activity.setRouteID(map.get("route_id").toString());

        String formalDate = map.get("date").toString();
        System.out.println(formalDate);

        String[] pieces = formalDate.split("T");
        activity.setDate(pieces[0]);

        activity.setParticipantsNumber(Integer.parseInt(map.get("participants_number").toString()));
        activity.setIntroduction(map.get("introduction").toString());
        activity.setHost(map.get("host").toString());

        activityDao.modifyActivityByID(activity);
        return "{\"state\" : \"success\"}";
    }

    @RequestMapping("/initialActivities")
    public String initialActivities(@RequestBody Map<String, Object> map) throws Exception {
        ActivityDao activityDao = new ActivityDapImpl();
        List<Activity> activityList = new ArrayList<>();
        activityList = activityDao.randomlyQueryActivities();
        StringBuilder activitiesJsonString = new StringBuilder();
        for (Activity activity : activityList) {
            activitiesJsonString.append(", ").append(activityObjectToJsonString(activity));
        }
        if (activitiesJsonString.length() > 2)
            return "{\"state\" : \"success\", \"activities\" : [" + activitiesJsonString.toString().substring(2) + "]}";
        else
            return "";
    }

    private String activityObjectToJsonString(Activity activity) {
        return "{\"activity_id\" : \"" + activity.getActivityID() + "\"," +
                "\"activity_name\" : \"" + activity.getActivityName() + "\"," +
                "\"route_id\" : \"" + activity.getRouteID() + "\"," +
                "\"date\" : \"" + activity.getDate() + "\"," +
                "\"participants_number\" : \"" + activity.getParticipantsNumber() + "\"," +
                "\"introduction\" : \"" + activity.getIntroduction() + "\"," +
                "\"host\" : \"" + activity.getHost() + "\"}";
    }

    @RequestMapping("/queryActivity")
    public String queryActivity(@RequestBody Map<String, Object> map) throws Exception {
        String activityID = map.get("activity_id").toString();
        Activity activity = new Activity();
        ActivityDao activityDao = new ActivityDapImpl();
        activity = activityDao.queryActivityByID(activityID);
        return "{\"state\" : \"success\", \"activity\" : " + activityObjectToJsonString(activity) + "}";
    }

    @RequestMapping("/fuzzyQueryActivities")
    public String fuzzyQueryActivities(@RequestBody Map<String, Object> map) throws Exception {
        ActivityDao activityDao = new ActivityDapImpl();
        List<Activity> activityList = activityDao.fuzzyQueryActivitiesByName(map.get("name").toString());
        StringBuilder activitiesJsonString = new StringBuilder();
        for (Activity activity : activityList) {
            activitiesJsonString.append(", ").append(activityObjectToJsonString(activity));
        }

        if (activitiesJsonString.length() > 2)
            return "{\"state\" : \"success\", \"activities\" : [" + activitiesJsonString.toString().substring(2) + "]}";
        else
            return "{\"state\" : \"empty\", \"activities\" : \"\"}";
    }

    @RequestMapping("/uploadParticipantInformation")
    public String uploadParticipantInformation(@RequestBody Map<String, Object> map) throws Exception {
        Participant participant = new Participant();
        participant.setActivityID(map.get("activity_id").toString());
        participant.setStudentID(map.get("stu_id").toString());
        participant.setStudentName(map.get("stu_name").toString());
        participant.setStudentSex(map.get("stu_sex").toString());
        participant.setStudentAge(Integer.parseInt(map.get("stu_age").toString()));
        participant.setStudentPhone(map.get("stu_phone").toString());
        participant.setCampus(map.get("campus").toString());

        ActivityDao activityDao = new ActivityDapImpl();
        try {
            activityDao.addParticipant(participant);
        } catch (Exception e) {
            return "{\"state\" : \"fail\"}";
        }
        return "{\"state\" : \"success\"}";
    }

    @RequestMapping("/deleteParticipant")
    public String deleteParticipant(@RequestBody Map<String, Object> map) throws Exception {
        // 这里活动id和学生id共同作为主码么
        String activityID = map.get("activity_id").toString();
        String studentID = map.get("stu_id").toString();
        String userID = map.get("user_id").toString();
        UserDao userDao = new UserDaoImpl();
        String role = userDao.queryRightByID(userID).getRole();
        ActivityDao activityDao = new ActivityDapImpl();
        String host = activityDao.queryActivityByID(activityID).getHost();
        if (role.equals("normal_user") && !host.equals(userID))
            return "{\"state\" : \"fail\"}";
        activityDao.deleteParticipantByID(activityID, studentID);
        return "{\"state\" : \"success\"}";
    }

    @RequestMapping("/modifyParticipant")
    public String modifyParticipant(@RequestBody Map<String, Object> map) throws Exception {
        String activityID = map.get("activity_id").toString();
        String userID = map.get("user_id").toString();
        UserDao userDao = new UserDaoImpl();
        String role = userDao.queryRightByID(userID).getRole();
        ActivityDao activityDao = new ActivityDapImpl();
        String host = activityDao.queryActivityByID(activityID).getHost();

        if (role.equals("normal_user") && !host.equals(userID))
            return "{\"state\" : \"fail\"}";

        Participant participant = new Participant();
        participant.setActivityID(map.get("activity_id").toString());
        participant.setStudentID(map.get("stu_id").toString());
        participant.setStudentName(map.get("stu_name").toString());
        participant.setStudentSex(map.get("stu_sex").toString());
        participant.setStudentAge(Integer.parseInt(map.get("stu_age").toString()));
        participant.setStudentPhone(map.get("stu_phone").toString());
        participant.setCampus(map.get("campus").toString());

        activityDao.modifyParticipantByID(participant);
        return "{\"state\" : \"success\"}";
    }

    @RequestMapping("/queryActivityParticipants")
    public String queryActivityParticipants(@RequestBody Map<String, Object> map) throws Exception {
        String activityID = map.get("activity_id").toString();
        List<Participant> participantList = new ArrayList<>();
        ActivityDao activityDao = new ActivityDapImpl();
        participantList = activityDao.queryParticipantsByActivityID(activityID);
        StringBuilder participantsJsonString = new StringBuilder();
        for (Participant participant : participantList) {
            participantsJsonString.append(", ").append(participantObjectToJsonString(participant));
        }
        if (participantsJsonString.length() > 2)
            return "{\"state\" : \"success\", \"participants\" : " +
                    "[" + participantsJsonString.substring(2) + "]}";
        else
            return "{\"state\" : \"empty\", \"participants\" : \"\"}";
    }

    private String participantObjectToJsonString(Participant participant) {
        return "{\"activity_id\" : \"" + participant.getActivityID() + "\"," +
                "\"stu_name\" : \"" + participant.getStudentName() + "\"," +
                "\"stu_sex\" : \"" + participant.getStudentSex() + "\"," +
                "\"stu_age\" : \"" + participant.getStudentAge() + "\"," +
                "\"stu_phone\" : \"" + participant.getStudentPhone() + "\"," +
                "\"campus\" : \"" + participant.getCampus() + "\"}";
    }
}
