package com.example.projectointegrador.view.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.projectointegrador.R;
import com.example.projectointegrador.model.Track;
import com.example.projectointegrador.service.NotificationActionService;

public class CreateNotification {

    public static final String CHANNEL_ID = "channel1";
    public static final String ACTION_PREVIOUS = "actionprevious";
    public static final String ACTION_PLAY = "actionplay";
    public static final String ACTION_NEXT = "actionnext";
    public static Notification notification;

    public static void createNotification(Context context, Track track, int playButton, int position, int size){

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");

        PendingIntent pendingIntentPrevious;
        int drwPrevious;

        if(position == 0){
            pendingIntentPrevious = null;
            drwPrevious = 0;
        } else {
            Intent intentPrevious = new Intent(context, NotificationActionService.class)
                    .setAction(ACTION_PREVIOUS);
            pendingIntentPrevious = PendingIntent.getBroadcast(context, 0,
                    intentPrevious, PendingIntent.FLAG_UPDATE_CURRENT);
            drwPrevious = R.drawable.ic_skip_previous_black_24dp;
        }

        Intent intentPlay = new Intent(context, NotificationActionService.class)
                .setAction(ACTION_PLAY);
        PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context, 0,
                intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent pendingIntentNext;
        int drwNext;

        if(position == size){
            pendingIntentNext = null;
            drwNext = 0;
        } else {
            Intent intentNext = new Intent(context, NotificationActionService.class)
                    .setAction(ACTION_NEXT);
            pendingIntentNext = PendingIntent.getBroadcast(context, 0,
                    intentNext, PendingIntent.FLAG_UPDATE_CURRENT);
            drwNext = R.drawable.ic_skip_next_black_24dp;
        }

        Glide.with(context).asBitmap().load(track.getAlbum().getCover()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_music_note_black_24dp)
                        .setContentTitle(track.getTitle())
                        .setContentText(track.getArtist().getName())
                        .setLargeIcon(resource)
                        .setOnlyAlertOnce(true)
                        .setShowWhen(false)
                        .addAction(drwPrevious, "Previous", pendingIntentPrevious)
                        .addAction(playButton, "Play", pendingIntentPlay)
                        .addAction(drwNext, "Next", pendingIntentNext)
                        .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(0, 1, 2)
                            .setMediaSession(mediaSessionCompat.getSessionToken()))
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .build();

                notificationManagerCompat.notify(1, notification);
            }
        });

    }


}
