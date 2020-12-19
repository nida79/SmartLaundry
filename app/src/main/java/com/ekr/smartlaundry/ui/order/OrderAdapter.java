package com.ekr.smartlaundry.ui.order;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ekr.smartlaundry.R;
import com.ekr.smartlaundry.data.ProductModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private ArrayList<ProductModel> productModels;
   private Context mcontext;

    public OrderAdapter(ArrayList<ProductModel> productModels,Context mcontext) {
        this.productModels = productModels;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel model = productModels.get(position);
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        formatRupiah.setMaximumFractionDigits(0);
        holder.textView_harga.setText(formatRupiah.format((double) model.getHarga_produk()));
        holder.textView_name.setText(model.getNama_produk());
        holder.plus.setOnClickListener(view -> {
            productModels.get(position).setQty(productModels.get(position).getQty()+1);
            holder.textView_result.setText(String.valueOf(productModels.get(position).getQty()));
            int hasil = Integer.parseInt(holder.textView_result.getText().toString());
            if (hasil > 0){
                holder.minus.setVisibility(View.VISIBLE);
            }
            if (hasil <1){
                holder.minus.setVisibility(View.INVISIBLE);
            }
            Intent intent = new Intent("counter");
            intent.putExtra("qty",String.valueOf(hasil));
            intent.putExtra("harga",String.valueOf(productModels.get(position).getHarga_produk()));
            LocalBroadcastManager.getInstance(mcontext).sendBroadcast(intent);
        });
        holder.minus.setOnClickListener(view -> {
            productModels.get(position).setQty(productModels.get(position).getQty()-1);
            holder.textView_result.setText(String.valueOf(productModels.get(position).getQty()));
            int hasil = Integer.parseInt(holder.textView_result.getText().toString());
            if (hasil > 0){
                holder.minus.setVisibility(View.VISIBLE);
            }
            if (hasil <1){
                holder.minus.setVisibility(View.INVISIBLE);
            }
            Intent intent = new Intent("counter");
            intent.putExtra("qty",String.valueOf(hasil));
            intent.putExtra("harga",String.valueOf(model.getHarga_produk()));
            LocalBroadcastManager.getInstance(mcontext).sendBroadcast(intent);
        });

    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_name, textView_harga, textView_result;
        private ImageView plus, minus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.tv_name_produk);
            textView_harga = itemView.findViewById(R.id.tv_harga_produk);
            textView_result = itemView.findViewById(R.id.tv_qty_product);
            plus = itemView.findViewById(R.id.plus_qty);
            minus = itemView.findViewById(R.id.minus_qty);

        }
    }
}
