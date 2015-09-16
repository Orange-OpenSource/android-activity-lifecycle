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

/**
 * We do not provide ON_CREATE nor ON_SAVE_INSTANCE_STATE events since we consider
 * that their addition will add extra complexity (bundle), and that anyway these
 * events will not be used often in real world applications
 */
public enum LifecycleEvent {
  ON_START, ON_RESUME, ON_PAUSE, ON_STOP, ON_DESTROY
}
