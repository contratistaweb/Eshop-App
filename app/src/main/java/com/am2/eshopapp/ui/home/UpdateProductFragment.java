package com.am2.eshopapp.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.am2.eshopapp.R;

public class UpdateProductFragment extends Fragment {

    TextView textView;
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
        View view = inflater.inflate(R.layout.fragment_update_product, container, false);

        textView = view.findViewById(R.id.tvRecibedId);
        Bundle bundle = this.getArguments();
        String myString = bundle.getString("id", "");
        textView.setText(myString);

        return inflater.inflate(R.layout.fragment_update_product, container, false);
    }
}