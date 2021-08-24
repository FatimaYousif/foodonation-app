package com.example.foodonation;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;


public class adapter extends RecyclerView.Adapter<adapter.MyViewHolder> implements Filterable {

    private static ArrayList<Food> foodlist;
    ArrayList<Food> backup;
    private Context context;

     RecyclerViewClickListener recyclerViewClickListener;


     public adapter(Context context)
     {
         this.context=context;
     }

//    public adapter(Context context,RecyclerViewClickListener recyclerViewClickListener, ArrayList foodlist) {
    public adapter(Context context, ArrayList foodlist) {
        this.context=context;
        this.foodlist=foodlist;
        backup=new ArrayList<>(foodlist);
//        backup.addAll(foodlist);
//        this.recyclerViewClickListener=recyclerViewClickListener;

    }

    @NonNull
    @NotNull
    @Override
    public adapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater;
        inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.myrow, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  MyViewHolder holder, int position) {
        Food temp=foodlist.get(position);
        Food food=foodlist.get(position);

        String title= food.getTitle();
        String name= food.getName();
        String image= food.getImage();

        holder.food_title.setText(title);
        holder.name.setText(name);
        holder.image.setImageURI(Uri.parse(image));


        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, viewItem.class);
                i.putExtra("title",temp.getTitle());
                i.putExtra("name",temp.getName());
                i.putExtra("address",temp.getAddress());
                i.putExtra("description",temp.getDescription());
                i.putExtra("image",temp.getImage());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodlist.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            ArrayList<Food> filtereddata=new ArrayList<>();
            if(keyword.toString().isEmpty())
                filtereddata.addAll(backup);
            else
            {
                for(Food f: backup)
                {
                    if(f.getTitle().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
                        filtereddata.add(f);
                }
            }
            FilterResults results=new FilterResults();
            results.values=filtereddata;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            foodlist.clear();
            foodlist.addAll((ArrayList<Food>) results.values);
            notifyDataSetChanged();
        }
    };

    public interface RecyclerViewClickListener
    {
        void onClick(View v, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView food_title,name;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);

            //to display image, food title, name of the person

           food_title=itemView.findViewById(R.id.food_title);
            name=itemView.findViewById(R.id.name);
            image=itemView.findViewById(R.id.image);

        }

    }
}

