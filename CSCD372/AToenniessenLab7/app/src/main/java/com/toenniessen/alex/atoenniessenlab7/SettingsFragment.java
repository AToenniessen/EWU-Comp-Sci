package com.toenniessen.alex.atoenniessenlab7;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {


    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
//        onSharedPreferenceChanged(getPreferenceScreen().getSharedPreferences(), getResources().getStringArray(R.array.clock_types)[0]); //crashes because of getpreferencescreen
//        onSharedPreferenceChanged(getPreferenceScreen().getSharedPreferences(), getResources().getStringArray(R.array.clock_types)[1]);
//        onSharedPreferenceChanged(getPreferenceScreen().getSharedPreferences(), getResources().getStringArray(R.array.clock_types)[2]);
//        onSharedPreferenceChanged(getPreferenceScreen().getSharedPreferences(), getResources().getStringArray(R.array.clock_types)[3]);

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
        Preference preference = findPreference(key);

        preference.setSummary(key);
    }
}
