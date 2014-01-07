package com.harsh.dualboot;

import static com.harsh.dualboot.Constants.CM102_DIR;
import static com.harsh.dualboot.Constants.CM11_DIR;
import static com.harsh.dualboot.Constants.D;
import static com.harsh.dualboot.Constants.E;
import static com.harsh.dualboot.Constants.PRIMARY_DATA;
import static com.harsh.dualboot.Constants.PRIMARY_ROM;
import static com.harsh.dualboot.Constants.SECONDARY_ROM;
import static com.harsh.dualboot.Constants.STOCK_DIR;
import static com.harsh.dualboot.Utils.LOG;
import static com.harsh.dualboot.Utils.SU_wop;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static Context mContext;
	Button but_prim, but_sec;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LOG("New Instance",D);
		mContext = getApplicationContext();
		if(!isJanice())
			finish();
		initDirs();
		but_prim = (Button) findViewById(R.id.but_primary);
		but_sec = (Button) findViewById(R.id.but_secondary);
		SharedPreferences mprefs = MainActivity.this.getSharedPreferences("download",0);
		boolean b = mprefs.getBoolean("interuppted", false);
		if(b)
			new File(mprefs.getString("last", null)).delete();
		SharedPreferences.Editor editor = mprefs.edit();
		editor.putBoolean("interuppted", false);
		editor.commit();
		int rom = getROM();
		switch (rom) {
			case PRIMARY_ROM:
				but_prim.setEnabled(false);
				but_sec.setOnClickListener(sec_listner);
				break;
			case SECONDARY_ROM:
				but_sec.setEnabled(false);
				but_prim.setOnClickListener(prim_listner);
				break;
		}
	}
	
	OnClickListener prim_listner = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			ShowDialog(true);
		}
	};
	
	OnClickListener sec_listner = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			ShowDialog(false);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		DownloadActivity.cancel = false;
	}
	
	public void ShowDialog(boolean isSecondary)
    {
		CharSequence[] temp = {"CM 10.2", "CM 11"};
		if (isSecondary)
			temp = new CharSequence[]{"Stock", "CM 10.2", "CM 11"};
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("Select ROM");
		final CharSequence items[] = temp;
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	KernelFlasher kf = new KernelFlasher(MainActivity.this);
		    	if (items[item].equals("Stock")) {
		    		File f = new File(STOCK_DIR + "/stock.md5");
		    		if(!f.exists())
		    			showStockInfoDialog(MainActivity.this);
		    		else
		    			kf.execute("stock");
		    	} else if (items[item].equals("CM 10.2")) {
		    		File f = new File(CM102_DIR + "/CM102_DB.md5");
		    		if(!f.exists()) {
		    			if(!isConnected())
		    				showInfoDialog();
		    			else {
		    				new DownloadActivity(MainActivity.this).execute("http://fs1.d-h.st/download/00092/L6N/CM102_DB.md5","CM102");
		    			}
		    		} else {
		    			kf.execute("CM102");
		    		}
		    	} else if (items[item].equals("CM 11")) {
		    		File f = new File(CM11_DIR + "/CM11_DB.md5");
		    		if(!f.exists()) {
		    			if(!isConnected())
		    				showInfoDialog();
		    			else {
			    			new DownloadActivity(MainActivity.this).execute("http://fs1.d-h.st/download/00092/Dxz/CM11_DB.md5","CM11");
		    			}
		    		} else {
		    			kf.execute("CM11");
		    		}
		    	}
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
    }
	
	public static boolean isJanice() {
		String model = SU_wop("getprop ro.product.device", mContext);
		if (!model.equalsIgnoreCase("GT-i9070")) {
			Toast.makeText(mContext, "Unsupported Device", Toast.LENGTH_SHORT).show();
			LOG("Unsupported Device : "+model, E);
			return false;
		}
		return true;
	}
	
	private void showStockInfoDialog(final Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(R.string.stock_dialog);
		builder.setTitle(R.string.stock_dialog_title);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	return;
            }
        });
		builder.create().show();
	}
	
	private boolean isConnected() {
	    boolean haveConnectedWifi = false;
	    boolean haveConnectedMobile = false;
	    ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    for (NetworkInfo ni : netInfo) {
	        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	            if (ni.isConnected())
	                haveConnectedWifi = true;
	        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	            if (ni.isConnected())
	                haveConnectedMobile = true;
	    }
	    return haveConnectedWifi || haveConnectedMobile;
	}
	
	private void showInfoDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.no_network_dialog);
		builder.setTitle(R.string.no_network_dialog_title);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	return;
            }
        });
		builder.create().show();
	}
	
	private void initDirs() {
		File f = new File(CM102_DIR);
		if(!f.exists())
			f.mkdirs();
		f = new File(CM11_DIR);
		if(!f.exists())
			f.mkdirs();
		f = new File(STOCK_DIR);
		if(!f.exists())
			f.mkdirs();
	}
	
	private int getROM() {
		File f = new File(PRIMARY_DATA);
		if(f.exists())
			return SECONDARY_ROM;
		return PRIMARY_ROM;
	}

}
