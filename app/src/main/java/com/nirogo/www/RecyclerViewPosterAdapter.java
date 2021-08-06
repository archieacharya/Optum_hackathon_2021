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


public class RecyclerViewPosterAdapter extends RecyclerView.Adapter<RecyclerViewPosterAdapter.ProductViewHolder> {


    private HomeFragment mCtxxx;
    List<RecyclerViewPoster> imageList;
    // private List<String> data;

    //  private ArrayList<Integer> mImages;

    // private LayoutInflater layoutInflater;
    private Context mContext;


    RecyclerViewPosterAdapter(HomeFragment mCtxxx, List<RecyclerViewPoster> imageList) {
        this.mCtxxx = mCtxxx;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public RecyclerViewPosterAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.poster,parent,false);
        return new RecyclerViewPosterAdapter.ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewPosterAdapter.ProductViewHolder holder, int position) {
        RecyclerViewPoster product = imageList.get(position);

        //loading the image



        Glide.with(mCtxxx)
                .load(product.getImage())
                .into(holder.image);






    }


    @Override
    public int getItemCount() {
        return imageList.size();
    }




    class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        LinearLayout parentLayout;

        ProductViewHolder(final View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.posterimg);
            parentLayout = itemView.findViewById(R.id.parent_layoutposter);


        }
    }

}


