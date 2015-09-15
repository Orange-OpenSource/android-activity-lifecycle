/*
 * Copyright (C) 2015 Orange
 * Authors: Christophe Maldivi
 *
 * This software is the confidential and proprietary information of Orange.
 * You shall not disclose such confidential information and shall use it only
 * in accordance with the terms of the license agreement you entered into
 * with Orange.
 */
package com.orange.dgil.trail.app.android.drawingtest;

import android.app.Activity;
import android.os.Bundle;

public class Activity1 extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    initActivityListener();
  }

  private void initActivityListener() {

  }

}
