package ru.gb.course1.l10_messages;

import static com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private static final int NOTIFICATION_ID = 123;
    private static final String CHANNEL_ID = "2 channel for test notif";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String message = "Я тост, демонстрация всплывающего сообщения";

        createNotificationChannels();

        findViewById(R.id.toast_button).setOnClickListener(v -> {
            Toast toast = Toast.makeText(this, R.string.app_name, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 100, 100);
            View view = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
            toast.setView(view);
            toast.show();
        });
        findViewById(R.id.snack_button).setOnClickListener(v -> {
            final Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_INDEFINITE);
            snackbar.setTextColor(Color.BLUE);
            snackbar.setActionTextColor(Color.RED);
            snackbar.setBackgroundTint(Color.CYAN);
            snackbar.setAnimationMode(ANIMATION_MODE_SLIDE);
            snackbar.setAction("Закрыть", snackView -> {
                Toast.makeText(this, "Закрыли снэк", Toast.LENGTH_SHORT).show();
                snackbar.dismiss();
            });
            snackbar.show();
        });
        findViewById(R.id.dialog_button).setOnClickListener(v -> {
            new AlertDialogFragment().show(getSupportFragmentManager(), "");
        });
        findViewById(R.id.notification_button).setOnClickListener(v -> {
            final Intent intent = new Intent(this, MainActivity.class);
            final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            final Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Заголовок")
                    .setContentText(message)
                    .setColorized(true)
                    .setColor(Color.RED)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_baseline_beach_access_24)
                    .build();
            NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, notification);
        });
    }

    private void createNotificationChannels() {
        final NotificationChannelCompat notificationChannel = new NotificationChannelCompat.Builder(
                CHANNEL_ID,
                NotificationManagerCompat.IMPORTANCE_MAX)
                .setDescription("OLOLO")
                .setName("Глупые сообщения")
                .build();
        NotificationManagerCompat.from(this).createNotificationChannel(notificationChannel);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Не жми \"назад\"", Toast.LENGTH_SHORT).show();
    }
}