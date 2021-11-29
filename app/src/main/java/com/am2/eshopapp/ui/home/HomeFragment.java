package com.am2.eshopapp.ui.home;

import static androidx.navigation.Navigation.findNavController;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.am2.eshopapp.Adapters.ProductAdapter;
import com.am2.eshopapp.Entities.ProductEntity;
import com.am2.eshopapp.Entities.SharedPreferenceEntities;
import com.am2.eshopapp.Entities.Usuario;
import com.am2.eshopapp.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ProductAdapter productAdapter;
    RecyclerView recyclerViewProduct;
    ArrayList<ProductEntity> listProduct;
    Button btnAddProduct, btnProductEdit;
    TextView tvProductId;
    private FirebaseFirestore db;
    private String role;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Log.d(getTag(), "*** Role *** => " + SharedPreferenceEntities.getRol());
        Log.d(getTag(), "*** Email *** => " + SharedPreferenceEntities.getEmail());
        role = SharedPreferenceEntities.getRol();
        btnAddProduct = view.findViewById(R.id.btnGoProductCreate);

        if ("vendedor".equals(role)) {
            btnAddProduct.setVisibility(View.VISIBLE);
        } else {
            btnAddProduct.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewProduct = view.findViewById(R.id.rvProducts);
        db = FirebaseFirestore.getInstance();
        listProduct = new ArrayList<>();
        // Cargar la lista
        getProducts();
        // Mostrar data
        showData();

//        btnAddProduct.setOnClickListener(view1 -> findNavController(view1).navigate(R.id.productCreateFragment));
    }

    public void showData() {
        db = FirebaseFirestore.getInstance();
        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(getContext()));
        productAdapter = new ProductAdapter(getContext(), listProduct, db);
        recyclerViewProduct.setAdapter(productAdapter);
    }

    public void getProducts() {
        db.collection("products").addSnapshotListener((value, error) -> {
            if (error != null) {
                Toast.makeText(getContext(), "Failed to retrive data", Toast.LENGTH_SHORT);
                return;
            }
            for (DocumentChange dc : value.getDocumentChanges()) {
                if (dc.getType() == DocumentChange.Type.ADDED) {
                    listProduct.add(dc.getDocument().toObject(ProductEntity.class));
                }
            }
            productAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onDestroyView() {//
        super.onDestroyView();
//        binding = null;
    }
}