package com.harsh.dualboot;

import android.os.Environment;

public class Constants {
	public static final String SDCARD_ROOT = Environment.getExternalStorageDirectory().toString();
	public static final String CM102_DIR = SDCARD_ROOT + "/DualBoot/CM102";
	public static final String CM102 = SDCARD_ROOT + "/DualBoot/CM102/CM102_DB.md5";
	public static final String CM11_DIR = SDCARD_ROOT + "/DualBoot/CM11";
	public static final String CM11 = SDCARD_ROOT + "/DualBoot/CM11/CM11_DB.md5";
	public static final String STOCK_DIR = SDCARD_ROOT + "/DualBoot/Stock";
	public static final String STOCK = SDCARD_ROOT + "/DualBoot/Stock/stock.md5";
	public static final String PRIMARY_DATA = "/primary_data";
	public static final String BOOT_PART = "/dev/block/mmcblk0p15";
	public static final int PRIMARY_ROM = 0;
	public static final int SECONDARY_ROM = 1;
	public static final char E = 'e';
	public static final char D = 'd';
}
