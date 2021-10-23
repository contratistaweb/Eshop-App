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

import com.am2.eshopapp.R;
import com.am2.eshopapp.databinding.FragmentHomeBinding;
import com.am2.eshopapp.ui.home.adapters.ProductAdapter;
import com.am2.eshopapp.ui.home.entities.ProductEntity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
//    private FragmentHomeBinding binding;

    private FirebaseFirestore db;
    ArrayList<ProductEntity> productEntityArrayList;

//    List <ProductEntity> arrayProducts = new ArrayList<>();
    ProductAdapter productAdapter;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

//        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
        View view = inflater.inflate(R.layout.fragment_home,container,false);
//        final TextView textView = binding.textHome;

        db = FirebaseFirestore.getInstance();

//        productAdapter = new ProductAdapter(getContext(), productEntityArrayList);
        recyclerView = view.findViewById(R.id.rvProducts);
        productEntityArrayList = new ArrayList<>();
        // cargar lista
        loadProducts();
        return view;
    }

    public  void loadProducts(){
        productEntityArrayList.add(new ProductEntity("ax1","product1",1.0,1,1,1,"test product 1"));
        productEntityArrayList.add(new ProductEntity("ax2","product2",1.0,1,1,1,"test product 2"));
        productEntityArrayList.add(new ProductEntity("ax3","product3",1.0,1,1,1,"test product 3"));
        productEntityArrayList.add(new ProductEntity("ax4","product4",1.0,1,1,1,"test product 4"));
        productEntityArrayList.add(new ProductEntity("ax5","product5",1.0,1,1,1,"test product 5"));
        productEntityArrayList.add(new ProductEntity("ax6","product6",1.0,1,1,1,"test product 6"));
        productEntityArrayList.add(new ProductEntity("ax7","product7",1.0,1,1,1,"test product 7"));
        showData();
    }

    public  void showData(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        productAdapter = new ProductAdapter(getContext(),productEntityArrayList);
        recyclerView.setAdapter(productAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding = null;
    }
}