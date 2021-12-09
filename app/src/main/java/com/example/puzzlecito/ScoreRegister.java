package com.example.puzzlecito;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.TimeZone;

public class ScoreRegister extends AppCompatActivity {

    long minutes = 0;
    long seconds = 0;

        public static String score;
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_score_register);

            TextView myname = findViewById(R.id.editTextTextPersonName);
            TextView myscore = findViewById(R.id.myscore);
            minutes = (MainActivity.elapsedTime ) / 60;
            seconds = (MainActivity.elapsedTime ) % 60;
            String time_score = minutes + ":" + seconds;
            myscore.setText(time_score);

            BBDDHelper dbHelper = new BBDDHelper(this);


            Button buttonAccept = findViewById(R.id.confirm);

            buttonAccept.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (v.getId() == R.id.confirm) {


                        score = myscore.getText().toString();
                        String name = myname.getText().toString();

                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        ContentValues valores = new ContentValues();
                        valores.put(BBDDSchema.NAME, name);
                        valores.put(String.valueOf(BBDDSchema.TIME), MainActivity.elapsedTime);

                        //Insert in database
                        db.insert(BBDDSchema.TABLE, null, valores);

                        Intent intent_score = new Intent(getApplicationContext(), Score.class);
                        intent_score.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent_score.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent_score);

                        addEvent();
                        recoveryScore();
                    }
                }
            });

        }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void notification(String title, String message, Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = createID();
        String channelId = "channel-id";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.r2dedos)//R.mipmap.ic_launcher
                .setContentTitle(title)
                .setContentText(message)
                .setVibrate(new long[]{100, 250})
                .setLights(Color.YELLOW, 500, 5000)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, R.color.white));

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(new Intent(context, Score.class));
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());
    }

    public int createID() {
        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.FRENCH).format(now));
        return id;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    void recoveryScore() {

        Context context = this.getApplicationContext();
        ContentResolver contentResolver = context.getContentResolver();

        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2021, Calendar.JANUARY, 01);
        long startMills = beginTime.getTimeInMillis();
        long endMills = System.currentTimeMillis();


        ContentUris.appendId(builder, startMills);
        ContentUris.appendId(builder, endMills);

        String[] args = new String[]{"3"};

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR}, 3);
        }

        Cursor eventCursor = contentResolver.query(builder.build(), new String[]{CalendarContract.Instances.TITLE,
                        CalendarContract.Instances.BEGIN, CalendarContract.Instances.END, CalendarContract.Instances.DESCRIPTION},
                CalendarContract.Instances.CALENDAR_ID + " = ?", args, null);


        boolean isRecord = true;

        while (eventCursor.moveToNext()) {
            final String title = eventCursor.getString(0);
            int description = Integer.valueOf(eventCursor.getString(3));

            if (title.compareTo("Puzzledroid")==0) {

                if (((minutes * 60) + seconds) > description) {
                    isRecord = false;
                }
            }
        }

        if (isRecord) {
            notification("Puzzledroid", "You broke the record!", this);
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent_main = new Intent(getApplicationContext(), MainActivity.class);
        intent_main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent_main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent_main);

    }



    public void addEvent(){
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.CALENDAR_ID, 3);
        values.put(CalendarContract.Events.DTSTART, System.currentTimeMillis());
        values.put(CalendarContract.Events.DTEND, System.currentTimeMillis());
        values.put(CalendarContract.Events.TITLE, "Puzzledroid");
        values.put(CalendarContract.Events.DESCRIPTION, String.valueOf((minutes*60)+seconds));
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
        long eventID = Long.parseLong(uri.getLastPathSegment());

        //notification("titulo", "mensaje", this);
    }





    }