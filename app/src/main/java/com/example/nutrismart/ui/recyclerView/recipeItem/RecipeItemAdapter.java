package com.example.nutrismart.ui.recyclerView.recipeItem;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutrismart.R;
import com.example.nutrismart.data.api.models.Meals;
import com.example.nutrismart.data.api.models.Result;
import com.example.nutrismart.ui.recipeDetail.RecipeDetailFragment;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class RecipeItemAdapter extends RecyclerView.Adapter<RecipeItemAdapter.ViewHolder>{
    List<Meals.Meal> data;
    Context context;

    public RecipeItemAdapter(Context context, List<Meals.Meal> data){
        super();
        this.context = context;
        this.data = data;
        Log.d("DEBUG", "adapter created");
    }

    @NonNull
    @Override
    public RecipeItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_recipeitem_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeItemAdapter.ViewHolder holder, int position) {
        holder.textViewRecipeTitle.setText(data.get(position).title);
        Log.d("DEBUG", "The TextView should be"+ data.get(position).title);
        //modify the data for the image URL
        String imageUrl;
        imageUrl = "https://spoonacular.com/recipeImages/"+data.get(position).id+"-312x231.jpg";
        Log.d("DEBUG",imageUrl);
        Picasso.get().load(imageUrl).placeholder(R.drawable.ic_placeholder).into(holder.imageViewRecipe);
        Log.d("DEBUG", (String) holder.textViewRecipeTitle.getText());
        //Navigate to recipe card
        holder.imageViewRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pass id to recipeDetail frag
                RecipeDetailFragment frg = new RecipeDetailFragment();
                Bundle args = new Bundle();
                args.putString("ID", String.valueOf(data.get(position).id));
                frg.setArguments(args);

                ((AppCompatActivity) v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.content, frg).addToBackStack(null).commit();
            }
        });
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
