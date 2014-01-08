package com.harsh.dualboot;

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
		but_wipe.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				handlerRequest();
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
}
