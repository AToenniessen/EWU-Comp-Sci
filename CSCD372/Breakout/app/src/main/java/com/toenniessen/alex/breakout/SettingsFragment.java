package com.toenniessen.alex.breakout;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {


    public SettingsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        SharedPreferences pref = getPreferenceManager().getSharedPreferences();
        onSharedPreferenceChanged(pref, "brick_count");
        onSharedPreferenceChanged(pref, "brick_hp");
        onSharedPreferenceChanged(pref, "ball_count");
        onSharedPreferenceChanged(pref, "paddle_sens");
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference pref = findPreference(key);
        String title = "";
        if(pref instanceof NumberPickerPreference){
            switch (key){
                case "brick_count":
                    title = getResources().getString(R.string.brick_preference) + " = " + ((NumberPickerPreference) pref).getValue();
                    break;
                case "brick_hp":
                    title = getResources().getString(R.string.brickHP_preference) + " = " + ((NumberPickerPreference) pref).getValue();
                    break;
                case "ball_count":
                    title = getResources().getString(R.string.ball_preference) + " = " + ((NumberPickerPreference) pref).getValue();
                    break;
                case "paddle_sens":
                    title = getResources().getString(R.string.paddle_preference) + " = " + ((NumberPickerPreference) pref).getValue();
                    break;
            }
            pref.setTitle(title);
        }
    }
}
