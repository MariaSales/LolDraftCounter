package com.mariasales.leagueoflegendsapp;

import android.app.Activity;
import android.content.Context;
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

import com.applidium.shutterbug.cache.ImageCache;
import com.applidium.shutterbug.utils.ShutterbugManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ChampionSearchActivity extends Activity {

    private ListView championsListView;
    private List<Champion> championsList = new ArrayList<Champion>();
    private ChampionsArrayAdapter adapter;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champion_search);

        populateChampionsList(); //TODO Get all champions before show list
        //populateTeste();
        populateChampionsListView();
        registerClickCallBack();
        setSearchBar();
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

        ListView list = (ListView) findViewById(R.id.championsListView);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Champion clickedChampion = championsList.get(position);
                Intent intent = new Intent(ChampionSearchActivity.this, TeamViewerActivity.class);
                intent.putExtra("Champion", clickedChampion);

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private void setSearchBar() {
        searchBar = (EditText) findViewById(R.id.search_bar);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = searchBar.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }
        });
    }

    private class ChampionsArrayAdapter extends ArrayAdapter<Champion> implements Filterable {

        Context context;
        LayoutInflater inflater;
        private List<Champion> filteredData = null;
        private ArrayList<Champion> arrayList;

        public ChampionsArrayAdapter() {
            super(ChampionSearchActivity.this, R.layout.champion_list_item_view, championsList);

            context = ChampionSearchActivity.this;
            inflater = LayoutInflater.from(context);
            filteredData = championsList;
            arrayList = new ArrayList<Champion>();
            arrayList.addAll(championsList);
        }

        public class ViewHolder {
            TextView championName;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final ViewHolder holder;
            View itemView = convertView;

            // Make sure we have a given view to work with (may have been given null)
            if( itemView == null) {
                holder = new ViewHolder();
                itemView = getLayoutInflater().inflate(R.layout.champion_list_item_view, parent, false);

                holder.championName = (TextView) findViewById(R.id.champion_name);
            }
            else
                holder = (ViewHolder) itemView.getTag();


            // Find the champion to work with
            Champion currentChampion = filteredData.get(position);

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

        public void filter (String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            filteredData.clear();
            if (charText.length() == 0) {
                filteredData.addAll(arrayList);
                notifyDataSetChanged();
            }
            else
            {
                for (Champion c : arrayList)
                {
                    if (c.getName().toLowerCase(Locale.getDefault()).contains(charText))
                    {
                        filteredData.add(c);
                    }
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return filteredData.size();
        }

        @Override
        public Champion getItem(int position) {
            return filteredData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }
}
