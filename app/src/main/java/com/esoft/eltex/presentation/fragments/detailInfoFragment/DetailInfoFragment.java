package com.esoft.eltex.presentation.fragments.detailInfoFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.esoft.eltex.R;
import com.esoft.eltex.domain.UserModel;


public class DetailInfoFragment extends Fragment {

    private TextView userName, roleId, email;
    private RecyclerView recyclerView;

    private DetailViewModel viewModel;
    private DetailAdapter detailAdapter;

    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, new DetailViewModelFactory(getActivity().getApplication())).get(DetailViewModel.class);
        viewModel.getUserInfo();
        viewModel.checkCode();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

        userName = view.findViewById(R.id.usernameTextView);
        roleId = view.findViewById(R.id.roleIdTextView);
        email = view.findViewById(R.id.emailTextView);
        recyclerView = view.findViewById(R.id.permissionRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        detailAdapter = new DetailAdapter();

        viewModel.userModelMutableLiveData.observe(getViewLifecycleOwner(), new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                if (userModel != null) {
                    userName.setText(getString(R.string.username_format, userModel.getUsername()));
                    roleId.setText(getString(R.string.roleId_format, userModel.getRoleId()));
                    email.setText(getString(R.string.email_format, userModel.getEmail()));
                    detailAdapter.setAdapterList(userModel.getListPermissions());
                    recyclerView.setAdapter(detailAdapter);
                }
            }
        });

        checkCode();
    }

    private void checkCode() {
       viewModel.codeLiveData.observe(getViewLifecycleOwner(), new Observer<Integer>() {
           @Override
           public void onChanged(Integer integer) {
               if (integer == 401) {
                   Toast.makeText(requireContext(), "Пользователь не авторизован", Toast.LENGTH_LONG).show();
                   navController.navigateUp();
               }
           }
       });
    }
}