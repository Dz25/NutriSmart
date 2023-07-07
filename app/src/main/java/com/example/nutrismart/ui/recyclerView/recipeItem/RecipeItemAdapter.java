package com.example.nutrismart.ui.recyclerView.recipeItem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutrismart.R;
import com.example.nutrismart.data.api.models.Result;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class RecipeItemAdapter extends RecyclerView.Adapter<RecipeItemAdapter.ViewHolder>{
    List<Result> data;

    public RecipeItemAdapter(List<Result> data){
        super();
        this.data = data;
    }

    @NonNull
    @Override
    public RecipeItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeItemAdapter.ViewHolder holder, int position) {
        holder.textViewRecipeTitle.setText(data.get(position).name);
        Picasso.get().load(data.get(position).image).centerCrop().into(holder.imageViewRecipe);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewRecipeTitle;
        ImageView imageViewRecipe;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRecipeTitle = itemView.findViewById(R.id.textViewRecipeTitle);
            imageViewRecipe = itemView.findViewById(R.id.imageViewRecipe);
        }
    }
}
