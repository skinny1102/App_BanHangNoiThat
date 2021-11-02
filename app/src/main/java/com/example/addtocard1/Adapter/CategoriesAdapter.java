package com.example.addtocard1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addtocard1.CategoriesProduct;
import com.example.addtocard1.MainActivity;
import com.example.addtocard1.R;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {
    private List<Categories> categoriesList;
    private Context context;
     MainActivity mainActivity;


    public CategoriesAdapter(Context context) {
        this.context = context;
        this.mainActivity = (MainActivity) context;
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
//            Toast.makeText(context,"Không có sản phẩm",Toast.LENGTH_LONG).show();
            return;
        }
        holder.imageView.setImageResource(categories.imgResource);
        holder.textView.setText(categories.nameCategories);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProductCategoies();
                Intent i = new Intent(context, CategoriesProduct.class);
                i.putExtra("Categories",categories.nameCategories);
                i.putExtra("USER_ID",mainActivity.getG_uid());
                context.startActivity(i);
            }
        });
    }

    private void showProductCategoies() {
//        Toast.makeText(context,"Chức năng đang được cập nhật",Toast.LENGTH_LONG).show();

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
