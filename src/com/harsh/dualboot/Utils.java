/*******************************************************************************
 * Copyright (c) 2014 Harsh Panchal <panchal.harsh18@gmail.com>.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 ******************************************************************************/
package com.harsh.dualboot;

import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import static com.harsh.dualboot.Constants.*;

public class Utils {
	public static String SU_wop(String cmds, Context context) {
        String out = null;
        try {
            out = new String();
            Process p = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(p.getOutputStream());
            os.writeBytes(cmds+"\n");
            os.writeBytes("exit\n");
            os.flush();
            InputStream stdout = p.getInputStream();
            byte[] buffer = new byte[4096];
            int read;
            while (true) {
                read = stdout.read(buffer);
                out += new String(buffer, 0, read);
                if (read < 4096) {
                    break;
                }
            }
        } catch (Exception e) {
        	Toast.makeText(context, "Error getting SU rights", Toast.LENGTH_SHORT).show();
            LOG("Error getting SU rights", E);
        }
        return out.substring(0,out.length()-1);
    }
	
	public static void LOG (String msg,char type) {
		if(type == 'e')
			Log.e("harsh_debug", "DualBoot ---> " + msg);
		else
			Log.d("harsh_debug", "DualBoot ---> " + msg);
	}
	
	public static int getROM() {
		File f = new File(PRIMARY_DATA);
		if(f.exists())
			return SECONDARY_ROM;
		return PRIMARY_ROM;
	}
}
