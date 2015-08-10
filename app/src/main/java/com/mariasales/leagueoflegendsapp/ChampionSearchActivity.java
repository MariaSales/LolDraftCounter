package com.mariasales.leagueoflegendsapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.applidium.shutterbug.utils.ShutterbugManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChampionSearchActivity extends Activity {

    private ListView championsListView;
    private List<Champion> championsList = new ArrayList<Champion>();
    private ArrayAdapter<Champion> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champion_search);

        Log.e("ERROR", "1");

        populateChampionsList(); //TODO Get all champions before show list
        //populateTeste();
        Log.e("ERROR", "3");
        populateChampionsListView();
        Log.e("ERROR", "5");
        registerClickCallBack();
    }

    private void populateTeste() {

        Log.e("ERROR", "2");
        Bitmap icon = BitmapFactory.decodeResource(getBaseContext().getResources(),
                R.drawable.irelia);

        championsList.add(new Champion("AAtrox", icon, "Top, Jungler", 15));
        championsList.add(new Champion("AAtrox", icon, "Top, Jungler", 15));
        championsList.add(new Champion("AAtrox", icon, "Top, Jungler", 15));
        championsList.add(new Champion("AAtrox", icon, "Top, Jungler", 15));
        championsList.add(new Champion("AAtrox", icon, "Top, Jungler", 15));
        championsList.add(new Champion("AAtrox", icon, "Top, Jungler", 15));
        championsList.add(new Champion("AAtrox", icon, "Top, Jungler", 15));
        championsList.add(new Champion("AAtrox", icon, "Top, Jungler", 15));
    }

    private void populateChampionsList() {

        final String[] championsNames;
        String url = "http://www.lusobit.com/lolcounter/images/champion/";

        championsNames = new String[]
                {"aatrox", "ahri", "akali", "alistar", "amumu", "anivia",
                        "annie", "ashe", "azir", "blitzcrank", "brand", "braum"};

        String auxURL;

        // Get champions images
        for (int i = 0; i < championsNames.length; i++) {

            auxURL = url + championsNames[i] + ".png";

            final int finalI = i;

            ShutterbugManager.getSharedImageManager(ChampionSearchActivity.this).download(auxURL, new ShutterbugManager.ShutterbugManagerListener() {
                @Override
                public void onImageSuccess(ShutterbugManager shutterbugManager, Bitmap bitmap, String s) {
                    championsList.add(new Champion(championsNames[finalI], bitmap, "Top, Jungler", 15));
                }

                @Override
                public void onImageFailure(ShutterbugManager shutterbugManager, String s) {
                    // TODO
                }
            });
        }
    }

    private void populateChampionsListView() {
        Log.e("ERROR", "4");
        adapter = new ChampionsArrayAdapter();
        championsListView = (ListView) findViewById(R.id.championsListView);
        championsListView.setAdapter(adapter);
    }

    private void registerClickCallBack() {

        Log.e("ERROR", "6");
        ListView list = (ListView) findViewById(R.id.championsListView);
        Log.e("ERROR", "7");

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Log.e("ERROR", "8");
                Champion clickedChampion = championsList.get(position);
                Log.e("ERROR", "9");
                Intent intent = new Intent(ChampionSearchActivity.this, TeamViewerActivity.class);
                Log.e("ERROR", "10");
                intent.putExtra("Champion", clickedChampion);
                Log.e("ERROR", "11");

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private class ChampionsArrayAdapter extends ArrayAdapter<Champion> implements Filterable {

        public ChampionsArrayAdapter() {
            super(ChampionSearchActivity.this, R.layout.champion_list_item_view, championsList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View itemView = convertView;

            // Make sure we have a given view to work with (may have been given null)
            if( itemView == null)
                itemView = getLayoutInflater().inflate(R.layout.champion_list_item_view, parent, false);

            // Find the champion to work with
            Champion currentChampion = championsList.get(position);

            // Fill the view
            ImageView championIcon = (ImageView) itemView.findViewById(R.id.champion_icon);
            championIcon.setImageBitmap(currentChampion.getIcon());

            // Champion Name
            TextView championName = (TextView) itemView.findViewById(R.id.champion_name);
            championName.setText(currentChampion.getName());

            // Champion Roles
            TextView championRoles = (TextView) itemView.findViewById(R.id.champion_roles);
            championRoles.setText(currentChampion.getRoles());

            // Champion Compatibilty
            TextView championCompatibility = (TextView) itemView.findViewById(R.id.champion_compatibility);
            championCompatibility.setText(currentChampion.getCompatibility() + " %");

            return itemView;

        }
    }
}
