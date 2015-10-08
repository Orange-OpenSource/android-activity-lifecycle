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

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
class Debug {
  private static final String DEBUG_TAG = "ActivityLifeCycle";
  private final SparseArray<List<Lifecycle>> listeners;
  private final SparseArray<String> activitiesName = new SparseArray<>();

  @Setter
  private boolean enabled;

  void showEvent(Context context, String message) {
    if (enabled) {
      Log.i(DEBUG_TAG, String.format("%s: %s", context, message));
    }
  }

  void showListeners() {
    for (int i = 0; i < listeners.size(); i++) {
      showListenersFor(listeners.keyAt(i));
    }
  }

  private void showListenersFor(int contextHashCode) {
    StringBuilder stringBuilder = new StringBuilder("");
    for (Lifecycle lifecycle : listeners.get(contextHashCode)) {
      stringBuilder.append(String.format(" %s", lifecycle.getClass().getName()));
    }
    Log.i(DEBUG_TAG, String.format("%s (%x) listeners: %s", activitiesName.get(contextHashCode), contextHashCode, stringBuilder));
  }

  void register(Context context) {
    activitiesName.put(context.hashCode(), context.getClass().getName());
  }
}