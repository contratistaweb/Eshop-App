package com.am2.eshopapp.ui.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentViewHolder;

import com.am2.eshopapp.R;
import com.am2.eshopapp.databinding.FragmentHomeBinding;
import com.am2.eshopapp.databinding.FragmentRegisterBinding;
import com.am2.eshopapp.ui.home.entities.ProductEntity;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<ProductEntity> model;

    //listener
    private View.OnClickListener listener;

    public ProductAdapter(Context context, ArrayList<ProductEntity> model) {
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,null,false);
        view.setOnClickListener(this);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        String productName = model.get(position).getName();
        Double productPrice = model.get(position).getPrice();
        int productStock = model.get(position).getStock();
        String productDescription = model.get(position).getDescription();
//        int productImage = model.get(position).getImage();
        holder.jtvProductName.setText(productName);
        holder.jtvProductPrice.setText(productPrice.toString());
        holder.jtvProductStock.setText(productStock);
        holder.jtvProductDescription.setText(productDescription);
//holder.imagen.setImageResource(productImage);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        if(listener!= null){
            listener.onClick(view);
        }
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public class ProductViewHolder extends  RecyclerView.ViewHolder{
        TextView jtvProductName, jtvProductPrice,jtvProductStock, jtvProductDescription;
//        ImageView jivProductImage;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            jtvProductName = itemView.findViewById(R.id.tvProductName);
            jtvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            jtvProductStock = itemView.findViewById(R.id.tvProductsStock);
            jtvProductDescription = itemView.findViewById(R.id.tvProductDescription);
//            jivProductImage  = itemView.findViewById(R.id.tvProductImage);

        }
    }
}
