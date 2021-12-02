package com.am2.eshopapp.ui.home;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.am2.eshopapp.Entities.ProductEntity;
import com.am2.eshopapp.Entities.SharedPreferenceEntities;
import com.am2.eshopapp.R;
import com.am2.eshopapp.databinding.FragmentProductCreateBinding;
import com.am2.eshopapp.databinding.FragmentUpdateProductBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateProductFragment extends Fragment {
    ProductEntity productEntity;
    TextView textView;
    Button jbtnCancelProductEdit, jbtnProductEdit;
    EditText jetEditProductName, jetEditProductDescription, jetEditProductStock, jetEditProductPrice;
    private FragmentUpdateProductBinding binding;
    private FirebaseFirestore db;

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

        db = FirebaseFirestore.getInstance();

        Button jbtnCancelProductEdit = binding.btnCancelEditProduct;
        Button jbtnProductEdit = binding.btnOneProductEdit;
        Button jbtnRegresarTienda = binding.btnRegresarTienda;
        Button jbtnConfirmarCompra = binding.btnConfirmarCompra;

        EditText jetEditProductName = binding.etEditProductName;
        EditText jetEditProductStock = binding.etEditProductStock;
        EditText jetEditProductPrice = binding.etEditProductPrice;
        EditText jetEditProductDescription = binding.etEditProductDescription;
        TextView jtvProductCreateTitle = binding.tvProductCreateTitle;
        TextView jtvFacturaTitle = binding.tvFacturaTitle;
        TextView jtvMensajeCompra = binding.tvMensajeCompra;
        TextView jtvProductoAgotado = binding.tvAgotado;

        productEntity = (ProductEntity) getArguments().getSerializable("key");

        String id = productEntity.getId();
        jetEditProductName.setText(productEntity.getName());
        jetEditProductStock.setText(productEntity.getStock());
        jetEditProductPrice.setText(productEntity.getPrice());
        jetEditProductDescription.setText(productEntity.getDescription());

        Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();

        db.collection("products").document(id.toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String stock = documentSnapshot.getString("stock");
                    if (stock.equals("0")){
                        jtvProductoAgotado.setVisibility(View.VISIBLE);
                        jbtnProductEdit.setVisibility(View.GONE);
                        jbtnConfirmarCompra.setVisibility(View.GONE);
                        jbtnCancelProductEdit.setVisibility(View.GONE);
                        jtvFacturaTitle.setVisibility(View.GONE);
                        jbtnRegresarTienda.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        String rol = SharedPreferenceEntities.leerPreferencia(3);
        if(rol.equals("usuario")){
            jbtnProductEdit.setVisibility(View.GONE);
            jbtnConfirmarCompra.setVisibility(View.VISIBLE);
            jetEditProductStock.setVisibility(View.GONE);
            jtvProductCreateTitle.setVisibility(View.GONE);
            jtvFacturaTitle.setVisibility(View.VISIBLE);
        }

        jbtnConfirmarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference docRef = db.collection("products").document(id.toString());
                Map<String, Object> dataProduct = new HashMap<>();

                String nStock = jetEditProductStock.getText().toString();
                int noStock = Integer.parseInt(nStock);
                int stockTotal = noStock-1;
                String compraProducto = String.valueOf(stockTotal);

                dataProduct.put("stock",compraProducto);

                docRef.update(dataProduct)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                jtvMensajeCompra.setVisibility(View.VISIBLE);
                                jbtnRegresarTienda.setVisibility(View.VISIBLE);
                                jbtnCancelProductEdit.setVisibility(View.GONE);
                                jbtnConfirmarCompra.setVisibility(View.GONE);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Product was not updated", Toast.LENGTH_SHORT).show();
                            }
                        });
//                findNavController(view).navigate(R.id.nav_home);
            }
        });

        jbtnRegresarTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNavController(view).navigate(R.id.nav_home);
            }
        });

        jbtnProductEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference docRef = db.collection("products").document(id.toString());
                Map<String, Object> dataProduct = new HashMap<>();
                dataProduct.put("name",jetEditProductName.getText().toString());
                dataProduct.put("description",jetEditProductDescription.getText().toString());
                dataProduct.put("stock",jetEditProductStock.getText().toString());
                dataProduct.put("price",jetEditProductPrice.getText().toString());

                docRef.update(dataProduct)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "Product updated", Toast.LENGTH_SHORT).show();
                                findNavController(view).navigate(R.id.nav_home);
                                
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Product was not updated", Toast.LENGTH_SHORT).show();
                            }
                        });
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