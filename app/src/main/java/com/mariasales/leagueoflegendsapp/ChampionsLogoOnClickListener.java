package com.mariasales.leagueoflegendsapp;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.applidium.shutterbug.utils.ShutterbugManager;

/**
 * Created by ricardosilva on 07/08/15.
 */
public class ChampionsLogoOnClickListener implements View.OnClickListener {

    private String champion;
    private String requestUrl;

    public ChampionsLogoOnClickListener(String champ) {
        requestUrl = "http://www.lusobit.com/lolcounter/images/champion/"+champ+".png";
    }

    @Override
    public void onClick(final View v) {
        // TODO: replace null with something
        ShutterbugManager.getSharedImageManager(null).download(requestUrl, new ShutterbugManager.ShutterbugManagerListener() {
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
}
