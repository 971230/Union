package com.ztesoft.net.model;

import java.util.Comparator;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class MapComparator implements Comparator<Map> {
	@Override
	public int compare(Map o1, Map o2) {
		int b1 = Integer.valueOf(o1.get("sortby") + "");
		int b2 = Integer.valueOf(o2.get("sortby") + "");
		if (b1 == 0) {
			b1 = 9999;
		}
		if (b2 == 0) {
			b2 = 9999;
		}
		return b1 - b2;
	}
}
