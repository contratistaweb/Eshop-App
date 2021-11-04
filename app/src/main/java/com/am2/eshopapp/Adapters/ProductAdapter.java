package com.am2.eshopapp.Adapters;

import static androidx.navigation.Navigation.findNavController;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.am2.eshopapp.DrawerActivity;
import com.am2.eshopapp.Entities.ProductEntity;
import com.am2.eshopapp.R;
import com.am2.eshopapp.ui.home.UpdateProductFragment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<ProductEntity> model;


    //listener
    private View.OnClickListener listener;
    private FirebaseFirestore db;

    private Context context;

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

        holder.jtvProductId.setText(productId);
        holder.jtvProductName.setText(productName);
        holder.jtvProductPrice.setText(productPrice);
        holder.jtvProductStock.setText(productStock);
        holder.jtvProductDescription.setText(productDescription);
        builder.setPositiveButton("YES", (dialogInterface, i) -> db.collection("products").document(productId).delete().addOnSuccessListener(unused -> {
            Toast.makeText(inflater.getContext(), "Data deleted", Toast.LENGTH_LONG).show();
            model.remove(holder.getAdapterPosition());
            notifyDataSetChanged();
        }).addOnFailureListener(e -> Toast.makeText(inflater.getContext(), "Fail deleted item", Toast.LENGTH_LONG).show()));
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {

        });
        holder.jbtnProductDelete.setOnClickListener(view -> {
            builder.setMessage("Are you sure of delete this item?");
            builder.create().show();
        });

        // Edit Button
        holder.jbtnProductEdit.setOnClickListener(view -> {
            UpdateProductFragment fragment = new UpdateProductFragment();
            Bundle bundle = new Bundle();
            bundle.putString("id", holder.jtvProductName.getText().toString());
            fragment.setArguments(bundle);
            // se supone reemplaza la vista despues de enviar el dato guardado en el bondle // hasta este punto no sucede nada al hacer click en update
            ((AppCompatActivity) context ).getSupportFragmentManager().beginTransaction().replace(R.id.nav_home, fragment).commit();
            // es la redireccion que usamos entre fragments (funciona para ir de un fragment a otro)
//            findNavController(view).navigate(R.id.updateProductFragment);

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
        TextView jtvProductId, jtvProductName, jtvProductPrice, jtvProductStock, jtvProductDescription;
        Button jbtnProductDelete, jbtnProductEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            jtvProductId = itemView.findViewById(R.id.tvProductId);
            jtvProductName = itemView.findViewById(R.id.tvProductName);
            jtvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            jtvProductStock = itemView.findViewById(R.id.tvProductsStock);
            jtvProductDescription = itemView.findViewById(R.id.tvProductDescription);
            jbtnProductDelete = itemView.findViewById(R.id.btnProductDelete);
            jbtnProductEdit = itemView.findViewById(R.id.btnProductEdit);
        }
    }
}
