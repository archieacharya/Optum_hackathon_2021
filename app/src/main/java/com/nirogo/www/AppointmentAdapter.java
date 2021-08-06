package com.nirogo.www;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ProductViewHolder> {

    private AppointmentHistory mCtx;
    private List<AppointmentList> productList;
    String quan_ttl = "0";
    //ElegantNumberButton quantityacac;


    private AppointmentAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    void setOnItemClickListener(AppointmentHistory listener) {
        mListener = listener;
    }


    AppointmentAdapter(AppointmentHistory mCtx, List<AppointmentList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public AppointmentAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_recycler, parent, false);
        return new AppointmentAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.ProductViewHolder holder, int position) {
        AppointmentList product = productList.get(position);

        //loading the image


        holder.ord_id.setText("Order ID: "+product.getOrd_id());
        holder.total_price.setText("Amount: ₹" + product.getTotal_price());
        holder.shop_name.setText(product.getShop_name()+", "+product.getShop_area());
        holder.merchant_status.setText("Date: "+product.getDate());
        holder.active_time.setText("Slot: "+product.getStart_time()+" to "+product.getEnd_time());





        //quantityacac.indexOfChild(product.getId());


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        int total = 0;
        TextView ord_id, merchant_status, total_price, active_time, shop_name;

        //HashMap<String, String> data = new HashMap<>();


        ProductViewHolder(final View itemView) {
            super(itemView);



            ord_id = itemView.findViewById(R.id.ord_id);
            merchant_status = itemView.findViewById(R.id.merchant_status);
            total_price = itemView.findViewById(R.id.amount);
            active_time = itemView.findViewById(R.id.active_time);
            shop_name = itemView.findViewById(R.id.shop_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });


        }


    }
}


