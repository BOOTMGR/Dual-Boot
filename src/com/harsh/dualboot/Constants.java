/*******************************************************************************
 * Copyright (c) 2014 Harsh Panchal <panchal.harsh18@gmail.com>.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 ******************************************************************************/
package com.harsh.dualboot;

import android.os.Environment;

public class Constants {
	public static final String SDCARD_ROOT = Environment.getExternalStorageDirectory().toString();
	public static final String CM102_DIR = SDCARD_ROOT + "/DualBoot/CM102";
	public static final String CM102 = SDCARD_ROOT + "/DualBoot/CM102/CM102_DB.md5";
	public static final String CM102_stock = SDCARD_ROOT + "/DualBoot/CM102/CM102_stock.md5";
	public static final String CM11_DIR = SDCARD_ROOT + "/DualBoot/CM11";
	public static final String CM11 = SDCARD_ROOT + "/DualBoot/CM11/CM11_DB.md5";
	public static final String CM11_stock = SDCARD_ROOT + "/DualBoot/CM11/CM11_stock.md5";
	public static final String STOCK_DIR = SDCARD_ROOT + "/DualBoot/Stock";
	public static final String STOCK = SDCARD_ROOT + "/DualBoot/Stock/stock.md5";
	public static final String MIUI_DIR = SDCARD_ROOT + "/DualBoot/MIUI";
	public static final String MIUI = SDCARD_ROOT + "/DualBoot/MIUI/MIUI_DB.md5";
	public static final String MIUI_Stock = SDCARD_ROOT + "/DualBoot/MIUI/MIUI_stock.md5";
	public static final String AOSP_DIR = SDCARD_ROOT + "/DualBoot/AOSP";
	public static final String AOSP = SDCARD_ROOT + "/DualBoot/AOSP/aosp_db.md5";
	public static final String AOSP_stock = SDCARD_ROOT + "/DualBoot/AOSP/aosp_stock.md5";
	public static final String UNKNOWN = SDCARD_ROOT + "/DualBoot/Unknown/unknown.md5";
	public static final String UNKNOWN_DIR = SDCARD_ROOT + "/DualBoot/Unknown";
	public static final String DLINK_CM102 = "http://fs1.d-h.st/download/00092/L6N/CM102_DB.md5";
	public static final String DLINK_CM102_STOCK = "http://fs1.d-h.st/download/00099/KHw/CM102_stock.md5";
	public static final String DLINK_CM11 = "http://fs1.d-h.st/download/00093/JxE/CM11_DB.md5";
	public static final String DLINK_CM11_STOCK = "http://fs1.d-h.st/download/00099/i6S/CM11_stock.md5";
	public static final String DLINK_MIUI = "http://fs1.d-h.st/download/00098/zHi/MIUI_DB.md5";
	public static final String DLINK_MIUI_STOCK = "http://fs1.d-h.st/download/00098/Zu6/MIUI_stock.md5";
	public static final String DLINK_AOSP = "http://fs1.d-h.st/download/00099/z6d/aosp_db.md5";
	public static final String DLINK_AOSP_STOCK = "http://fs1.d-h.st/download/00099/GfF/aosp_stock.md5";
	public static final String PRIMARY_DATA = "/primary_data";
	public static final String SECONDARY = "/data/secondary_data";
	public static final String SECONDARY_DATA = "/data/secondary_data/data";
	public static final String SECONDARY_CACHE = "/data/secondary_data/cache";
	public static final String BOOT_PART = "/dev/block/mmcblk0p15";
	public static final int PRIMARY_ROM = 0;
	public static final int SECONDARY_ROM = 1;
	public static final char E = 'e';
	public static final char D = 'd';
	public static final int current_version = 121;
}
