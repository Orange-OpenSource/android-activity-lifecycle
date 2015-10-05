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