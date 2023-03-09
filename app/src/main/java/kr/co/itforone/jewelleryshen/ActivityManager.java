package kr.co.itforone.jewelleryshen;

import android.app.Activity;

import java.util.ArrayList;

public class ActivityManager {

    //private static kr.co.itforone.jewelleryshen.ActivityManager activityMananger = null;
    private static ActivityManager activityManager = null;
    private ArrayList<Activity> activityList = null;

    private ActivityManager() {
        activityList = new ArrayList<Activity>();
    }

    public static kr.co.itforone.jewelleryshen.ActivityManager getInstance() {

        if (activityManager == null) { //if( kr.co.itforone.jewelleryshen.ActivityManager.activityMananger == null ) {
            activityManager = new kr.co.itforone.jewelleryshen.ActivityManager();
        }
        return activityManager;
    }

    public ArrayList<Activity> getActivityList() {
        return activityList;
    }
    public Activity getActivityLast() {
            return activityList.get(activityList.size()-1);
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public boolean removeActivity(Activity activity) {
        return activityList.remove(activity);
    }

/*    public void clearManager(){

        activityList.clear();

    }*/

    public void finishAllActivity() {
        for (Activity activity : activityList) {

            activity.finish();

        }
    }

}