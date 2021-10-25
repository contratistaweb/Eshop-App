package com.am2.eshopapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.am2.eshopapp.Entities.ProductEntity;
import com.am2.eshopapp.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements View.OnClickListener{

    LayoutInflater inflater;
    ArrayList<ProductEntity> model;

    //listener
    private View.OnClickListener listener;

    public ProductAdapter(Context context, ArrayList<ProductEntity>model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.product_item,parent,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void SetOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String productName = model.get(position).getName();
        String productPrice = model.get(position).getPrice();
        String productStock = model.get(position).getStock();
        String productDescription = model.get(position).getDescription();
        holder.jtvProductName.setText(productName);
        holder.jtvProductPrice.setText(productPrice);
        holder.jtvProductStock.setText(productStock);
        holder.jtvProductDescription.setText(productDescription);
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView jtvProductName, jtvProductPrice,jtvProductStock, jtvProductDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            jtvProductName = itemView.findViewById(R.id.tvProductName);
            jtvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            jtvProductStock = itemView.findViewById(R.id.tvProductsStock);
            jtvProductDescription = itemView.findViewById(R.id.tvProductDescription);
        }
    }
}
