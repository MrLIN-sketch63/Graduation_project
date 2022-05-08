package chu.edu.tw.graduationproject.service;

import static android.content.Intent.ACTION_BOOT_COMPLETED;

import static chu.edu.tw.graduationproject.service.AlarmReceiver.setReminderAlarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.List;
import java.util.concurrent.Executors;

import chu.edu.tw.graduationproject.data.DatabaseHelper;
import chu.edu.tw.graduationproject.model.Alarm;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Executors.newSingleThreadExecutor().execute(() -> {
                final List<Alarm> alarms = DatabaseHelper.getInstance(context).getAlarms();
                setReminderAlarms(context, alarms);
            });
        }
    }

}