package com.am2.eshopapp.ui.home;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.am2.eshopapp.Entities.ProductEntity;
import com.am2.eshopapp.R;
import com.am2.eshopapp.databinding.FragmentProductCreateBinding;
import com.am2.eshopapp.databinding.FragmentUpdateProductBinding;

public class UpdateProductFragment extends Fragment {
ProductEntity productEntity;
    TextView textView;
    Button jbtnCancelProductEdit, jbtnProductEdit;
    private FragmentUpdateProductBinding binding;

    public UpdateProductFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUpdateProductBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String hola = getArguments().getString("hola");
        Button jbtnCancelProductEdit = binding.btnCancelEditProduct;
        Button jbtnProductEdit = binding.btnOneProductEdit;

        productEntity = (ProductEntity) getArguments().getSerializable("key");
        Toast.makeText(getContext(), productEntity.getName(), Toast.LENGTH_SHORT).show();

//        textView = view.findViewById(R.id.tvRecibedId);
//        Bundle bundle = this.getArguments();
//        String myString = bundle.getString("id", "");
//        textView.setText(myString);



        jbtnProductEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        jbtnCancelProductEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.nav_home);
            }
        });
    }
}