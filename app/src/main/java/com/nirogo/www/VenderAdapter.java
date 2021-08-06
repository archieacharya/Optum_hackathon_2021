package com.nirogo.www;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

    public class VenderAdapter extends RecyclerView.Adapter<VenderAdapter.ProductViewHolder> {


        private Context mCtx;
        private List<VenderList> productList;
        private OnItemClickListener mListener;
        AlertDialog.Builder builder;

        public interface OnItemClickListener{
            void onItemClick(int position);
        }

        void setOnItemClickListener(OnItemClickListener listener){
            mListener = listener;
        }

        VenderAdapter(VenderActivity mCtx, List<VenderList> productList) {
            this.mCtx = mCtx;
            this.productList = productList;
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_list,parent,false);
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
            VenderList product = productList.get(position);

            //loading the image
            Glide.with(mCtx)
                    .load(product.getImg())
                    .into(holder.imageView);



            holder.shopname.setText(product.getShop_name());
            holder.shoparea.setText(product.getShop_area());
            holder.distance.setText(product.getDistance()+" KM");


        }

        @Override
        public int getItemCount() {
            return productList.size();
        }


        class ProductViewHolder extends RecyclerView.ViewHolder {

            TextView shopname, shoparea, distance;
            ImageView imageView;

            ProductViewHolder(final View itemView) {
                super(itemView);


                shopname = itemView.findViewById(R.id.shop_name);
                shoparea = itemView.findViewById(R.id.shop_area);
                distance = itemView.findViewById(R.id.distance);
                imageView = itemView.findViewById(R.id.icongrain);

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

