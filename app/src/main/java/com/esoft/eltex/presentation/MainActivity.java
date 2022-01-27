package com.esoft.eltex.presentation;


import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.widget.Toast;

import com.esoft.eltex.R;
import com.esoft.eltex.app.ErrorMessageEvent;
import com.esoft.eltex.data.PreferenceDataSource;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private NavHostFragment navHostFragment;

    private PreferenceDataSource preferenceDataSource;

    private ErrorMessageEvent event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        preferenceDataSource = new PreferenceDataSource(this);
        event = ErrorMessageEvent.getInstance();
        onEvent(event);

        String token = preferenceDataSource.getPrefString("token");
        if (token != null) {
            if (navController.getCurrentDestination().getId() == R.id.loginFragment) {
                navController.navigate(R.id.action_loginFragment_to_detailInfoFragment);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ErrorMessageEvent event) {
        if (event.message != null) {
            switch (event.message) {
                case "200":
                    Toast.makeText(this, "Успешная аутентификация", Toast.LENGTH_SHORT).show();
                    if (navController.getCurrentDestination().getId() == R.id.loginFragment) {
                        navController.navigate(R.id.action_loginFragment_to_detailInfoFragment);
                    }
                    break;
                case "401":
                    Toast.makeText(this, "Ошибка аутентификации", Toast.LENGTH_SHORT).show();
                    preferenceDataSource.clearPref();
                    navController.navigate(R.id.action_loginFragment_to_detailInfoFragment);
                    break;
                case "404":
                    Toast.makeText(this, "Данные не найдены", Toast.LENGTH_SHORT).show();
                    break;
                case "400":
                    Toast.makeText(this, "Ошибка запроса", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}