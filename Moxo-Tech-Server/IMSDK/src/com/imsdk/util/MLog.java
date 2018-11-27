package com.imsdk.util;

public class MLog {
	public static void i(boolean debug, String TAG, String content) {
		if (debug) {
			if (content == null || content.trim().equals("")) {
				System.out.println();
			} else {
				System.out.println(String.format("[%s] %s", TAG, content));
			}
		}
	}

	public static void d(boolean debug, String TAG, String content) {
		if (debug) {
			if (content == null || content.trim().equals("")) {
				System.out.println();
			} else {
				System.out.println(String.format(
						"============[%s]  %s============", TAG, content));
			}
		}
	}
}
