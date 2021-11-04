package com.am2.eshopapp.ui.home;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.am2.eshopapp.Adapters.ProductAdapter;
import com.am2.eshopapp.Entities.ProductEntity;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewProduct = view.findViewById(R.id.rvProducts);
        db = FirebaseFirestore.getInstance();
        listProduct = new ArrayList<>();
        // Cargar la lista
        getProducts();
        // Mostrar data
        showData();


        btnAddProduct = view.findViewById(R.id.btnGoProductCreate);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.productCreateFragment);
            }
        });

        return view;
    }

    public void showData() {
        db = FirebaseFirestore.getInstance();
        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(getContext()));
        productAdapter = new ProductAdapter(getContext(), listProduct, db);
        recyclerViewProduct.setAdapter(productAdapter);

        productAdapter.SetOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void getProducts() {
        db.collection("products")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
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
                    }
                });
    }

    @Override
    public void onDestroyView() {//
        super.onDestroyView();
//        binding = null;
    }
}