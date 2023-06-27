package com.example.nutrismart.ui.profile;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.nutrismart.R;


public class ProfileFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}