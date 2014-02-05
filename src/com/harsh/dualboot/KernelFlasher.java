/*******************************************************************************
 * Copyright (c) 2014 Harsh Panchal <panchal.harsh18@gmail.com>.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 ******************************************************************************/
package com.harsh.dualboot;

import static com.harsh.dualboot.Constants.BOOT_PART;
import static com.harsh.dualboot.Constants.CM102;
import static com.harsh.dualboot.Constants.CM11;
import static com.harsh.dualboot.Constants.E;
import static com.harsh.dualboot.Constants.STOCK;
import static com.harsh.dualboot.Utils.LOG;
import static com.harsh.dualboot.Constants.*;

import java.io.DataOutputStream;
import java.io.File;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class KernelFlasher extends AsyncTask<String, Void, Void> {
	
	private ProgressDialog mProgressDialog;
	private Context context;
	
	public KernelFlasher(final Context context) 
	{
	     this.context = context;
	     mProgressDialog = new ProgressDialog(context);
	     mProgressDialog.setMessage("Flashing Kernel...");
	     mProgressDialog.setIndeterminate(true);
	     mProgressDialog.setCancelable(false);
	     mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	}
	
	@Override
	protected void onPreExecute() {
	    super.onPreExecute();
	    mProgressDialog.show();
	}
	
	@Override
	protected void onPostExecute(Void v) {
	    mProgressDialog.dismiss();
	    Toast.makeText(context, "Kernel Flashed successfully", Toast.LENGTH_SHORT).show();
	    Toast.makeText(context, "Reboot your device now", Toast.LENGTH_SHORT).show();
	    File f = new File("/sys/kernel/abb-ponkey/emulator");
	    if(f.exists())
	    	new SU(context).execute("echo 800 > /sys/kernel/abb-ponkey/emulator");
	}

	@Override
	protected Void doInBackground(String... arg) {
		Process process;
		try {
			   process = Runtime.getRuntime().exec("su");
	           DataOutputStream os = new DataOutputStream(process.getOutputStream());
	           String path = STOCK;
	           if (arg[0].equals("CM11"))
	        	   path = CM11;
	           else if (arg[0].equals("CM102"))
	        	   path = CM102;
	           else if (arg[0].equals("stock"))
	        	   path = STOCK;
	           else if (arg[0].equals("CM11_stock"))
	        	   path = CM11_stock;
	           else if (arg[0].equals("CM102_stock"))
	        	   path = CM102_stock;
	           else if (arg[0].equals("MIUI"))
	        	   path = MIUI;
	           else if (arg[0].equals("MIUI_stock"))
	        	   path = MIUI_Stock;
	           else if (arg[0].equals("unknown"))
	        	   path = UNKNOWN;
	           os.writeBytes("dd if=" + path + " of=" + BOOT_PART + "\n");
	           os.writeBytes("exit\n");
	           os.flush();
	           process.waitFor();
	       } catch (Exception e) {
	           Toast.makeText(context, "Error Occured...!", Toast.LENGTH_SHORT).show();
	           LOG("Error getting SU rights", E);
	       }
		return null;
	}

}
