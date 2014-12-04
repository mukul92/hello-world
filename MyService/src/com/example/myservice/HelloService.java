package com.example.myservice;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class HelloService extends IntentService {

	public HelloService() {
		super("HelloIntentService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		long endTime = System.currentTimeMillis() + 5*1000;
	      while (System.currentTimeMillis() < endTime) {
	          synchronized (this) {
	              try {
	            	  
	            	  NotificationCompat.Builder mBuilder =
	            		        new NotificationCompat.Builder(this)
	            		        .setSmallIcon(R.drawable.ic_launcher)
	            		        .setContentTitle("My notification")
	            		        .setContentText("Hello World!");
	            	  
	            	  
	            	  Intent resultIntent = new Intent(this, MainActivity.class);

	            	// The stack builder object will contain an artificial back stack for the
	            	// started Activity.
	            	// This ensures that navigating backward from the Activity leads out of
	            	// your application to the Home screen.
	            	TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
	            	// Adds the back stack for the Intent (but not the Intent itself)
	            	stackBuilder.addParentStack(MainActivity.class);
	            	// Adds the Intent that starts the Activity to the top of the stack
	            	stackBuilder.addNextIntent(resultIntent);
	            	PendingIntent resultPendingIntent =
	            	        stackBuilder.getPendingIntent(
	            	            0,
	            	            PendingIntent.FLAG_UPDATE_CURRENT
	            	        );
	            	mBuilder.setContentIntent(resultPendingIntent);
	            	  
	            	  
	            	  
	            	  
	            	  
	            	  
	            	  manager.notify(1, mBuilder.build());
	            	  
	            	  
	            	  
	                  wait(endTime - System.currentTimeMillis());
	              } catch (Exception e) {
	              }
	          }
	      }
		
		
		
		
	}
	

}
