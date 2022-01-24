package com.esoft.eltex.presentation.fragments.loginFragment;

import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.esoft.eltex.R;


public class LoginFragment extends Fragment{

    private Button loginInBtn;
    private TextView loginText, passwordText;
    private NavController navController;
    private LoginViewModel viewModel;

    private static final String BASIC = "Basic ";
    private static final String ACP = "android-client:password";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(), new LoginViewModelFactory(requireActivity().getApplication())).get(LoginViewModel.class);
        viewModel.getCode();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        loginInBtn = view.findViewById(R.id.loginInBtn);
        loginText = view.findViewById(R.id.loginText);
        passwordText = view.findViewById(R.id.passwordText);

        onClick();
        checkCode();
    }

    private void checkCode() {
        viewModel.codeLiveData.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer) {
                    case 200:
                        Toast.makeText(requireActivity(), "Успешная авторизация", Toast.LENGTH_LONG).show();
                        navController.navigate(R.id.action_loginFragment_to_detailInfoFragment);
                        break;
                    case 404:
                        Toast.makeText(requireActivity(), "Пользователь не авторизован", Toast.LENGTH_LONG).show();
                        break;
                    case 401:
                    case 400:
                        Toast.makeText(requireActivity(), "Ошибка", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void onClick() {
        loginInBtn.setOnClickListener(view -> {
            try {
                String base = BASIC + Base64.encodeToString((ACP).getBytes("UTF-8"), Base64.NO_WRAP);
                String grantType = getString(R.string.grant_type);
                String username = loginText.getText().toString();
                String password = passwordText.getText().toString();
                viewModel.loginIn(base, grantType, username, password);
            }catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}