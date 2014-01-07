package com.harsh.dualboot;

import static com.harsh.dualboot.Constants.E;
import static com.harsh.dualboot.Utils.LOG;

import java.io.DataOutputStream;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class SU extends AsyncTask<String, Void, Void> {
	
	private ProgressDialog mProgressDialog;
	static public boolean cancel = false;
	private Context context;
	static int result;

	public SU(Context context) 
	{
	     this.context = context;
	     mProgressDialog = new ProgressDialog(context);
	     mProgressDialog.setTitle("Processing...");
	     mProgressDialog.setMessage("Please wait...");
	     mProgressDialog.setIndeterminate(true);
	     mProgressDialog.setCancelable(false);
	}
	
	@Override
	protected void onPreExecute() {
	    super.onPreExecute();
	    mProgressDialog.show();	    	
	}
	
	@Override
	protected void onPostExecute(Void unused) {
	    mProgressDialog.dismiss();
	}

	@Override
	protected Void doInBackground(String... cmds) {
		Process process;
		try {
			   process = Runtime.getRuntime().exec("su");
	           DataOutputStream os = new DataOutputStream(process.getOutputStream());
	           for(int i=0;i<cmds.length;i++)
	               os.writeBytes(cmds[i]+"\n");
	           os.writeBytes("exit\n");
	           os.flush();
	           process.waitFor();
	           result = process.exitValue();
	       } catch (Exception e) {
	           Toast.makeText(context, "Error Occured...!", Toast.LENGTH_SHORT).show();
	           LOG("Error getting SU rights", E);
	       }
	       return null;
	   }
	
}
