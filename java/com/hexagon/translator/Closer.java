package com.hexagon.translator;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import androidx.core.app.NotificationCompat;

import androidx.core.content.ContextCompat;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import com.hexagon.translator.R;

public class Closer extends Service {
    public NotificationManager notificationManager;
    public NotificationCompat.Builder notify;
    public Notification notification;
    public static Closer instance;
    public static final String STOP="stop";
    public static String height55;
    Timer timer=new Timer();
    String action="";
    View mFloatingView;
    WindowManager.LayoutParams params=null;
    WindowManager mWindowManager;
    private int initialX;
    private int initialY;
    private float initialTouchX;
    private float initialTouchY;

    public Closer(){
}
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    if(intent != null){
         action = intent.getAction();
        if(action==Closer.STOP){

            stopSelf();

        }else{
        }
    }

        return Service.START_STICKY;
    }
    @Override
    public void onCreate() {
        super.onCreate();


       mFloatingView = LayoutInflater.from(this).inflate(R.layout.slider, null);

        //Add the view to the window.
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE ,
                    PixelFormat.TRANSLUCENT);
        }else {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE ,
                    PixelFormat.TRANSLUCENT);
        }
        //Specify the view position
        params.gravity = Gravity.TOP | Gravity.LEFT;        //Initially view will be added to top-left corner
        params.x = 0;
        params.y = 200;

        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);

ImageView image=mFloatingView.findViewById(R.id.image);
        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //remember the initial position.
                        initialX = params.x;
                        initialY = params.y;

                        //get the touch location
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        int Xdiff = (int) (event.getRawX() - initialTouchX);
                        int Ydiff = (int) (event.getRawY() - initialTouchY);
                        //The check for Xdiff <10 && YDiff< 10 because sometime elements moves a little while clicking.
                        //So that is click event.
                        if (Xdiff < 10 && Ydiff < 10) {
                            Intent intent=new Intent(Closer.this,Heads.class);
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                                startForegroundService(intent);
                            }else{
                                startService(intent);
                            }
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        //Update the layout with new X & Y coordinate
                        mWindowManager.updateViewLayout(mFloatingView, params);
                        return true;
                }
                return false;
            }
        });
        startNotify();

    }
    public static Closer getinstance(){
  return instance;
    }
    public void stop(){
    stopSelf();
    }
    protected void startwork(Context context){

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest saveRequest =
                new PeriodicWorkRequest.Builder(Coolworker.class, 16, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build();

        WorkManager.getInstance(context).enqueueUniquePeriodicWork("Mywork", ExistingPeriodicWorkPolicy.KEEP,saveRequest);
    }
    public void startNotify(){

        Intent intent1=new Intent(Closer.this, Closer.class);
intent1.setAction(Closer.STOP);
        PendingIntent pendingIntent1=PendingIntent.getService(Closer.this,2,intent1,0);

        notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        notify= new NotificationCompat.Builder(getApplicationContext(),"notify")
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("Hexagon Translator is Running")
                .setContentText("Hexagon Translator is Drawing Over Other Apps..")
                .setOngoing(true)
                .setColor(ContextCompat.getColor(Closer.this,R.color.colorAccent))
                .setVibrate(null)
                .setSound(null)
                .addAction(0,"STOP",pendingIntent1)
                .setPriority(Notification.PRIORITY_MAX);
        notify.build().flags=Notification.FLAG_NO_CLEAR|Notification.PRIORITY_MAX|Notification.FLAG_ONGOING_EVENT;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelid="Hexagon Translator";
            NotificationChannel channel=new NotificationChannel(channelid,"Hexagon Translator", NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(false);
            channel.setSound(null,null);
            notificationManager.createNotificationChannel(channel);
            notify.setChannelId(channelid);
        }
        notification=notify.build();

        startForeground(1000,notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(action != Closer.STOP){
          Intent intent=new Intent(Closer.this,Closer.class);
          intent.setAction("start");
          if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
           startForegroundService(intent);
          }else{
              startService(intent);
          }
        }
        if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
    }
}
