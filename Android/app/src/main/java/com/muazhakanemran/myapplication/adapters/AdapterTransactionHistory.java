package com.muazhakanemran.myapplication.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muazhakanemran.myapplication.R;
import com.muazhakanemran.myapplication.models.Job;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by muazekici on 17.02.2018.
 */

public class AdapterTransactionHistory extends RecyclerView.Adapter<AdapterTransactionHistory.TransactionViewHolder> {


    LayoutInflater layoutInflater;
    List<Job> activeJobs, passiveJobs;

    public AdapterTransactionHistory(Context context, List<Job> actives, List<Job> passives){
        layoutInflater = LayoutInflater.from(context);
        this.activeJobs = actives;
        this.passiveJobs = passives;
    }


    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_transaction,parent,false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {

        if(position < activeJobs.size()){
            holder.tvItemId.setText(activeJobs.get(position).getId());
            holder.tvItemQuantity.setText(activeJobs.get(position).getQuantity()+"");
            holder.tvItemStatus.setText("active");
        }else{
            int newPos = position - (activeJobs == null ? 0 :activeJobs.size());
            holder.tvItemId.setText(passiveJobs.get(newPos).getId());
            holder.tvItemQuantity.setText(passiveJobs.get(newPos).getQuantity()+"");
            holder.tvItemStatus.setText("active");
        }

    }

    @Override
    public int getItemCount() {
        return (activeJobs == null ? 0: activeJobs.size() )+ (passiveJobs == null ? 0:passiveJobs.size());
    }



    public class TransactionViewHolder extends RecyclerView.ViewHolder {

        TextView tvItemId;

        TextView tvItemQuantity;

        TextView tvItemStatus;

        public TransactionViewHolder(View view) {
            super(view);
            tvItemId = view.findViewById(R.id.tv_item_id);
            tvItemStatus = view.findViewById(R.id.tv_item_status);
            tvItemQuantity = view.findViewById(R.id.tv_item_quantity);
        }
    }
}
