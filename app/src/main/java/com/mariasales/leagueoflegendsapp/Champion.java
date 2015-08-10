package com.mariasales.leagueoflegendsapp;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ricardosilva on 08/08/15.
 */
public class Champion implements Parcelable {

    private String name;
    private Bitmap icon;
    private String roles;
    private int compatibility;

    public Champion(String name, Bitmap icon, String roles, int compatibility) {
        this.name = name;
        this.icon = icon;
        this.roles = roles;
        this.compatibility = compatibility;
    }

    protected Champion(Parcel in) {
        name = in.readString();
        icon = in.readParcelable(Bitmap.class.getClassLoader());
        roles = in.readString();
        compatibility = in.readInt();
    }

    public static final Creator<Champion> CREATOR = new Creator<Champion>() {
        @Override
        public Champion createFromParcel(Parcel in) {
            return new Champion(in);
        }

        @Override
        public Champion[] newArray(int size) {
            return new Champion[size];
        }
    };

    public String getName() {
        return this.name;
    }

    public Bitmap getIcon() {
        return this.icon;
    }

    public String getRoles() { return this.roles; }

    public int getCompatibility() { return this.compatibility; }

    public void setIcon(Bitmap newIcon) {
        this.icon = newIcon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(icon, flags);
        dest.writeString(roles);
        dest.writeInt(compatibility);
    }
}
