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



public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ProductViewHolder> {


    private HomeFragment mCtx;
    private List<PostList> productList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    PostAdapter(HomeFragment mCtx, List<PostList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.post_recycler,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        PostList product = productList.get(position);


        Glide.with(mCtx)
                .load(product.getImg())
                .into(holder.imageView);



        holder.head.setText(product.getHead());
        holder.desc.setText(product.getDesc());
        // holder.imageView.setText(product.getDistance()+" KM");

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView head, desc;
        ImageView imageView;

        ProductViewHolder(View itemView) {
            super(itemView);


            head = itemView.findViewById(R.id.head);
            desc = itemView.findViewById(R.id.desc);
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

