package com.github.anrimian.musicplayer.data.database;

import android.content.Context;

import androidx.room.Room;

/**
 * Created on 18.11.2017.
 */

public class DatabaseManager {

    private static final String DATABASE_NAME = "music_player_database";

    private final Context context;

    public DatabaseManager(Context context) {
        this.context = context;
    }

    public AppDatabase getAppDatabase() {
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                .addMigrations(Migrations.getMigration1_2(context),
                        Migrations.MIGRATION_2_3,
                        Migrations.getMigration3_4(context),
                        Migrations.MIGRATION_4_5,
                        Migrations.MIGRATION_5_6,
                        Migrations.MIGRATION_6_7,
                        Migrations.MIGRATION_7_8,
                        Migrations.MIGRATION_8_9,
                        Migrations.MIGRATION_9_10,
                        Migrations.MIGRATION_10_11,
                        Migrations.MIGRATION_11_12,
                        Migrations.MIGRATION_12_13)
                .build();
    }
}
