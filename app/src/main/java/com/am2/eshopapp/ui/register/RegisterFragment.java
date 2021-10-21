package com.am2.eshopapp.ui.register;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {

    private RegisterViewModel loginViewModel;
    private FragmentRegisterBinding binding;

    private FragmentTransaction transaction;
    private Fragment fragmentHome, fragmentLogin, fragmentRegister;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

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

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();


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
                        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*])(?=\\S+$).{8,}$";
                        Pattern p2 = Pattern.compile(passwordPattern);
                        Matcher m2 = p2.matcher(pass);
                        boolean b2 = m2.matches();
                        if (b2 && pass.length()>5) {
                            mAuth.createUserWithEmailAndPassword(email, pass)
                                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                // Sign in success, update UI with the signed-in user's information
                                                Log.d("userEmail", "signInWithEmail:success");
                                                FirebaseUser user = mAuth.getCurrentUser();
//                                                updateUI(user);
                                                saveUserToFirestore();
                                                findNavController(view).navigate(R.id.fragmentLogin);
                                            } else {
                                                // If sign in fails, display a message to the user.
                                                Log.w("userEmail", "signInWithEmail:failure", task.getException());
                                                Toast.makeText(getContext(), "Authentication failed.",
                                                        Toast.LENGTH_SHORT).show();
//                                                updateUI(null);
                                            }
                                        }
                                    });

//                            findNavController(view).navigate(R.id.nav_home);
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

    public void saveUserToFirestore(){
        EditText jetName = binding.etName;
        EditText jetEmail = binding.etEmail;
        EditText jetMobile = binding.etMobile;
        EditText jetPassword = binding.etPassword;

        Map<String, Object> user = new HashMap<>();
        String name = jetName.getText().toString();
        String email = jetEmail.getText().toString();
        String cel = jetMobile.getText().toString();
        String pw = jetPassword.getText().toString();
        user.put("name", name);
        user.put("email", email);
        user.put("cel", cel);
        user.put("password", pw);
        db.collection("Users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(),
                                "Registro completo", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),
                                "Sea serio", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}