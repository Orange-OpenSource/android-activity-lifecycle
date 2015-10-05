package com.orange.android.activitylifecycle;
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
import android.app.Application;
import android.content.Context;
import lombok.NonNull;
import lombok.Synchronized;

/**
 * Listens to lifecycle events of all activities of the application, then dispatches to
 * ${@link Lifecycle} listener clients depending on the activity it is listening to.
 *
 * Listeners are linked to a context which is provided when we register. We assume that this context
 * is an instance of the Activity, so that we can unregister the listeners automatically when we receive
 * the "on destroy" event for this activity.
 * The ${@link #reset() reset} function is here to clean up everything at start up since we know that under rare
 * conditions android may not send the "on destroy" event properly.
 *
 * Example:
 *  - the application has three running activities A1, A2, A3
 *  - two clients: C1A1 which listens for A1 events, C2A2 which listens for A2 events
 *  then:
 *  - the ${@link ApplicationLifecycle} instance will receive all A1/A2/A3 lifecycle events
 *  - the ${@link ApplicationLifecycle} instance will dispatch A1 events only to C1A1, and A2 events only to C2A2
 */
public class ApplicationLifecycle {

  private static final ApplicationLifecycle sInstance = new ApplicationLifecycle();

  private final ApplicationCallbacksListener callbacksListener = new ApplicationCallbacksListener();

  private boolean initialized;

  /**
   * if not already done, we initialize here the component in order to receive activity lifecycle events.
   * @param application needed to register through the android system API
   */
  @Synchronized
  private static void init(Application application) {
    if (!sInstance.initialized) {
      application.registerActivityLifecycleCallbacks(sInstance.callbacksListener);
      sInstance.initialized = true;
    }
  }

  /**
   * A client class uses this function to register for receiving lifecycle events of the
   * provided activity context.
   * Here we let you provide a context which is used to match the associated activity, but
   * if your context is not the activity (could be the application context), it will raise an
   * exception.
   * @param lifecycle client listener which wants to receive lifecycle events for the associated context
   * @param context activity context. NB: not the application context!
   */
  public static void register(@NonNull Lifecycle lifecycle, @NonNull Context context) {
    if (context instanceof Activity) {
      init(((Activity)context).getApplication());
      doRegister(lifecycle, context);
    } else {
      throw new IllegalArgumentException("The provided context is not an activity context; application context ? Please register with the activity context instead.");
    }
  }

  @Synchronized
  private static void doRegister(Lifecycle lifecycle, Context context) {
    sInstance.callbacksListener.register(lifecycle, context);
  }

  /**
   * Can be used to clean up everything at start up since we know that under rare
   * conditions android may not send the "on destroy" event properly, which would
   * involve Context memory leaks...
   */
  @Synchronized
  public static void reset() {
    sInstance.callbacksListener.getListeners().clear();
  }

  /**
   * If debug mode is set, each activity lifecycle event will appear in the log
   * @param enable
   */
  public static void setDebugEnabled(boolean enable) {
    sInstance.callbacksListener.getDebug().setEnabled(enable);
  }

  /** Print in the log listeners of recorded activities */
  public static void showListeners() {
    sInstance.callbacksListener.getDebug().showListeners();
  }
}
