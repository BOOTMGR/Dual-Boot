/*******************************************************************************
 * Copyright (c) 2014 Harsh Panchal <panchal.harsh18@gmail.com>.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 ******************************************************************************/
package com.harsh.dualboot;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AppShareActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_share);
		ListView lv = (ListView) findViewById(R.id.lv1);
		final PackageManager pm = getPackageManager();
		List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
		final List<String> appLabel = new ArrayList<String>();
		final List<String> packageName = new ArrayList<String>();
		for(ApplicationInfo appinfo : packages) {
			if ((appinfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0)
				continue;
			appLabel.add((String) pm.getApplicationLabel(appinfo));
			packageName.add(appinfo.packageName);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, appLabel);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String s = Utils.SU_wop("pm path " + packageName.get(arg2), AppShareActivity.this, true);
				String apk = s.replace("package:/data/app/", "");
				try {
					Process p = Runtime.getRuntime().exec("su -c ln -s /primary_data/app/" + apk + " /data/secondary_data/data/app/" + apk);
					p.waitFor();
					Toast.makeText(AppShareActivity.this, "Shared : " + appLabel.get(arg2), Toast.LENGTH_SHORT).show();
				} catch(Exception e) {
					Toast.makeText(AppShareActivity.this, "Error executing SU", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
