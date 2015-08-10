package com.mariasales.leagueoflegendsapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class LogoScreenActivity extends Activity {

    private ImageView logo;
    private RelativeLayout relLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_screen);

        logo = (ImageView) findViewById(R.id.league_logo_id);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogoScreenActivity.this, TeamViewerActivity.class);
                startActivity(intent);
            }
        });

        relLayout = (RelativeLayout) findViewById(R.id.background_layout);
        relLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogoScreenActivity.this, TeamViewerActivity.class);
                startActivity(intent);
            }
        });
    }
}
