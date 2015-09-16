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
import android.util.SparseArray;
import lombok.Getter;

import java.util.List;

@Getter()
class ApplicationCallbacksListener implements Application.ActivityLifecycleCallbacks {

  private final SparseArray<List<Lifecycle>> listeners = new SparseArray<>();

  @Override
  public void onActivityStarted(Activity activity) {
    notify(activity, LifecycleEvent.ON_START);
  }

  @Override
  public void onActivityResumed(Activity activity) {
    notify(activity, LifecycleEvent.ON_RESUME);
  }

  @Override
  public void onActivityPaused(Activity activity) {
    notify(activity, LifecycleEvent.ON_PAUSE);
  }

  @Override
  public void onActivityStopped(Activity activity) {
    notify(activity, LifecycleEvent.ON_STOP);
  }

  @Override
  public void onActivityDestroyed(Activity activity) {
    notify(activity, LifecycleEvent.ON_DESTROY);
    listeners.remove(activity.hashCode());
  }

  private void notify(Context context, LifecycleEvent event) {
    List<Lifecycle> contextListeners = listeners.get(context.hashCode());
    if (contextListeners != null) {
      for (Lifecycle lifecycle : contextListeners) {
        lifecycle.onLifecycleEvent(event);
      }
    }
  }


  @Override
  public void onActivityCreated(Activity activity, Bundle bundle) {
    // useless
  }
  @Override
  public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    // useless
  }
}
