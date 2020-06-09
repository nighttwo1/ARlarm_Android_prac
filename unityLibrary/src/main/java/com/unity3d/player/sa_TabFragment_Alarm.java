package com.unity3d.player;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;


public class sa_TabFragment_Alarm extends Fragment {
    int cnt=0;

    private Button mNewAlarmBtn;
    private RecyclerView alarm_recyclerView;

    public static AlarmListAdapter alarmListAdapter;
    private ArrayList<String> Alarm_id_List;
    private ArrayList<String> itemList;
    private HashMap<String, String> Alarm_Time_HM;
    private HashMap<String, String> Alarm_weekofday_HM;
    private HashMap<String, String> Alarm_gameType_HM;
    private HashMap<String, String> Alarm_IsSuper_HM;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sa_tab_fragment_alarm, null);

        alarm_recyclerView = view.findViewById(R.id.alarm_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        alarm_recyclerView.setLayoutManager(layoutManager);

        Alarm_id_List = new ArrayList<>();
        itemList = new ArrayList<>();
        Alarm_Time_HM = new HashMap<>();
        Alarm_weekofday_HM = new HashMap<>();
        Alarm_gameType_HM = new HashMap<>();
        Alarm_IsSuper_HM = new HashMap<>();

        alarmListAdapter = new AlarmListAdapter(getContext(), itemList, Alarm_id_List, Alarm_Time_HM, Alarm_weekofday_HM, Alarm_gameType_HM, Alarm_IsSuper_HM);
        alarmListAdapter.notifyDataSetChanged();
        alarm_recyclerView.setAdapter(alarmListAdapter);

        //AlarmListDeco alarmListDeco = new AlarmListDeco();
        //alarm_recyclerView.addItemDecoration(alarmListDeco);
        Cursor cursor = LoadingActivity.mAlarmDbOpenHelper.selectColumns();
        while(cursor.moveToNext()){
            String Alarm_Pid= cursor.getString(cursor.getColumnIndexOrThrow("P_id"));
            String Alarm_Time= cursor.getString(cursor.getColumnIndexOrThrow("P_alarmTime"));
            String Alarm_weekofday= cursor.getString(cursor.getColumnIndexOrThrow("P_weekofday"));
            String Alarm_gameType= cursor.getString(cursor.getColumnIndexOrThrow("P_gameType"));
            String Alarm_IsSuper= cursor.getString(cursor.getColumnIndexOrThrow("P_IsSuper"));
            String Alarm_IsActive= cursor.getString(cursor.getColumnIndexOrThrow("P_IsActive"));
            String Alarm_ActivateNum= cursor.getString(cursor.getColumnIndexOrThrow("P_ActivateNum"));
            String Alarm_Content= cursor.getString(cursor.getColumnIndexOrThrow("P_Content"));

            Alarm_id_List.add(Alarm_Pid);
            Alarm_Time_HM.put(Alarm_Pid, Alarm_Time);
            Alarm_weekofday_HM.put(Alarm_Pid, Alarm_weekofday);
            Alarm_gameType_HM.put(Alarm_Pid, Alarm_gameType);
            Alarm_IsSuper_HM.put(Alarm_Pid, Alarm_IsSuper);

            itemList.add(Alarm_Content);
        }
        alarmListAdapter.notifyDataSetChanged();

        mNewAlarmBtn = view.findViewById(R.id.newalarm_button);
        mNewAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AlarmSettingActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        alarmListAdapter.notifyDataSetChanged();
    }
}
