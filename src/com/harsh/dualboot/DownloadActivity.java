/*******************************************************************************
 * Copyright (c) 2014 Harsh Panchal <panchal.harsh18@gmail.com>.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 ******************************************************************************/
package com.harsh.dualboot;

import static com.harsh.dualboot.Constants.CM102;
import static com.harsh.dualboot.Constants.CM11;
import static com.harsh.dualboot.Constants.E;
import static com.harsh.dualboot.Utils.LOG;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.os.AsyncTask;

public class DownloadActivity extends AsyncTask<String, String, String> {
	
	private ProgressDialog mProgressDialog;
	static public boolean cancel = false;
	private Context context;
	static String ver;

	public DownloadActivity(final Context context) 
	{
	     this.context = context;
	     mProgressDialog = new ProgressDialog(context);
	     mProgressDialog.setMessage("Downloading file...");
	     mProgressDialog.setIndeterminate(false);
	     mProgressDialog.setMax(100);
	     mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	     mProgressDialog.setCancelable(false);
	     mProgressDialog.setOnCancelListener(new OnCancelListener(){
	    	 @Override
	    	 public void onCancel(DialogInterface arg0){
	    	    cancel = true;
	    	    SharedPreferences mprefs = context.getSharedPreferences("download",0);
	    	    SharedPreferences.Editor editor = mprefs.edit();
	    	    editor.putBoolean("interuppted", true);
	    	    editor.commit();
	    	}
	    });
	}
	
	@Override
	protected void onPreExecute() {
	    super.onPreExecute();
	    mProgressDialog.show();
	}

	@Override
	protected String doInBackground(String... url) {
		SharedPreferences mprefs = context.getSharedPreferences("download",0);
	    SharedPreferences.Editor editor = mprefs.edit();
	    try {
	    	String path = "CM11";
	    	if(url[1].equals("CM11")){
	    		path = CM11;
	    		ver = "CM11";
	    		editor.putString("last", CM11);
	    		editor.commit();
	    	} else if(url[1].equals("CM102")) {
	    		path = CM102;
	    		ver = "CM102";
	    		editor.putString("last", CM102);
	    		editor.commit();
	    	}
	        File dest = new File(path);
	        URL u = new URL(url[0]);
	        HttpURLConnection c = (HttpURLConnection) u.openConnection();
	        c.setRequestMethod("GET");
	        c.connect();
	        int lenghtOfFile = c.getContentLength();
	        if(!dest.exists()) {
	        	dest.createNewFile();
	        }
	        FileOutputStream f = new FileOutputStream(dest);
	        InputStream in = c.getInputStream();
	        byte[] buffer = new byte[1024];
	        int len1 = 0;
	        long total = 0;
	        while ((len1 = in.read(buffer)) > 0) {
	    		if(cancel)
	    			break;
	            total += len1;
	            publishProgress("" + (int)((total*100)/lenghtOfFile));
	            f.write(buffer, 0, len1);
	        }
	        f.close();
	    } catch (Exception e) {
	        LOG(e.toString(), E);
	    }
	    return null;
	}

	protected void onProgressUpdate(String... progress) {
	     mProgressDialog.setProgress(Integer.parseInt(progress[0]));
	}

	@Override
	protected void onPostExecute(String unused) {
	    mProgressDialog.dismiss();
	    if (!cancel)
	    	new KernelFlasher(context).execute(ver);
	}	
}
