package com.extect.appbase;

import android.app.Application;
import android.content.Context;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.Builder;

import utils.Utils;

/*import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;*/

public class BaseApplication extends Application {

	private static Picasso picasso;
	public static Context context;
	AppEnvironment appEnvironment;

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		appEnvironment = AppEnvironment.PRODUCTION;
		// Initialize the SDK before executing any other operations,
		/*FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(this);*/
		//Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
		/**
		 * initialize Intercom in Application space
		 */

		//context =

	}
	public static Picasso getPicasso(Context context) {
		if (picasso == null) {
			Builder picassoBuilder = new Builder(context);
			picassoBuilder.downloader(new Utils.UrlCustomDownloader(context));
			picasso = picassoBuilder.build();
			//picasso.setIndicatorsEnabled(true);
		}
		return picasso;
	}

	public AppEnvironment getAppEnvironment() {
		return appEnvironment;
	}

	public void setAppEnvironment(AppEnvironment appEnvironment) {
		this.appEnvironment = appEnvironment;
	}
}
