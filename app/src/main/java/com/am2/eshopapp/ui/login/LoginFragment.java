package com.am2.eshopapp.ui.login;

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
import androidx.navigation.Navigation;

import com.am2.eshopapp.Entities.SharedPreferenceEntities;
import com.am2.eshopapp.Entities.Usuario;
import com.am2.eshopapp.R;
import com.am2.eshopapp.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private FragmentLoginBinding binding;
    private FirebaseUser user;
    private ArrayList<Usuario> arrayList;
    private FirebaseFirestore db;
    private Usuario usuario;

    private FragmentTransaction transaction;
    private Fragment fragmentHome, fragmentLogin,  fragmentRegister;

    private FirebaseAuth mAuth;

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
        db = FirebaseFirestore.getInstance();

        jtvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.fragmentRegister);
            }
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        arrayList = new ArrayList<>();


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
                        // Validar password
                        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*])(?=\\S+$).{8,}$";
                        Pattern p2 = Pattern.compile(passwordPattern);
                        Matcher m2 = p2.matcher(password);
                        boolean b2 = m2.matches();
                        if (b2){
                            mAuth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
//                                                user = mAuth.getCurrentUser();
                                                // Sign in success, update UI with the signed-in user's information
//                                                Log.d("userEmail", "signInWithEmail:success");
//                                                FirebaseUser user = mAuth.getCurrentUser();
                                                consultaUser(view);
//                                                updateUI(user);
//                                                findNavController(view).navigate(R.id.nav_home);
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
                        }else{
                            Toast.makeText(getContext(), "Contraseña devil.", Toast.LENGTH_LONG).show();
                        }
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

    public void consultaUser(View view){
        String email = binding.etEmail.getText().toString();
        db.collection("Users").whereEqualTo("email", email)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                        usuario = documentSnapshot.toObject(Usuario.class);
                        break;
                    }
                    SharedPreferenceEntities.setEmail(usuario.getEmail());
                    SharedPreferenceEntities.setName(usuario.getName());
//                    SharedPreferenceEntities.setRol(usuario.getRol());
                    SharedPreferenceEntities.guardarPreferecia();
                    findNavController(view).navigate(R.id.nav_home);
                }else {
                    Toast.makeText(getContext(), "SharedPreference_Paila", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}