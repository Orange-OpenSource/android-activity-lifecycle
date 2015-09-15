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
import android.os.Bundle;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
class ApplicationCallbacksListener implements Application.ActivityLifecycleCallbacks {

  private final Map<Context, List<LifecycleListener>> listenerMap = new HashMap<>();

  @Override
  public void onActivityCreated(Activity activity, Bundle bundle) {
    notify(activity.getClass(), LifecycleEvent.ON_CREATE, bundle);
  }

  @Override
  public void onActivityStarted(Activity activity) {
    notify(activity.getClass(), LifecycleEvent.ON_START);
  }

  @Override
  public void onActivityResumed(Activity activity) {
    notify(activity.getClass(), LifecycleEvent.ON_RESUME);
  }

  @Override
  public void onActivityPaused(Activity activity) {
    notify(activity.getClass(), LifecycleEvent.ON_PAUSE);
  }

  @Override
  public void onActivityStopped(Activity activity) {
    notify(activity.getClass(), LifecycleEvent.ON_STOP);
  }

  @Override
  public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    notify(activity.getClass(), LifecycleEvent.ON_SAVE_INSTANCE_STATE, bundle);
  }

  @Override
  public void onActivityDestroyed(Activity activity) {
    notify(activity.getClass(), LifecycleEvent.ON_DESTROY);
    listenerMap.remove(activity);
  }

  private void notify(Class<?> clazz, LifecycleEvent event) {
    notify(clazz, event, null);
  }

  private void notify(Class<?> clazz, LifecycleEvent event, Bundle bundle) {
    List<LifecycleListener> listeners = listenerMap.get(clazz);
    if (listeners != null) {
      for (LifecycleListener lifecycleListener : listeners) {
        lifecycleListener.onLifecycleEvent(event, bundle);
      }
    }
  }
}
