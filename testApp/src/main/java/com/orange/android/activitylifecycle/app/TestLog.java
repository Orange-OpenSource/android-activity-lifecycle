package com.orange.android.activitylifecycle.app;
/*
 * Copyright (C) 2015 Orange
 * Authors: Christophe Maldivi
 *
 * This software is the confidential and proprietary information of Orange.
 * You shall not disclose such confidential information and shall use it only
 * in accordance with the terms of the license agreement you entered into
 * with Orange.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.orange.android.activitylifecycle.LifecycleEvent;

public class TestLog {
  
  public static void logLifecycleEvent(Activity activity, LifecycleEvent lifecycleEvent, Bundle bundle) {
    Log.i(getTag(activity), "received lifecycleEvent: " + lifecycleEvent + (bundle == null ? "" : " / bundle: " + bundle.toString()));
  }

  public static void onCreate(Activity activity, Bundle bundle) {
    Log.d(getTag(activity), "activity method called: onCreate" + (bundle == null ? "" : " / bundle: " + bundle.toString()));
  }

  public static void onStart(Activity activity) {
    Log.d(getTag(activity), "activity method called: onStart");
  }

  public static void onPause(Activity activity) {
    Log.d(getTag(activity), "activity method called: onPause");  }

  public static void onRestart(Activity activity) {
    Log.d(getTag(activity), "activity method called: onRestart");  }

  public static void onResume(Activity activity) {
    Log.d(getTag(activity), "activity method called: onResume");
  }

  public static void onSaveInstanceState(Activity activity, Bundle bundle) {
    Log.d(getTag(activity), "activity method called: onSaveInstanceState" + (bundle == null ? "" : " / bundle: " + bundle.toString()));
  }

  public static void onStop(Activity activity) {
    Log.d(getTag(activity), "activity method called: onStop");
  }

  public static void onDestroy(Activity activity) {
    Log.d(getTag(activity), "activity method called: onDestroy");
  }

  private static String getTag(Activity activity) {
    return String.format("(%s - %x)", activity.getClass().getSimpleName(), activity.hashCode());
  }
}
