package com.unity3d.player;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static androidx.recyclerview.widget.RecyclerView.*;
import static com.unity3d.player.sa_TabFragment_Alarm.alarmListAdapter;

public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> itemList;
    private ArrayList<String> Alarm_id_List;
    private HashMap<String, String> Alarm_Time_HM;
    private HashMap<String, String> Alarm_weekofday_HM;
    private HashMap<String, String> Alarm_gameType_HM;
    private HashMap<String, String> Alarm_IsSuper_HM;

    public AlarmListAdapter(Context mContext, ArrayList<String> itemList, ArrayList<String> alarm_id_List, HashMap<String, String> alarm_Time_HM, HashMap<String, String> alarm_weekofday_HM, HashMap<String, String> alarm_gameType_HM, HashMap<String, String> alarm_IsSuper_HM) {
        this.mContext = mContext;
        this.itemList = itemList;
        this.Alarm_id_List = alarm_id_List;
        this.Alarm_Time_HM = alarm_Time_HM;
        this.Alarm_weekofday_HM = alarm_weekofday_HM;
        this.Alarm_gameType_HM = alarm_gameType_HM;
        this.Alarm_IsSuper_HM = alarm_IsSuper_HM;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_alarm, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = itemList.get(position);
        holder.item_layout.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("알람 삭제");
                builder.setMessage("선택하신 알람을 삭제하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                itemList.remove(position);
                                int selected_P_id= Integer.parseInt(Alarm_id_List.get(position));
                                LoadingActivity.mAlarmDbOpenHelper.deleteColumn(selected_P_id);
                                alarmListAdapter.notifyDataSetChanged();
                                //Toast.makeText(getApplicationContext(),"예를 선택했습니다.",Toast.LENGTH_LONG).show();
                            }
                        });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(getApplicationContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                            }
                        });
                builder.show();
                return false;
            }
        });

        //알람 제목
        holder.alarm_title1.setText(item);
        //알람 시간 && 알람 남은 시간
        String alarm_time= Alarm_Time_HM.get(Alarm_id_List.get(position));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try{
            Date temp_date = sdf.parse(alarm_time);
            SimpleDateFormat newsdf = new SimpleDateFormat("hh:mm aa", new Locale("en", "US"));
            holder.alarm_time_text.setText(newsdf.format(temp_date));
            Log.d(String.valueOf(this), "TIME: "+newsdf.format(temp_date));

            /*
            * 남은 시간 계산시 앞으로의 알람 일정에서 빼는 방식으로 수정할 필요가 있음*/
            Date currentTime = Calendar.getInstance().getTime();
            long subt= temp_date.getTime()-currentTime.getTime();
            long diffSeconds = subt / 1000 % 60;
            long diffMinutes = subt / (60 * 1000) % 60;
            long diffHours = subt / (60 * 60 * 1000) % 24;
            long diffDays = subt / (24 * 60 * 60 * 1000);
            Log.d(String.valueOf(this), "SubTime: "+diffDays+"일 "+diffHours+"시간 "+diffMinutes+"분 후");

        }catch (Exception e){

        }

    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView alarm_title1;
        public TextView alarm_time_text;
        public LinearLayout item_layout;

        public ViewHolder(View itemView) {
            super(itemView);

            item_layout= itemView.findViewById(R.id.item_layout);
            alarm_title1= itemView.findViewById(R.id.alarm_title1);
            alarm_time_text = itemView.findViewById(R.id.alarm_time1);
        }
    }

    private String getTimeWithoutDate(String alarm_time){
        String temp2="";
        String temp="";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date setdate = sdf.parse(alarm_time);
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            temp2 = sdf2.format(setdate);
            Log.d(String.valueOf(this), "temp2?: "+temp2);
            temp = temp2.split(" ")[1];
        }catch (ParseException e){

        }catch (NullPointerException e){

        }

        return temp;
    }
}
