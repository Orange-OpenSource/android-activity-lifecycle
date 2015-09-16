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

public interface Lifecycle {
  /**
   * Event for the activity we are listening to
   * @param lifecycleEvent
   */
  void onLifecycleEvent(LifecycleEvent lifecycleEvent);
}