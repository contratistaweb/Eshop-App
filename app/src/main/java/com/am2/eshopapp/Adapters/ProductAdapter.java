package com.am2.eshopapp.Adapters;

import static androidx.navigation.Navigation.findNavController;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.am2.eshopapp.Entities.ProductEntity;
import com.am2.eshopapp.Entities.SharedPreferenceEntities;
import com.am2.eshopapp.Entities.Usuario;
import com.am2.eshopapp.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<ProductEntity> model;
    ProductEntity productEntity;

    //listener
    private View.OnClickListener listener;
    private FirebaseFirestore db;

    private Context context;

    private Usuario usuario;

    public ProductAdapter(Context context, ArrayList<ProductEntity> model, FirebaseFirestore db) {
        this.inflater = LayoutInflater.from(context);
        this.model = model;
        this.db = db;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.product_item, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void SetOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(inflater.getContext());

        String productId = model.get(position).getId();
        String productName = model.get(position).getName();
        String productPrice = model.get(position).getPrice();
        String productStock = model.get(position).getStock();
        String productDescription = model.get(position).getDescription();
        //String productImageUrl = model.get(position).getImageUrl();

        holder.jtvProductId.setText(productId);
        holder.jtvProductName.setText("Product name: " + productName);
        holder.jtvProductPrice.setText("Price: " + productPrice);
        holder.jtvProductStock.setText("Stock: " + productStock);
        holder.jtvProductDescription.setText("Description: " + productDescription);

        Glide.with(context)
                .load(model.get(position).getImageUrl())
                .placeholder(R.drawable.bolsa_de_la_compra)
                .error(R.drawable.bolsa_de_la_compra)
                .into(holder.jivProductImg);

        builder.setPositiveButton("YES", (dialogInterface, i) -> db.collection("products").document(productId).delete().addOnSuccessListener(unused -> {
            Toast.makeText(inflater.getContext(), "Data deleted", Toast.LENGTH_LONG).show();
            model.remove(holder.getAdapterPosition());
            notifyDataSetChanged();
        }).addOnFailureListener(e -> Toast.makeText(inflater.getContext(), "Fail deleted item", Toast.LENGTH_LONG).show()));
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {

        });

        if ("vendedor".equals(SharedPreferenceEntities.getRol())) {
//            holder.jbtnAddProduct.setVisibility(View.VISIBLE);
            holder.jbtnProductDelete.setVisibility(View.VISIBLE);
            holder.jbtnProductEdit.setVisibility(View.VISIBLE);
            holder.jbtnComprar.setVisibility(View.INVISIBLE);
        }

        // Delete Button
        holder.jbtnProductDelete.setOnClickListener(view -> {
            builder.setMessage("Are you sure of delete this item?");
            builder.create().show();
        });

        // Edit Button
        holder.jbtnProductEdit.setOnClickListener(view -> {
            productEntity = model.get(position);
//            SharedPreferenceEntities.limpiarPreferencia();
            Bundle bundle = new Bundle();
            bundle.putSerializable("key", productEntity);
            Navigation.findNavController(holder.itemView).navigate(R.id.updateProductFragment, bundle);
//            Navigation.findNavController(holder.view).navigate(R.id.updateProductFragment,bundle);
        });

        holder.jbtnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SharedPreferenceEntities.getEmail() == null) {
                    Toast.makeText(context, "Debes iniciar sesión.", Toast.LENGTH_LONG).show();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("key", productEntity);
                    Navigation.findNavController(holder.itemView).navigate(R.id.nav_login, bundle);
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("key", productEntity);
                    Navigation.findNavController(holder.itemView).navigate(R.id.nav_gallery, bundle);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView jetUserEmail, jetUserName, jtvProductId, jtvProductName, jtvProductPrice, jtvProductStock, jtvProductDescription;
        Button jbtnAddProduct, jbtnProductDelete, jbtnProductEdit, jbtnComprar;
        ImageView jivProductImg;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            jetUserEmail = itemView.findViewById(R.id.etEmail);
            jetUserName = itemView.findViewById(R.id.etName);
            jtvProductId = itemView.findViewById(R.id.tvProductId);
            jtvProductName = itemView.findViewById(R.id.tvProductName);
            jtvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            jtvProductStock = itemView.findViewById(R.id.tvProductsStock);
            jtvProductDescription = itemView.findViewById(R.id.tvProductDescription);
            jbtnAddProduct = itemView.findViewById(R.id.btnGoProductCreate);
            jbtnProductDelete = itemView.findViewById(R.id.btnProductDelete);
            jbtnProductEdit = itemView.findViewById(R.id.btnProductEdit);
            jbtnComprar = itemView.findViewById(R.id.btnComprar);
            jivProductImg = itemView.findViewById(R.id.ivProductImg);
        }
    }
}
