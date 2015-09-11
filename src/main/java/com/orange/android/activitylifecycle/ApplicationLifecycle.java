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

import java.util.ArrayList;
import java.util.List;

/**
 * Listens to lifecycle events of all activities of the application, then dispatches to listener
 * clients depending on the activity it is listening to.
 *
 * Example:
 *  - the application has three running activities A1, A2, A3
 *  - two clients: C1A1 which listens for A1 events, C2A2 which listens for A2 events
 *  then:
 *  - the ${ApplicationLifecycle} instance will receive all A1/A2/A3 lifecycle events
 *  - the ${ApplicationLifecycle} instance will dispatch A1 events only to C1A1, and A2 events
 *  only to C2A2
 */
public class ApplicationLifecycle {

  private static final ApplicationLifecycle sInstance = new ApplicationLifecycle();

  private final ApplicationCallbacksListener callbacksListener = new ApplicationCallbacksListener();

  private boolean initialized;

  public static void init(Application application) {
    application.registerActivityLifecycleCallbacks(sInstance.callbacksListener);
    sInstance.initialized = true;
  }

  /**
   * A client class uses this function to register for receiving lifecycle events of the
   * provided activity class
   * @param lifecycleListener client listener which wants to receive lifecycle events for the associated class
   * @param activityClazz activity for which we want to receive lifecycle events
   */
  public static void register(@NonNull LifecycleListener lifecycleListener, @NonNull Class<?> activityClazz) {
    if (sInstance.initialized) {
      doRegister(lifecycleListener, activityClazz);
    } else {
      throw new IllegalStateException("Component not initialized, please call " + ApplicationLifecycle.class.getSimpleName() + " init class");
    }
  }

  private static void doRegister(LifecycleListener lifecycleListener, Class<?>activityClazz) {
    List<LifecycleListener> listeners = sInstance.callbacksListener.getListenerMap().get(activityClazz);
    if (listeners == null) {
      listeners = new ArrayList<>();
      sInstance.callbacksListener.getListenerMap().put(activityClazz, listeners);
    }
    listeners.add(lifecycleListener);
  }

  /**
   * A client class uses this function to register for receiving lifecycle events of the
   * provided activity class.
   * Here we let you provide a context which is used to match the associated activity, but
   * if your context is not the activity (could be the application context), it will raise an
   * exception.
   * @param lifecycleListener client listener which wants to receive lifecycle events for the associated context
   * @param context activity context which can be cast to the activity object at your own risk
   */
  public static void register(@NonNull LifecycleListener lifecycleListener, @NonNull Context context) {
    if (context instanceof Activity) {
      checkInit(context);
      register(lifecycleListener, ((Activity) context).getClass());
    } else {
      throw new IllegalArgumentException("The provided context is not an activity context; application context ? Please register with the activity class instead.");
    }
  }

  private static void checkInit(Context context) {
    if (!sInstance.initialized) {
      init(((Activity)context).getApplication());
    }
  }
}
