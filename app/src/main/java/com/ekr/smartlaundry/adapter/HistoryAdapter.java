package com.ekr.smartlaundry.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.ekr.smartlaundry.R;
import com.ekr.smartlaundry.data.CuciModel;
import com.ekr.smartlaundry.utils.Session;
import java.util.ArrayList;
import java.util.List;

import static com.ekr.smartlaundry.utils.GlobalPath.step;
import static com.ekr.smartlaundry.utils.GlobalPath.step1;
import static com.ekr.smartlaundry.utils.GlobalPath.step2;
import static com.ekr.smartlaundry.utils.GlobalPath.step3;
import static com.ekr.smartlaundry.utils.GlobalPath.step4;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private ArrayList<CuciModel> cuciModels;
    private Context context;
    private OnItemClickListener mListener;
    private Session session;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public HistoryAdapter(ArrayList<CuciModel> cuciModels, Context context) {
        this.cuciModels = cuciModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view, mListener);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CuciModel data =cuciModels.get(position);
        session = new Session(context);
        holder.textView_name.setText(data.getNama());
        holder.textView_tgl.setText(data.getTanggal());
        holder.textView_resi.setText(data.getResi());
        holder.textView_status.setText(data.getStatus());
        if (!session.getSpRule().equals("user")){
            holder.textView_name.setVisibility(View.VISIBLE);
        }
//        String proses = data.getStatus();
//        switch (proses){
//            case "Menunggu":
//                step1 = 0;
//                step2 = -1;
//                step3 = -1;
//                break;
//            case "Proses":
//                step1 = 1;
//                step2 = 0;
//                step3 = -1;
//                break;
//            case "Selesai":
//                step1 = 1;
//                step2 = 1;
//                step3 = 1;
//                break;
//        }
//
//        List<StepBean> stepBeans = new ArrayList<>();
//        stepBeans.add(new StepBean("Menunggu",step1));
//        stepBeans.add(new StepBean("Proses",step2));
//        stepBeans.add(new StepBean("Selesai",step3));
//
//        holder.horizontalStepView.setStepViewTexts(stepBeans)
//                .setTextSize(14)
//                .setStepsViewIndicatorCompletedLineColor(context.getColor(R.color.orange))
//                .setStepsViewIndicatorUnCompletedLineColor(context.getColor(R.color.white))
//                .setStepViewComplectedTextColor(context.getColor(R.color.white))
//                .setStepViewUnComplectedTextColor(context.getColor(R.color.uncompleted_text_color))
//                .setStepsViewIndicatorCompleteIcon(context.getDrawable(R.drawable.sukses))
//                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(context, R.drawable.default_icon))
//                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(context, R.drawable.attention));
    }

    @Override
    public int getItemCount() {
        return cuciModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_tgl, textView_name,textView_resi,textView_status;
//        HorizontalStepView horizontalStepView;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            textView_tgl = itemView.findViewById(R.id.row_tanggal);
            textView_name = itemView.findViewById(R.id.row_nama);
            textView_resi = itemView.findViewById(R.id.row_resi);
            textView_status = itemView.findViewById(R.id.row_status);
//            horizontalStepView = itemView.findViewById(R.id.horistep);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position1 = getAdapterPosition();
                    if (position1 != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position1);
                    }
                }
            });
        }
    }
}
