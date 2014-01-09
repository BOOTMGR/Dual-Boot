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
	public static final String PRIMARY_DATA = "/primary_data";
	public static final String SECONDARY = "/data/secondary_data";
	public static final String SECONDARY_DATA = "/data/secondary_data/data";
	public static final String SECONDARY_CACHE = "/data/secondary_data/cache";
	public static final String BOOT_PART = "/dev/block/mmcblk0p15";
	public static final int PRIMARY_ROM = 0;
	public static final int SECONDARY_ROM = 1;
	public static final char E = 'e';
	public static final char D = 'd';
}
