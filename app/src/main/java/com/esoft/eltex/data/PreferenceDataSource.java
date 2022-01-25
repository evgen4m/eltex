package com.esoft.eltex.data;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceDataSource {

    private static final String STORAGE_NAME = "LoginInData";

    private static SharedPreferences settings = null;
    private static SharedPreferences.Editor editor = null;
    private Context context;

    public PreferenceDataSource(Context context){
        this.context = context;
    }

    private void init(){
        settings = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
    }

    public void addPrefString(String name, String value) {
        if( settings == null ){
            init();
        }
        editor.putString(name, value);
        editor.commit();
    }

    public String getPrefString(String name) {
        if( settings == null ){
            init();
        }
        return settings.getString(name, null);
    }

    public void clearPref() {
        if(settings == null) {
            init();
        }
        editor.clear();
        editor.commit();
    }


}
