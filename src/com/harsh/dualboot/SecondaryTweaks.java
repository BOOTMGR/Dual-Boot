/*******************************************************************************
 * Copyright (c) 2014 Harsh Panchal <panchal.harsh18@gmail.com>.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 ******************************************************************************/
package com.harsh.dualboot;

import static com.harsh.dualboot.Constants.PRIMARY_DATA;
import static com.harsh.dualboot.Constants.SECONDARY_CACHE;
import static com.harsh.dualboot.Constants.SECONDARY_DATA;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SecondaryTweaks extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.advance);
		Button but_wipe = (Button) findViewById(R.id.but_wipe);
		Button but_share = (Button) findViewById(R.id.but_share);
		Button but_share_ret = (Button) findViewById(R.id.but_share_rev);
		but_wipe.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				handlerRequest();
			}
		});
		but_share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				warnUser();
			}
		});
		but_share_ret.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mRevertShare();
			}
		});
	}
	
	private void handlerRequest() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.warn);
		builder.setTitle(R.string.warn_title);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	new SU(SecondaryTweaks.this).execute("rm -r " + SECONDARY_DATA);
            	new SU(SecondaryTweaks.this).execute("rm -r " + SECONDARY_CACHE);
            	return;
            }
        });
		builder.setNegativeButton(R.string.cancel, null);
		builder.create().show();
	}
	
	private void warnUser() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.share_dialog);
		builder.setTitle(R.string.cleanup_warn_title);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	new SU(SecondaryTweaks.this).execute("rm -r " + SECONDARY_DATA + "/app");
            	new SU(SecondaryTweaks.this).execute("ln -s " + PRIMARY_DATA + "/app " + SECONDARY_DATA + "/app");
            	return;
            }
        });
		builder.setNegativeButton(R.string.cancel, null);
		builder.create().show();
	}
	
	private void mRevertShare() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.share_rev_dialog);
		builder.setTitle(R.string.cleanup_warn_title);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	new SU(SecondaryTweaks.this).execute("rm -r " + SECONDARY_DATA + "/app");
            	new SU(SecondaryTweaks.this).execute("mkdir " + SECONDARY_DATA + "/app");
            	new SU(SecondaryTweaks.this).execute("chmod 775 " + SECONDARY_DATA + "/app");
            	return;
            }
        });
		builder.setNegativeButton(R.string.cancel, null);
		builder.create().show();
	}
}
