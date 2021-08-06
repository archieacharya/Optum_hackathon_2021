package com.nirogo.www;

import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.ProductViewHolder> {


    private DocterDetailActivity mCtx;
    ProgressDialog progressDialog;
    private List<TimeTableList> productList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    TimeTableAdapter(DocterDetailActivity mCtx, List<TimeTableList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.time_list,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        TimeTableList product = productList.get(position);




        holder.date.setText(product.getTM_DATE());
        holder.time.setText(product.getTM_START_TM()+" - "+product.getTM_END_TM()+" HRS");
        holder.amt.setText("INR "+product.getTM_AMT());
        if(product.getTM_STS().equals("Booked")) {
            holder.statusss.setBackgroundResource(R.drawable.bg4);
        } if(product.getTM_STS().equals("Available")) {
            holder.statusss.setBackgroundResource(R.drawable.bg5);
        }
        holder.statusss.setText(product.getTM_STS());


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView date, time, statusss, amt;
        CardView cardd;


        ProductViewHolder(View itemView) {
            super(itemView);



            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.timeee);
            statusss = itemView.findViewById(R.id.status);
            amt = itemView.findViewById(R.id.amttt);
            progressDialog  = new ProgressDialog(itemView.getContext());
            cardd = itemView.findViewById(R.id.carddd);


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