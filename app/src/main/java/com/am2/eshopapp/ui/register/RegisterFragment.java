package com.am2.eshopapp.ui.register;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.am2.eshopapp.R;
import com.am2.eshopapp.databinding.FragmentLoginBinding;
import com.am2.eshopapp.databinding.FragmentRegisterBinding;
import com.am2.eshopapp.ui.login.LoginFragment;
import com.am2.eshopapp.ui.login.LoginViewModel;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {

    private RegisterViewModel loginViewModel;
    private FragmentRegisterBinding binding;

    private FragmentTransaction transaction;
    private Fragment fragmentHome, fragmentLogin, fragmentRegister;


    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        TextView jtvSignIn = binding.tvSignIn;
        jtvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.fragmentLogin);
            }
        });
        EditText jetName = binding.etName;
        EditText jetEmail = binding.etEmail;
        EditText jetMobile = binding.etMobile;
        EditText jetPassword = binding.etPassword;
        Button jbtnRegister = binding.btnRegister;
        jbtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, email, mobile, pass;
                name = jetName.getText().toString();
                email = jetEmail.getText().toString();
                mobile = jetMobile.getText().toString();
                pass = jetPassword.getText().toString();
                // si algun campo esta vacio
                if (
                        !name.isEmpty() ||
                                !email.isEmpty() ||
                                !mobile.isEmpty() ||
                                !pass.isEmpty()
                ) {
                    // si campo email tiene el formato correcto
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    Pattern p = Pattern.compile(emailPattern);
                    Matcher m = p.matcher(email);
                    boolean b = m.matches();
                    if (b) {
                        String passwordPattern = "[a-zA-Z0-9_!@]+";
                        Pattern p2 = Pattern.compile(passwordPattern);
                        Matcher m2 = p2.matcher(pass);
                        boolean b2 = m2.matches();
                        if (b2 && pass.length()>5) {
                            findNavController(view).navigate(R.id.nav_home);
                        } else {
                            Toast.makeText(getContext(), "Email no coincide.", Toast.LENGTH_LONG).show();
                        }
                    } else {
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