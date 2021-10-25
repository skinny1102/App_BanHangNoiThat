package com.example.addtocard1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.addtocard1.R;

import java.util.List;

public class AddressAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<String> list;
    int selectedPosition = -1;
    public String strAddress;


    public AddressAdapter(Context context, int layout, List<String> list) {
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
    public  class ViewHolder{
        RadioButton radioButton;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            // anh xa ;
            viewHolder = new ViewHolder();
            viewHolder.radioButton = (RadioButton) view.findViewById(R.id.radio_btn);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();

        }
        if(selectedPosition==i){
            viewHolder.radioButton.setChecked(true);
            setStrAddress(list.get(i));

        }else{
            viewHolder.radioButton.setChecked(false);
        }
        viewHolder.radioButton.setText(list.get(i));
        viewHolder.radioButton.setTag(i);

        viewHolder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = (Integer)viewHolder.radioButton.getTag();
                notifyDataSetChanged();
            }
        });
        return view;
    }

    public String getStrAddress() {
        return strAddress;
    }

    public void setStrAddress(String strAddress) {
        this.strAddress = strAddress;
    }
}
