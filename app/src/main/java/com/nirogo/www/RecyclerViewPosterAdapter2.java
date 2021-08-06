package com.nirogo.www;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


public class RecyclerViewPosterAdapter2 extends RecyclerView.Adapter<RecyclerViewPosterAdapter2.ProductViewHolder> {


    private HomeFragment mCtxxx;
    List<RecyclerViewPoster2> imageList2;
    // private List<String> data;

    //  private ArrayList<Integer> mImages;

    // private LayoutInflater layoutInflater;
    private Context mContext;


    RecyclerViewPosterAdapter2(HomeFragment mCtxxx, List<RecyclerViewPoster2> imageList2) {
        this.mCtxxx = mCtxxx;
        this.imageList2 = imageList2;
    }

    @NonNull
    @Override
    public RecyclerViewPosterAdapter2.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.poster2,parent,false);
        return new RecyclerViewPosterAdapter2.ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewPosterAdapter2.ProductViewHolder holder, int position) {
        RecyclerViewPoster2 product = imageList2.get(position);

        //loading the image



        Glide.with(mCtxxx)
                .load(product.getImage())
                .into(holder.image);






    }


    @Override
    public int getItemCount() {
        return imageList2.size();
    }




    class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        LinearLayout parentLayout;

        ProductViewHolder(final View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.posterimg2);
            parentLayout = itemView.findViewById(R.id.parent_layoutposter2);


        }
    }

}


