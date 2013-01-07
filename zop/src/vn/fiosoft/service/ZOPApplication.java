package vn.fiosoft.service;

import android.app.Application;
import android.content.Intent;

public class ZOPApplication extends Application {

	private static int mMaxResults = 3;

	private static Boolean isRuning = false;
	private Boolean isListening = false;
	public static ZOPApplication mInstance;

	@Override
	public void onCreate() {
		super.onCreate();

		mInstance = this;

	}

	public static Boolean getIsRuning() {
		return isRuning;
	}

	/**
	 * start application and service
	 */
	public static void start() {
		if (isRuning == true)
			return;

		isRuning = true;

		// start Service
		mInstance.startService(new Intent(mInstance, ZOPService.class));

	}

}
