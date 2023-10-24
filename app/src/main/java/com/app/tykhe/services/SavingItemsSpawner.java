package com.app.tykhe.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleService;

import com.app.tykhe.HomeActivity;
import com.app.tykhe.R;
import com.app.tykhe.localStorage.Repo;
import com.app.tykhe.localStorage.entities.Reminder;
import com.app.tykhe.localStorage.entities.SavingItem;
import com.app.tykhe.localStorage.entities.User;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class SavingItemsSpawner extends LifecycleService {

    private static SavingItemsSpawner instance;

    public static SavingItemsSpawner getInstance() {
        return instance;
    }

    private Repo repo;

    private Reminder reminder;
    private User user;
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
        Log.d("asdf", "asdf");
        this.repo = new Repo( getApplication() );

        repo.getReminder().observe(this, reminder -> {
            if( !reminder.isEmpty() ) {
                this.reminder = reminder.get(0);
            }
        });
        repo.getUser().observe( this, user -> {
            if (!user.isEmpty() ) {
                this.user = user.get(0);
            }
        });
    }

    private Timer timer;
    private TimerTask timerTask;
    private int counter = 0;
    public void startTimer() {
        timer = new Timer();
        this.repo = new Repo( getApplication() );
        repo.getReminder().observe(this, reminder -> {
            if( !reminder.isEmpty() ) {
                this.reminder = reminder.get(0);
            }
        });
        repo.getUser().observe( this, user -> {
            if( !user.isEmpty()) {
                this.user = user.get(0);
            }
        });
        timerTask = new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void run() {
                Log.i("Count", "=========  " + (counter++) + " " + String.valueOf( reminder ) );
                if (reminder != null && user != null) {


                    Calendar currentDate = Calendar.getInstance();

                    Log.d("asdf",  String.valueOf(currentDate.get(Calendar.DAY_OF_MONTH)));

                    try {
                        //List<Reminder> reminder = repo.getReminder().getValue();
                        Log.d("asdf", reminder.chosenType);
                        if (reminder.status) {
                            switch (reminder.chosenType) {
                                case "weekly":
                                    try {
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
                                        Date date = dateFormat.parse(reminder.time);

                                        Calendar eventCalendar = Calendar.getInstance();
                                        eventCalendar.set(Calendar.DAY_OF_WEEK, reminder.weeklyChoonenDay);
                                        eventCalendar.set(Calendar.HOUR, date.getHours());
                                        eventCalendar.set(Calendar.MINUTE, date.getMinutes());
                                        //eventCalendar.setTime(date);
                                        //Log.d( "asdf",String.valueOf( eventCalendar.get(Calendar.DAY_OF_WEEK) ) + " " + String.valueOf( currentDate.get(Calendar.DAY_OF_WEEK) ) );

                                        if (
                                                eventCalendar.get(Calendar.DAY_OF_WEEK) == currentDate.get(Calendar.DAY_OF_WEEK)
                                                        &&
                                                        eventCalendar.get(Calendar.HOUR) == currentDate.get(Calendar.HOUR)
                                                        &&
                                                        eventCalendar.get(Calendar.MINUTE) == currentDate.get(Calendar.MINUTE)
                                        ) {
                                            if (currentDate.get(Calendar.SECOND) == 1) {
                                                sendNotification();
                                            } else {
                                                Log.d("asdf", "calendar already sent");
                                            }
                                        } else {
                                            Log.d("asdf", String.valueOf(eventCalendar.get(Calendar.DAY_OF_WEEK)) + " " + String.valueOf(currentDate.get(Calendar.DAY_OF_WEEK)));
                                        }

                                    } catch (Exception ex) {
                                    }

                                    break;
                                case "biweekly":
                                    try {
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
                                        Date date = dateFormat.parse(reminder.time);

                                        Calendar firstOccurrence = (Calendar) currentDate.clone();
                                        firstOccurrence.set(Calendar.DAY_OF_MONTH, reminder.biweeklyChosenDay);
                                        firstOccurrence.set(Calendar.HOUR, date.getHours());
                                        firstOccurrence.set(Calendar.MINUTE, date.getMinutes());
                                        // Calculate the second occurrence by adding 7 days to the first occurrence
                                        Calendar secondOccurrence = (Calendar) firstOccurrence.clone();

                                        secondOccurrence.set(Calendar.DAY_OF_MONTH, reminder.biweeklyChosenDay + 14);

                                        //secondOccurrence.setTime(date);
                                        secondOccurrence.set(Calendar.HOUR, date.getHours());
                                        secondOccurrence.set(Calendar.MINUTE, date.getMinutes());
                                        if (
                                                (
                                                        firstOccurrence.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH)
                                                                && firstOccurrence.get(Calendar.HOUR) == currentDate.get(Calendar.HOUR)
                                                                && firstOccurrence.get(Calendar.MINUTE) == currentDate.get(Calendar.MINUTE)
                                                )
                                                        ||
                                                        //currentDate.get(Calendar.DAY_OF_MONTH) < reminder.biweeklyChosenDay + 14
                                                        // &&
                                                        (
                                                                secondOccurrence.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH)
                                                                        && secondOccurrence.get(Calendar.HOUR) == currentDate.get(Calendar.HOUR)
                                                                        && secondOccurrence.get(Calendar.MINUTE) == currentDate.get(Calendar.MINUTE)
                                                        )
                                        ) {
                                            if (currentDate.get(Calendar.SECOND) == 1) {
                                                sendNotification();
                                            } else {
                                                Log.d("asdf", "calendar already sent");
                                            }
                                        } else {
                                            Log.d("asdf", String.valueOf(reminder.biweeklyChosenDay) + "" + String.valueOf(secondOccurrence.get(Calendar.DAY_OF_MONTH)) + " " + String.valueOf(currentDate.get(Calendar.DAY_OF_MONTH)));
                                        }


                                    } catch (Exception ex) {
                                    }
                                    break;
                                case "monthly":
                                    try {
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
                                        Date date = dateFormat.parse(reminder.time);

                                        Calendar eventCalendar = Calendar.getInstance();
                                        eventCalendar.set(Calendar.DAY_OF_MONTH, reminder.monthlyChosenDay );
                                        eventCalendar.set(Calendar.HOUR, date.getHours());
                                        eventCalendar.set(Calendar.MINUTE, date.getMinutes());

                                        if (
                                                eventCalendar.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH)
                                                        &&
                                                        eventCalendar.get(Calendar.HOUR) == currentDate.get(Calendar.HOUR)
                                                        &&
                                                        eventCalendar.get(Calendar.MINUTE) == currentDate.get(Calendar.MINUTE)
                                        ) {
                                            if (currentDate.get(Calendar.SECOND) == 1) {
                                                sendNotification();
                                            } else {
                                                Log.d("asdf", "calendar already sent");
                                            }
                                        } else {
                                            Log.d("asdf", String.valueOf(eventCalendar.get(Calendar.DAY_OF_MONTH)) + " " + String.valueOf(currentDate.get(Calendar.DAY_OF_MONTH)));
                                        }
                                    } catch (Exception ex) {
                                    }
                                    break;
                                case "Monthly":
                                    try {
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
                                        Date date = dateFormat.parse(reminder.time);

                                        Calendar eventCalendar = Calendar.getInstance();
                                        eventCalendar.set(Calendar.DAY_OF_MONTH, reminder.monthlyChosenDay );
                                        eventCalendar.set(Calendar.HOUR, date.getHours());
                                        eventCalendar.set(Calendar.MINUTE, date.getMinutes());

                                        if (
                                                eventCalendar.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH)
                                                        &&
                                                        eventCalendar.get(Calendar.HOUR) == currentDate.get(Calendar.HOUR)
                                                        &&
                                                        eventCalendar.get(Calendar.MINUTE) == currentDate.get(Calendar.MINUTE)
                                        ) {
                                            if (currentDate.get(Calendar.SECOND) == 1) {
                                                sendNotification();
                                            } else {
                                                Log.d("asdf", "calendar already sent");
                                            }
                                        } else {
                                            Log.d("asdf", String.valueOf(eventCalendar.get(Calendar.DAY_OF_MONTH)) + " " + String.valueOf(currentDate.get(Calendar.DAY_OF_MONTH)));
                                        }
                                    } catch (Exception ex) {
                                    }
                                    break;
                            }
                        }
                    } catch (NullPointerException ex) {
                        Log.d("asdf", "reminder not found");
                    }

                }
            }

            ;
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
        String NOTIFICATION_CHANNEL_ID = "com.tykhe.notification";
        String channelName = "Tykhe";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        //chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        Intent intent = new Intent(this, HomeActivity.class); // Replace with the target activity
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID).setOngoing(false)
                .setContentTitle(getApplication().getResources().getString(R.string.notification_title))
                .setSmallIcon(R.drawable.applogo )
                .setContentText(getApplication().getResources().getString( R.string.notification_text ))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setAutoCancel(true)
                .setContentIntent( pendingIntent );

        /*
        Notification notification = notificationBuilder.setOngoing(false)
                .setContentTitle(getApplication().getResources().getString(R.string.notification_title))
                .setSmallIcon(R.drawable.app_logo )
                .setContentText(getApplication().getResources().getString( R.string.notification_text ))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setAutoCancel(true)
                ;
         */
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// Display the notification
        notificationManager.notify(1, notificationBuilder.build());
        //startForeground(1 , notificationBuilder.build());

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        SavingItem savingItem = new SavingItem();
        savingItem.SavingItemStatus = 1;
        savingItem.SavingItemAmounts = user.contributionAmount;
        savingItem.SavingItmeDate = currentDate.format(formatter);
        repo.createSavingItem( savingItem );


    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       return null;
    }
}