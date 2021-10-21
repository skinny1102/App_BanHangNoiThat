package com.example.addtocard1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.addtocard1.R;

import java.util.List;

public class CategoriesAdapter1 extends BaseAdapter {
    Context context;
    int layout;
    List<Categories> list;

    public CategoriesAdapter1(Context context, int layout, List<Categories> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            // anh xa ;
            viewHolder = new ViewHolder();
            viewHolder.textView =  (TextView) view.findViewById(R.id.tv_item_categories);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.img_iconcategories);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.textView.setText(list.get(i).nameCategories);
        viewHolder.imageView.setImageResource(list.get(i).imgResource);
        return view;
    }
}
