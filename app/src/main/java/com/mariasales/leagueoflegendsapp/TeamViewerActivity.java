package com.mariasales.leagueoflegendsapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.applidium.shutterbug.utils.ShutterbugManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TeamViewerActivity extends Activity {

    private static final int CHAMPION_SEARCH_REQUEST_CODE_ALLY_1 = 111;
    private static final int CHAMPION_SEARCH_REQUEST_CODE_ALLY_2 = 112;
    private static final int CHAMPION_SEARCH_REQUEST_CODE_ALLY_3 = 113;
    private static final int CHAMPION_SEARCH_REQUEST_CODE_ALLY_4 = 114;
    private static final int CHAMPION_SEARCH_REQUEST_CODE_ALLY_5 = 115;
    private static final int CHAMPION_SEARCH_REQUEST_CODE_ENEMY_1 = 121;
    private static final int CHAMPION_SEARCH_REQUEST_CODE_ENEMY_2 = 122;
    private static final int CHAMPION_SEARCH_REQUEST_CODE_ENEMY_3 = 123;
    private static final int CHAMPION_SEARCH_REQUEST_CODE_ENEMY_4 = 124;
    private static final int CHAMPION_SEARCH_REQUEST_CODE_ENEMY_5 = 125;

    private ImageView ally1, ally2, ally3, ally4, ally5, enemy1, enemy2, enemy3, enemy4, enemy5;
    private Button resetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_viewer);

        // Set Ally_Team and Enemy_Team

        ally1 = (ImageView) findViewById(R.id.ally_1);
        ally2 = (ImageView) findViewById(R.id.ally_2);
        ally3 = (ImageView) findViewById(R.id.ally_3);
        ally4 = (ImageView) findViewById(R.id.ally_4);
        ally5 = (ImageView) findViewById(R.id.ally_5);

        enemy1 = (ImageView) findViewById(R.id.enemy_1);
        enemy2 = (ImageView) findViewById(R.id.enemy_2);
        enemy3 = (ImageView) findViewById(R.id.enemy_3);
        enemy4 = (ImageView) findViewById(R.id.enemy_4);
        enemy5 = (ImageView) findViewById(R.id.enemy_5);

        ally1.setOnClickListener(new ChampionPositionOnClickListener(CHAMPION_SEARCH_REQUEST_CODE_ALLY_1));
        ally2.setOnClickListener(new ChampionPositionOnClickListener(CHAMPION_SEARCH_REQUEST_CODE_ALLY_2));
        ally3.setOnClickListener(new ChampionPositionOnClickListener(CHAMPION_SEARCH_REQUEST_CODE_ALLY_3));
        ally4.setOnClickListener(new ChampionPositionOnClickListener(CHAMPION_SEARCH_REQUEST_CODE_ALLY_4));
        ally5.setOnClickListener(new ChampionPositionOnClickListener(CHAMPION_SEARCH_REQUEST_CODE_ALLY_5));
        enemy1.setOnClickListener(new ChampionPositionOnClickListener(CHAMPION_SEARCH_REQUEST_CODE_ENEMY_1));
        enemy2.setOnClickListener(new ChampionPositionOnClickListener(CHAMPION_SEARCH_REQUEST_CODE_ENEMY_2));
        enemy3.setOnClickListener(new ChampionPositionOnClickListener(CHAMPION_SEARCH_REQUEST_CODE_ENEMY_3));
        enemy4.setOnClickListener(new ChampionPositionOnClickListener(CHAMPION_SEARCH_REQUEST_CODE_ENEMY_4));
        enemy5.setOnClickListener(new ChampionPositionOnClickListener(CHAMPION_SEARCH_REQUEST_CODE_ENEMY_5));


        // Set Reset Button

        resetBtn = (Button) findViewById(R.id.reset_button);

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ally1.setImageResource(R.drawable.addicon);
                ally2.setImageResource(R.drawable.addicon);
                ally3.setImageResource(R.drawable.addicon);
                ally4.setImageResource(R.drawable.addicon);
                ally5.setImageResource(R.drawable.addicon);
                enemy1.setImageResource(R.drawable.addicon);
                enemy2.setImageResource(R.drawable.addicon);
                enemy3.setImageResource(R.drawable.addicon);
                enemy4.setImageResource(R.drawable.addicon);
                enemy5.setImageResource(R.drawable.addicon);
            }
        });

    }

    protected void onActivityResult( int requestCode, int resultCode, Intent pData) {
        if(resultCode == Activity.RESULT_OK) {
            Champion champion = pData.getExtras().getParcelable("Champion");
            switch (requestCode) {
                case CHAMPION_SEARCH_REQUEST_CODE_ALLY_1:
                    ally1.setImageBitmap(champion.getIcon());
                    break;
                case CHAMPION_SEARCH_REQUEST_CODE_ALLY_2:
                    ally2.setImageBitmap(champion.getIcon());
                    break;
                case CHAMPION_SEARCH_REQUEST_CODE_ALLY_3:
                    ally3.setImageBitmap(champion.getIcon());
                    break;
                case CHAMPION_SEARCH_REQUEST_CODE_ALLY_4:
                    ally4.setImageBitmap(champion.getIcon());
                    break;
                case CHAMPION_SEARCH_REQUEST_CODE_ALLY_5:
                    ally5.setImageBitmap(champion.getIcon());
                    break;
                case CHAMPION_SEARCH_REQUEST_CODE_ENEMY_1:
                    enemy1.setImageBitmap(champion.getIcon());
                    break;
                case CHAMPION_SEARCH_REQUEST_CODE_ENEMY_2:
                    enemy2.setImageBitmap(champion.getIcon());
                    break;
                case CHAMPION_SEARCH_REQUEST_CODE_ENEMY_3:
                    enemy3.setImageBitmap(champion.getIcon());
                    break;
                case CHAMPION_SEARCH_REQUEST_CODE_ENEMY_4:
                    enemy4.setImageBitmap(champion.getIcon());
                    break;
                case CHAMPION_SEARCH_REQUEST_CODE_ENEMY_5:
                    enemy5.setImageBitmap(champion.getIcon());
                    break;
            }
        }
    }

    private class ChampionPositionOnClickListener implements View.OnClickListener {

        private int requestCode;

        public ChampionPositionOnClickListener(int code) {
            requestCode = code;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(TeamViewerActivity.this, ChampionSearchActivity.class);
            startActivityForResult(intent, requestCode);
        }
    }
/*

        View.OnClickListener onClickListener2 = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String url = "http://www.lusobit.com/lolcounter/images/champion/aatrox.png";

                ShutterbugManager.getSharedImageManager(TeamViewerActivity.this).download(url, new ShutterbugManager.ShutterbugManagerListener() {
                    @Override
                    public void onImageSuccess(ShutterbugManager shutterbugManager, Bitmap bitmap, String s) {
                        ImageView imageView = (ImageView) v;
                        imageView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onImageFailure(ShutterbugManager shutterbugManager, String s) {

                    }
                });
            }
        };

        ally1.setOnClickListener(onClickListener2);

    ChampionsLogoOnClickListener championsLogoOnClickListener = new ChampionsLogoOnClickListener("aatrox");

*/
}
