package com.unity3d.player;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.unity3d.player.Database.AlarmDbOpenHelper;

public class LoadingActivity extends AppCompatActivity {

    public static AlarmDbOpenHelper mAlarmDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView1.setImageResource(R.drawable.intro);

        mAlarmDbOpenHelper = new AlarmDbOpenHelper(this);
        mAlarmDbOpenHelper.open();
        mAlarmDbOpenHelper.create();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            public void run() {
                Intent intent = new Intent(LoadingActivity.this, AlarmMainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000); //1.0s..?
    }
}
