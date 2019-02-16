package com.fanok.mdpu24;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.fanok.mdpu24.activity.ChatActivity;
import com.fanok.mdpu24.activity.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class ChatService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getData().get("title");
            int id = 100;
            Intent intent = new Intent(this, MainActivity.class);
            switch (title) {
                case "Новое сообщение":
                    id = 0;
                    intent = new Intent(this, ChatActivity.class);
                    intent.putExtra("group", remoteMessage.getData().get("group"));
                    SharedPreferences preferences = getSharedPreferences(StartActivity.PREF_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("activity", 1);
                    editor.apply();
                    break;
                case "Новый пользователь":
                    id = 1;
                    break;
            }
            sendNotification(remoteMessage.getNotification().getBody(), id, title, intent);
        }
    }

    @Override
    public void onNewToken(String token) {
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        /*SharedPreferences preferences = getSharedPreferences(StartActivity.PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", token);
        editor.apply();*/
    }

    private void sendNotification(String body, int id, String title, Intent intent) {

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "Chat")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_action_action_search)
                //.setNumber(count)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (notificationManager != null) {
            notificationManager.notify(id, notificationBuilder.build());
            Thread t = new Thread(() -> {
                Intent brIntent = new Intent(ChatActivity.ACTION);
                sendBroadcast(brIntent);
            });
            t.start();
        }

    }
}
