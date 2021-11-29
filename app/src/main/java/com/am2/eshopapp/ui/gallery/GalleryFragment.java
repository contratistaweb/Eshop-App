package com.am2.eshopapp.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.am2.eshopapp.Entities.ProductEntity;
import com.am2.eshopapp.Entities.SharedPreferenceEntities;
import com.am2.eshopapp.R;
import com.am2.eshopapp.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = binding.textGallery;
//        Log.d(getTag(), "productEntity.getName() => "+productEntity.getName());
        textView.setText("Factura a nombre de: " + SharedPreferenceEntities.getName() +
                ". pendiente pasar el producto, tendria que intentar subirlos a la tabla de compras (ya que por parametro no a querido pasar) y hacer el llamado aqui, filtrando por " +
                SharedPreferenceEntities.getEmail() + " para renderizar la info aqui");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}