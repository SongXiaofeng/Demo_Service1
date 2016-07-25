package com.example.demo_service;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.util.Log;

public class ServiceUtils {
	
	
	public static boolean isServiceRunning(Context mContext,String className) {  
        boolean isRunning = false;  
        ActivityManager activityManager = (ActivityManager)  
        mContext.getSystemService(Context.ACTIVITY_SERVICE);   
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);  
  
        if (!(serviceList.size()>0)) {  
            return false;  
        }  
  
        for (int i=0; i<serviceList.size(); i++) {  
        	Log.d("sxf",serviceList.get(i).service.getClassName());
        	if (serviceList.get(i).service.getClassName().equals(className)) {
                isRunning = true;  
                break;  
            }  
        }  
        return isRunning;  
} 
}
