package com.am2.eshopapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.am2.eshopapp.Adapters.ProductAdapter;
import com.am2.eshopapp.Entities.ProductEntity;
import com.am2.eshopapp.R;
import com.am2.eshopapp.databinding.FragmentHomeBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    ProductAdapter productAdapter;
    RecyclerView recyclerViewProduct;
    ArrayList<ProductEntity> listProduct;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        recyclerViewProduct = view.findViewById(R.id.rvProducts);
        listProduct = new ArrayList<>();
        // Cargar la lista
        loadList();
        // Mostrar data
        showData();
        return view;
    }

    public void loadList(){
        listProduct.add(new ProductEntity("Monitor","300","50","LG"));
        listProduct.add(new ProductEntity("Torre","30","30","Gamer"));
        listProduct.add(new ProductEntity("Mouse","30","30","Genius"));
        listProduct.add(new ProductEntity("Teclado","30","30","Samsung"));
    }

    public void showData(){
        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(getContext()));
        productAdapter = new ProductAdapter(getContext(), listProduct);
        recyclerViewProduct.setAdapter(productAdapter);

        productAdapter.SetOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onDestroyView() {//
        super.onDestroyView();
//        binding = null;
    }
}