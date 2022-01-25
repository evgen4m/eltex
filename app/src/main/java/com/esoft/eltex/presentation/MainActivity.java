package com.esoft.eltex.presentation;


import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.esoft.eltex.R;
import com.esoft.eltex.data.PreferenceDataSource;


public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private NavHostFragment navHostFragment;
    private PreferenceDataSource preferenceDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        preferenceDataSource = new PreferenceDataSource(this);
        String token = preferenceDataSource.getPrefString("token");

        if (token != null) {
            navController.navigate(R.id.action_loginFragment_to_detailInfoFragment);
        }
    }

}