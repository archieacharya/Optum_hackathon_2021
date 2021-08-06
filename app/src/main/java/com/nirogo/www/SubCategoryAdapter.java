package com.nirogo.www;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ProductViewHolder> {


    private SubCategoryActivity mCtx;
    private List<SubCategoryList> productList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    SubCategoryAdapter(SubCategoryActivity mCtx, List<SubCategoryList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_cat_list,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        SubCategoryList product = productList.get(position);


        Glide.with(mCtx)
                .load(product.getSHP_IMG())
                .into(holder.imageView);



        holder.shopname.setText(product.getSHP_CAT());
        holder.shopdesc.setText(product.getSHP_DESC());
        // holder.imageView.setText(product.getDistance()+" KM");

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView shopname, shopdesc;
        ImageView imageView;

        ProductViewHolder(View itemView) {
            super(itemView);


            shopname = itemView.findViewById(R.id.shop_name);
            shopdesc = itemView.findViewById(R.id.desc);
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

