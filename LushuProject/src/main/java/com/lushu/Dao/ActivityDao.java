package com.lushu.Dao;

import com.lushu.entity.Activity;
import com.lushu.entity.Participant;

import java.util.List;

public interface ActivityDao {
    void addActivity(Activity activity) throws Exception;

    void deleteActivityByID(String activityID) throws Exception;

    void modifyActivityByID(Activity activity) throws  Exception;

    Activity queryActivityByID(String activityID) throws Exception;

    List<Activity> randomlyQueryActivities() throws Exception;

    List<Activity> fuzzyQueryActivitiesByName(String name) throws Exception;

    void addParticipant(Participant participant) throws Exception;

    void deleteParticipantByID(String activityID, String studentID) throws Exception;

    void modifyParticipantByID(Participant participant) throws Exception;

    List<Participant> queryParticipantsByActivityID(String activityID) throws Exception;
}
