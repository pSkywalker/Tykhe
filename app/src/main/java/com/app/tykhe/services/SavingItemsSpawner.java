package com.app.tykhe.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.app.tykhe.R;
import com.app.tykhe.localStorage.Repo;
import com.app.tykhe.localStorage.entities.Reminder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class SavingItemsSpawner extends Service {

    private static SavingItemsSpawner instance;

    public static SavingItemsSpawner getInstance() {
        return instance;
    }

    private Repo repo;

    private Reminder reminder;
    public void setReminder( Reminder reminder ){
        this.reminder = reminder;
    }


    public SavingItemsSpawner() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            startMyOwnForeground();
        else{
            startForeground(1, new Notification());
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground()
    {
        this.repo = new Repo( getApplication() );
    }

    private Timer timer;
    private TimerTask timerTask;
    private int counter = 0;
    public void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void run() {

                    Log.i("Count", "=========  " + (counter++));

                    Calendar currentDate = Calendar.getInstance();
                    try {
                        //List<Reminder> reminder = repo.getReminder().getValue();
                        if (reminder.status) {
                            switch (reminder.chosenType) {
                                case "weekly":
                                    try {
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
                                        Date date = dateFormat.parse(reminder.time);

                                        Calendar eventCalendar = Calendar.getInstance();
                                        eventCalendar.set(Calendar.DAY_OF_WEEK, reminder.weeklyChoonenDay);
                                        eventCalendar.setTime(date);

                                        if (
                                                eventCalendar.get(Calendar.DAY_OF_WEEK) == currentDate.get(Calendar.DAY_OF_WEEK)
                                                        &&
                                                        eventCalendar.get(Calendar.HOUR) == currentDate.get(Calendar.HOUR)
                                                        &&
                                                        eventCalendar.get(Calendar.MINUTE) == currentDate.get(Calendar.MINUTE)
                                        ) {
                                            sendNotification();
                                        }

                                    } catch (Exception ex) {
                                    }

                                    break;
                                case "biweekly":
                                    try {
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
                                        Date date = dateFormat.parse(reminder.time);

                                        Calendar firstOccurrence = (Calendar) currentDate.clone();
                                        firstOccurrence.set(Calendar.DAY_OF_WEEK, reminder.biweeklyChosenDay);
                                        firstOccurrence.setTime(date);
                                        // Calculate the second occurrence by adding 7 days to the first occurrence
                                        Calendar secondOccurrence = (Calendar) firstOccurrence.clone();
                                        secondOccurrence.add(Calendar.DAY_OF_YEAR, 7);
                                        secondOccurrence.setTime(date);

                                        if (
                                                (
                                                        firstOccurrence.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH)
                                                                && firstOccurrence.get(Calendar.HOUR) == currentDate.get(Calendar.HOUR)
                                                                && firstOccurrence.get(Calendar.MINUTE) == currentDate.get(Calendar.MINUTE)
                                                )
                                                        ||
                                                        (
                                                                firstOccurrence.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH)
                                                                        && firstOccurrence.get(Calendar.HOUR) == currentDate.get(Calendar.HOUR)
                                                                        && firstOccurrence.get(Calendar.MINUTE) == currentDate.get(Calendar.MINUTE)
                                                        )
                                        ) {
                                            sendNotification();
                                        }


                                    } catch (Exception ex) {
                                    }
                                    break;
                                case "monthly":
                                    try {
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
                                        Date date = dateFormat.parse(reminder.time);

                                        Calendar eventCalendar = Calendar.getInstance();
                                        eventCalendar.set(Calendar.DAY_OF_MONTH, reminder.monthlyChosenDay);
                                        eventCalendar.setTime(date);

                                        if (
                                                eventCalendar.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH)
                                                        &&
                                                        eventCalendar.get(Calendar.HOUR) == currentDate.get(Calendar.HOUR)
                                                        &&
                                                        eventCalendar.get(Calendar.MINUTE) == currentDate.get(Calendar.MINUTE)
                                        ) {
                                            sendNotification();
                                        }
                                    } catch (Exception ex) {
                                    }
                                    break;
                            }
                        }
                    }catch( NullPointerException ex ) {Log.d("asdf", "reminder not found");}

            }
            };

        timer.schedule(timerTask, 1000, 1000); //
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, Restarter.class);
        this.sendBroadcast(broadcastIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendNotification(){
        String NOTIFICATION_CHANNEL_ID = "com.tykhe.permanence";
        String channelName = "Tykhe";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        //chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID);

        Notification notification = notificationBuilder.setOngoing(false)
                .setContentTitle(getApplication().getResources().getString(R.string.notification_title))
                .setSmallIcon(R.drawable.app_logo )
                .setContentText(getApplication().getResources().getString( R.string.notification_text ))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       return null;
    }
}