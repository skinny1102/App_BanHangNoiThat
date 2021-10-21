package com.example.addtocard1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addtocard1.R;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {
    private List<Categories> categoriesList;
    private Context context;

    public CategoriesAdapter(Context context) {
        this.context = context;
    }

    public  void setData(List<Categories> categoriesList){
        this.categoriesList= categoriesList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_categories,parent,false);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        Categories categories = categoriesList.get(position);
        if(categories==null){
            return;
        }
        holder.imageView.setImageResource(categories.imgResource);
        holder.textView.setText(categories.nameCategories);
    }

    @Override
    public int getItemCount() {
     if(categoriesList !=null){
         return categoriesList.size();
     }
        return 0;
    }

    public  class CategoriesViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_item_categories);
            imageView=  itemView.findViewById(R.id.img_iconcategories);
        }
    }
}
