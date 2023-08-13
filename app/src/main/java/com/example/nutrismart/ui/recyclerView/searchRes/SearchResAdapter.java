package com.example.nutrismart.ui.recyclerView.searchRes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutrismart.R;
import com.example.nutrismart.data.api.models.Result;
import com.example.nutrismart.data.api.models.Results;
import com.example.nutrismart.ui.add.AddDetailFragment;
import com.example.nutrismart.ui.recipeDetail.RecipeDetailFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchResAdapter extends RecyclerView.Adapter<SearchResAdapter.ViewHolder> {
    private Context context;
    private List<Result> resultsList;

    public SearchResAdapter(Context context, List<Result> resultsList) {
        this.context = context;
        this.resultsList = resultsList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewResImg;
        TextView textViewResTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewResImg = itemView.findViewById(R.id.imageViewResImg);
            textViewResTitle = itemView.findViewById(R.id.textViewResTitle);

        }
    }

    @NonNull
    @Override
    public SearchResAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_searchres, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResAdapter.ViewHolder holder, int position) {
        //data process will be done on the add activity
        holder.textViewResTitle.setText(resultsList.get(position).name);
        Picasso.get().load(resultsList.get(position).image).placeholder(R.drawable.ic_placeholder).fit().into(holder.imageViewResImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemType;
                if(resultsList.get(position).itemType.equalsIgnoreCase("recipes")){
                    itemType = "recipes";
                } else if (resultsList.get(position).itemType.equalsIgnoreCase("products")) {
                    itemType = "products";
                } else{
                    itemType = "menuItems";
                }
                AddDetailFragment frg = new AddDetailFragment();
                Bundle args = new Bundle();
                args.putString("ID", String.valueOf(resultsList.get(position).id));
                args.putString("itemType", itemType);
                frg.setArguments(args);
                ((AppCompatActivity) v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.contentBox, frg).addToBackStack(null).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }
}
