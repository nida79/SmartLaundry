package com.ekr.smartlaundry.ui.order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ekr.smartlaundry.R;
import com.ekr.smartlaundry.data.ProductModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private ArrayList<ProductModel> productModels;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onPlusClick(int position);

        void onMinusClick(int position);
    }

    public OrderAdapter(ArrayList<ProductModel> productModels) {
        this.productModels = productModels;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent,
                false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel model = productModels.get(position);
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        Integer harga = Integer.parseInt(model.getHarga_produk());
        int qty = 0;
        holder.textView_harga.setText(formatRupiah.format((double) harga));
        holder.textView_name.setText(model.getNama_produk());
        holder.plus.setOnClickListener(view -> {
            int result_qty = qty+1;
            holder.textView_result.setText(String.valueOf(result_qty));
        });
        holder.minus.setOnClickListener(view -> {
            int result_qty = qty-1;
            holder.textView_result.setText(String.valueOf(result_qty));
        });
        int result = Integer.parseInt(holder.textView_result.getText().toString());
        if (result <= 0){
            holder.minus.setVisibility(View.INVISIBLE);
        }else {
            holder.minus.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_name, textView_harga, textView_result;
        private ImageView plus, minus;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.tv_name_produk);
            textView_harga = itemView.findViewById(R.id.tv_harga_produk);
            textView_result = itemView.findViewById(R.id.tv_qty_product);
            plus = itemView.findViewById(R.id.plus_qty);
            minus = itemView.findViewById(R.id.minus_qty);
            plus.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onPlusClick(position);
                    }
                }
            });
            minus.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onMinusClick(position);
                    }
                }
            });
        }
    }
}
