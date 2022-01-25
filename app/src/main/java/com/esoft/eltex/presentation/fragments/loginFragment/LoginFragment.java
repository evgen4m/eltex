package com.esoft.eltex.presentation.fragments.loginFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.esoft.eltex.R;
import com.esoft.eltex.domain.TokenModel;


public class LoginFragment extends Fragment{

    private Button loginInBtn;
    private TextView loginText, passwordText;
    private NavController navController;
    private LoginViewModel viewModel;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(), new LoginViewModelFactory(requireActivity().getApplication())).get(LoginViewModel.class);
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

        viewModel.tokenModelMutableLiveData.observe(getViewLifecycleOwner(), new Observer<TokenModel>() {
            @Override
            public void onChanged(TokenModel tokenModel) {
                if (tokenModel.getToken() != null) {
                    NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_loginFragment_to_detailInfoFragment);
                }
            }
        });

        onClick();
    }

    private void onClick() {
        loginInBtn.setOnClickListener(view -> {
            try {
                String grantType = getString(R.string.grant_type);
                String username = loginText.getText().toString();
                String password = passwordText.getText().toString();
                viewModel.loginIn(grantType, username, password);
            }catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}