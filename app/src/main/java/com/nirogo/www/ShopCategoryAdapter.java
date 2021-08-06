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

public class ShopCategoryAdapter extends RecyclerView.Adapter<ShopCategoryAdapter.ProductViewHolder> {


    private HomeFragment mCtx;
    private List<ShopCategoryList> productList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    ShopCategoryAdapter(HomeFragment mCtx, List<ShopCategoryList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_category,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ShopCategoryList product = productList.get(position);


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


            shopname = itemView.findViewById(R.id.name);
            shopdesc = itemView.findViewById(R.id.desc);
            imageView = itemView.findViewById(R.id.img);

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