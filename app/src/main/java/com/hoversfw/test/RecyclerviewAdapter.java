package com.hoversfw.test;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.RecyclerviewHolder> {
    private ArrayList<RecyclerviewItem> list;

    @NonNull
    @Override
    public RecyclerviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,parent,false);
        RecyclerviewHolder holder=new RecyclerviewHolder(v);
        return holder;
    }


    public RecyclerviewAdapter(ArrayList<RecyclerviewItem>mlist){
        list=mlist;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewHolder holder, int position) {
        RecyclerviewItem item=list.get(position);
        holder.image.setImageResource(item.getImageResource());
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(String title,String description){
        list.add(new RecyclerviewItem(R.drawable.hoversfw,title,description));
        list.add(new RecyclerviewItem(R.drawable.hoversfw,"",""));
        notifyDataSetChanged();
    }

    public void remove(){
        list.remove(list.size()-1);
        notifyDataSetChanged();
    }

    public static class RecyclerviewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView title;
        public TextView description;

        public RecyclerviewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.img);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
        }
    }
}
