package com.am2.eshopapp.ui.home;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.am2.eshopapp.R;
import com.am2.eshopapp.databinding.FragmentProductCreateBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProductCreateFragment extends Fragment {

    private FragmentProductCreateBinding binding;
    private FirebaseFirestore db;

    public ProductCreateFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProductCreateBinding.inflate(inflater, container, false);
        Button jbtnCancelProductCreate = binding.btnCancelProductCreate;
        Button jbtnProductCreate = binding.btnProductCreate;
        EditText jetProductName = binding.etProductName;
        EditText jetProductPrice = binding.etProductPrice;
        EditText jetProductDescription = binding.etProductDescription;
        EditText jetProductStock = binding.etProductStock;
        db= FirebaseFirestore.getInstance();

        jbtnCancelProductCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.nav_home);
            }
        });

        jbtnProductCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, description, stock, price;
                name = jetProductName.getText().toString();
                description = jetProductDescription.getText().toString();
                stock = jetProductStock.getText().toString();
                price = jetProductPrice.getText().toString();

                // Validar si hay campos vacios
                if (
                        !name.isEmpty() &&
                                !description.isEmpty() &&
                                !stock.isEmpty() &&
                                !price.isEmpty()
                ){
                    saveProductToFirestore();
                    findNavController(view).navigate(R.id.nav_home);
                }else {
                    Toast.makeText(getContext(), "Llene todos los campos.", Toast.LENGTH_LONG).show();
                }

            }
        });
        return binding.getRoot();
    }

    public void saveProductToFirestore(){
        EditText jetProductName = binding.etProductName;
        EditText jetProductPrice = binding.etProductPrice;
        EditText jetProductDescription = binding.etProductDescription;
        EditText jetProductStock = binding.etProductStock;

        Map<String, Object> product = new HashMap<>();
        String productName = jetProductName.getText().toString();
        String productPrice = jetProductPrice.getText().toString();
        String productDescription = jetProductDescription.getText().toString();
        String productStock = jetProductStock.getText().toString();

        product.put("name", productName);
        product.put("price", productPrice);
        product.put("description", productDescription);
        product.put("stock", productStock);
        db.collection("products")
                .add(product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(),
                                "Operation complete!!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),
                                "Upps, product create fail...", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}