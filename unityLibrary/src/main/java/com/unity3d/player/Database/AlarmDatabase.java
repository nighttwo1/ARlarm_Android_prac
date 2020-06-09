package com.unity3d.player.Database;

import android.provider.BaseColumns;

public class AlarmDatabase {
    public static class AlarmDB implements BaseColumns{
        public static final String TABLE_NAME = "Alarm_P";
        public static final String P_id="P_id";
        public static final String P_alarmTime="P_alarmTime";
        public static final String P_weekofday="P_weekofday";
        public static final String P_gameType="P_gameType";
        public static final String P_IsSuper="P_IsSuper";
        public static final String P_IsActive="P_IsActive";
        public static final String P_ActivateNum="P_ActivateNum";
        public static final String P_Content="P_Content";

        public static final String _CREATE_TABLE = "create table if not exists "+TABLE_NAME+"("
                +P_id+" integer primary key autoincrement, "
                +P_alarmTime+" text not null , "
                +P_weekofday+" text not null , "
                +P_gameType+" integer not null , "
                +P_IsSuper+" integer not null , "
                +P_IsActive+" integer not null, "
                +P_ActivateNum+" integer not null, "
                +P_Content+" text not null);";
    }
}
