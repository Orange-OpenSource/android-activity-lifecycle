package com.orange.android.activitylifecycle;
/*
 * Copyright (C) Orange
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter()
class ApplicationCallbacksListener implements Application.ActivityLifecycleCallbacks {

  private final SparseArray<List<Lifecycle>> listeners = new SparseArray<>();
  private final Debug debug = new Debug(listeners);

  void register(Lifecycle lifecycle, Context context) {
    List<Lifecycle> contextListeners = listeners.get(context.hashCode());
    if (contextListeners == null) {
      contextListeners = new ArrayList<>();
      listeners.put(context.hashCode(), contextListeners);
      debug.register(context);
    }
    contextListeners.add(lifecycle);
  }

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
    debug.showEvent(context, event.toString());
    List<Lifecycle> contextListeners = listeners.get(context.hashCode());
    if (contextListeners != null) {
      for (Lifecycle lifecycle : contextListeners) {
        lifecycle.onLifecycleEvent(event);
      }
    }
  }

  @Override
  public void onActivityCreated(Activity activity, Bundle bundle) {
    debug.showEvent(activity, String.format("ON_CREATE%s", bundle == null ? "" : " / Bundle: " + bundle.toString()));
  }
  @Override
  public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    debug.showEvent(activity, String.format("ON_SAVE_INSTANCE_STATE%s", bundle == null ? "" : " / Bundle: " + bundle.toString()));
  }
}