package com.am2.eshopapp.ui.login;

import static androidx.navigation.Navigation.findNavController;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.am2.eshopapp.R;
import com.am2.eshopapp.databinding.FragmentLoginBinding;
import com.am2.eshopapp.ui.register.RegisterFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private FragmentLoginBinding binding;

    private FragmentTransaction transaction;
    private Fragment fragmentHome, fragmentLogin,  fragmentRegister;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        TextView jtvSignUp = binding.tvSignUp;
        Button jbtnLogin = binding.btnLogin;
        EditText jetEmail = binding.etEmail;
        EditText jetPassword = binding.etPassword;

        jtvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.fragmentRegister);
            }
        });

        jbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = jetEmail.getText().toString();
                password = jetPassword.getText().toString();

                // Si hay campos vacios
                if (
                        !email.isEmpty() &&
                                !password.isEmpty()
                ) {
                    // si campo email tiene el formato correcto
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    Pattern p = Pattern.compile(emailPattern);
                    Matcher m = p.matcher(email);
                    boolean b = m.matches();
                    if(b){
                        findNavController(view).navigate(R.id.nav_home);
                    }else{
                        Toast.makeText(getContext(), "Email no coincide.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "hay un campo vacio.", Toast.LENGTH_LONG).show();
                }

            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}