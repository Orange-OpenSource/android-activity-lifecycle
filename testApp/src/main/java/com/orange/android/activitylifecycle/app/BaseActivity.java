/*
 * Copyright (C) 2015 Orange
 * Authors: Christophe Maldivi
 *
 * This software is the confidential and proprietary information of Orange.
 * You shall not disclose such confidential information and shall use it only
 * in accordance with the terms of the license agreement you entered into
 * with Orange.
 */
package com.orange.android.activitylifecycle.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.orange.android.activitylifecycle.ApplicationLifecycle;
import com.orange.android.activitylifecycle.LifecycleEvent;
import com.orange.android.activitylifecycle.Lifecycle;

public abstract class BaseActivity extends Activity implements Lifecycle {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    ApplicationLifecycle.setDebugEnabled(true);
    ApplicationLifecycle.register(this, this);
    super.onCreate(savedInstanceState);
    TestLog.onCreate(this, savedInstanceState);
    setContentView(R.layout.main);
    ((TextView)findViewById(R.id.text)).setText(getClass().getSimpleName());
  }

  @Override
  public void onLifecycleEvent(LifecycleEvent lifecycleEvent) {
    TestLog.logLifecycleEvent(this, lifecycleEvent);
  }

  @Override
  protected void onStart() {
    super.onStart();
    TestLog.onStart(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    TestLog.onPause(this);
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    TestLog.onRestart(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    TestLog.onResume(this);
    ApplicationLifecycle.showListeners();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    TestLog.onSaveInstanceState(this, outState);
  }

  @Override
  protected void onStop() {
    super.onStop();
    TestLog.onStop(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    TestLog.onDestroy(this);
  }

  public void startActivity1(View view) {
    launchActivity(Activity1.class);
  }
  public void startActivity2(View view) {
    launchActivity(Activity2.class);
  }
  public void startActivity3(View view) {
    launchActivity(Activity3.class);
  }

  private void launchActivity(Class<?> clazz) {
    startActivity(new Intent(this, clazz));
  }
}
