package com.taz;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
    
        //Toast.makeText(context, context.getString(R.string.Alertnotifty) + intent.getStringExtra("title") , Toast.LENGTH_LONG).show();
        String Title = intent.getStringExtra(context.getString(R.string.tittle));
        String Detail = intent.getStringExtra(context.getString(R.string.detail));
        Intent x = new Intent(context, Alert.class);
        x.putExtra(context.getString(R.string.tittle), Title);
        x.putExtra(context.getString(R.string.detail), Detail);
        x.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(x);
    }
}

