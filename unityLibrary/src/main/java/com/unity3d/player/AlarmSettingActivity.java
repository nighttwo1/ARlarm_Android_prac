package com.unity3d.player;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.unity3d.player.Database.AlarmDbOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import ca.antonious.materialdaypicker.MaterialDayPicker;
import lib.kingja.switchbutton.SwitchMultiButton;

public class AlarmSettingActivity extends AppCompatActivity {

    private TimePicker mTimePicker;
    private TextView tv_selectedDate;
    private ImageButton calendar_btn;
    private MaterialDayPicker materialDayPicker;
    private SwitchMultiButton select_game_btn;
    private Button addAlarmButton;
    private MaterialButton cancle_btn;

    private MaterialButtonToggleGroup toggleGroup_super;
    private MaterialButton super1_btn;
    private MaterialButton super2_btn;
    private MaterialButton super3_btn;
    private MaterialButton super4_btn;

    private String selected_hour;
    private String selected_minute;
    private String selected_Date;

    private String selected_weekofDay;
    private int selected_game;
    private HashMap<String, Boolean> selected_super_alarm;
    private int isSuper_alramOn;

    public static String Alarm_Time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setting);

        layoutsetting();
        selected_super_alarm= new HashMap<>();


        //timepicker_autoSize();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            selected_hour = mTimePicker.getHour() + "";
            selected_minute = mTimePicker.getMinute() + "";
        } else {
            selected_hour = mTimePicker.getCurrentHour() + "";
            selected_minute = mTimePicker.getCurrentMinute() + "";
        }

        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    selected_hour = timePicker.getHour() + "";
                    selected_minute = timePicker.getMinute() + "";
                } else {
                    selected_hour = timePicker.getCurrentHour() + "";
                    selected_minute = timePicker.getCurrentMinute() + "";
                }
            }
        });

        getCurrentDate();

        calendar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year1= Calendar.getInstance().get(Calendar.YEAR);
                int month1= Calendar.getInstance().get(Calendar.MONTH);
                int day1 = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                int weekday1 = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

                DatePickerDialog dialog = new DatePickerDialog(AlarmSettingActivity.this, datepickerlistener, year1, month1, day1);
                //dialog.getDatePicker().getChildAt(0).setVisibility(View.GONE);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dialog.show();

            }
        });

        //요일 선택
        /*
        * 일월화수목금토: 1234567
        * */
        materialDayPicker.setDaySelectionChangedListener(new MaterialDayPicker.DaySelectionChangedListener() {
            @Override
            public void onDaySelectionChanged(List<MaterialDayPicker.Weekday> list) {
                selected_weekofDay="";
                if(list.contains(MaterialDayPicker.Weekday.SUNDAY)){
                    selected_weekofDay+="1";
                }
                if(list.contains(MaterialDayPicker.Weekday.MONDAY)){
                    selected_weekofDay+="2";
                }
                if(list.contains(MaterialDayPicker.Weekday.TUESDAY)){
                    selected_weekofDay+="3";
                }
                if(list.contains(MaterialDayPicker.Weekday.WEDNESDAY)){
                    selected_weekofDay+="4";
                }
                if(list.contains(MaterialDayPicker.Weekday.THURSDAY)){
                    selected_weekofDay+="5";
                }
                if(list.contains(MaterialDayPicker.Weekday.FRIDAY)){
                    selected_weekofDay+="6";
                }
                if(list.contains(MaterialDayPicker.Weekday.SATURDAY)){
                    selected_weekofDay+="7";
                }
            }
        });

        //게임 선택
        /*
        * 먼먼이: 0
        * 테마1: 1
        * 테마2: 2*/
        select_game_btn.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                Toast.makeText(getApplicationContext(), "게임: "+position, Toast.LENGTH_SHORT).show();
                selected_game= position;
            }
        });

        //무적 알람 선택
        toggleGroup_super.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if(isChecked) {

                    if(checkedId==super1_btn.getId()){
                        selected_super_alarm.put("1", true);
                        super1_btn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.ARlarm_blue)));
                        super1_btn.setTextColor(Color.WHITE);
                    }else if(checkedId==super2_btn.getId()){
                        selected_super_alarm.put("2", true);
                        super2_btn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.ARlarm_blue)));
                        super2_btn.setTextColor(Color.WHITE);
                    }else if(checkedId==super3_btn.getId()){
                        selected_super_alarm.put("3", true);
                        super3_btn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.ARlarm_blue)));
                        super3_btn.setTextColor(Color.WHITE);
                    }else if(checkedId== super4_btn.getId()){
                        selected_super_alarm.put("4", true);
                        super4_btn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.ARlarm_blue)));
                        super4_btn.setTextColor(Color.WHITE);
                    }

                }else{

                    if(checkedId==super1_btn.getId()){
                        selected_super_alarm.put("1", false);
                        super1_btn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
                        super1_btn.setTextColor(getResources().getColor(R.color.ARlarm_blue));
                    }else if(checkedId==super2_btn.getId()){
                        selected_super_alarm.put("2", false);
                        super2_btn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
                        super2_btn.setTextColor(getResources().getColor(R.color.ARlarm_blue));
                    }else if(checkedId==super3_btn.getId()){
                        selected_super_alarm.put("3", false);
                        super3_btn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
                        super3_btn.setTextColor(getResources().getColor(R.color.ARlarm_blue));
                    }else if(checkedId== super4_btn.getId()){
                        selected_super_alarm.put("4", false);
                        super4_btn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.transparent)));
                        super4_btn.setTextColor(getResources().getColor(R.color.ARlarm_blue));
                    }
                }
            }
        });

        addAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alarm_Time = selected_Date+" "+selected_hour+":"+selected_minute;

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                try {
                    Date setdate = sdf.parse(Alarm_Time);
                    Date currentTime = Calendar.getInstance().getTime();
                    if(setdate.compareTo(currentTime)>0){
                        Toast.makeText(getApplicationContext(), "딱임", Toast.LENGTH_SHORT).show();
                        check_current_selection();
                        AlarmDbOpenHelper.insertColumn(Alarm_Time, selected_weekofDay, selected_game, isSuper_alramOn, 1, 0, "sample_a1");

                        Intent intent2 = new Intent(getApplicationContext(), AlarmMainActivity.class);
                        intent2.putExtra("alarm_addition", "complete");
                        startActivity(intent2);

                    }else{
                        Toast.makeText(getApplicationContext(), "알람 설정시간은 현재보다 이전일 수 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(), "Time?: "+Alarm_Time, Toast.LENGTH_SHORT).show();


            }
        });
        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void layoutsetting(){
        mTimePicker = findViewById(R.id.timepicker);

        tv_selectedDate = findViewById(R.id.tv_selectedDate);

        calendar_btn = findViewById(R.id.calendar_btn);

        materialDayPicker = findViewById(R.id.day_picker);
        materialDayPicker.setLocale(Locale.ENGLISH);

        select_game_btn = findViewById(R.id.select_game_btn);

        toggleGroup_super = findViewById(R.id.toggleGroup_super);
        super1_btn = findViewById(R.id.super1_btn);
        super2_btn = findViewById(R.id.super2_btn);
        super3_btn = findViewById(R.id.super3_btn);
        super4_btn = findViewById(R.id.super4_btn);

        addAlarmButton = findViewById(R.id.button2);
        cancle_btn = findViewById(R.id.cancle_btn);
    }

    private void timepicker_autoSize(){
        /*
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        ViewGroup.LayoutParams layoutParams = timepicker.getLayoutParams();
        layoutParams.width = width;
        timepicker.setLayoutParams(layoutParams);
        */
        mTimePicker.setScaleX(1.5f);
    }

    private DatePickerDialog.OnDateSetListener datepickerlistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            Toast.makeText(getApplicationContext(), year + "년" + (month+1) + "월" + dayOfMonth +"일", Toast.LENGTH_SHORT).show();
            SimpleDateFormat simpledateformat = new SimpleDateFormat("EE");
            Date date = new Date(year, month, dayOfMonth-1);
            String dayOfWeek = simpledateformat.format(date).substring(0,1);

            tv_selectedDate.setText((month+1)+"월 "+dayOfMonth+"일 ("+dayOfWeek+")");
            selected_Date = year+"-"+(month+1)+"-"+dayOfMonth;
        }
    };
    private void getCurrentDate(){
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat weekdayFormat = new SimpleDateFormat("EE", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

        String current_weekDay = weekdayFormat.format(currentTime);
        String current_year = yearFormat.format(currentTime);
        String current_month = monthFormat.format(currentTime);
        String current_day = dayFormat.format(currentTime);

        tv_selectedDate.setText(current_month+"월 "+current_day+"일 ("+current_weekDay+")");
        selected_Date = current_year+"-"+current_month+"-"+current_day;
    }

    private void check_current_selection(){
        Log.d("current_selection_checking", "요일: "+selected_weekofDay);
        Log.d("current_selection_checking", "게임: "+selected_game);
        isSuper_alramOn = 0;
        for(String key:selected_super_alarm.keySet()){
            Log.d("current_selection_checking", "무적 "+key+": "+selected_super_alarm.get(key));
            isSuper_alramOn = 1;
        }
    }
}
