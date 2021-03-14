package com.example.simplemarvel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.simplemarvel.models.CharactersInfo;

import java.util.List;


public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.ViewHolder>
{
   private List<CharactersInfo> charactersInfoList;
   private Context context;

   public CharactersAdapter(List<CharactersInfo> charactersInfoList, Context context)
   {
       this.charactersInfoList = charactersInfoList;
       this.context = context;
   }

   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
   {
       View charView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_character, parent, false);
       return new ViewHolder(charView);
   }

   @Override
   public void onBindViewHolder(@NonNull ViewHolder holder, int position)
   {
        CharactersInfo charactersInfo = charactersInfoList.get(position);
        holder.bind(charactersInfo);
   }

   @Override
   public int getItemCount()
   {
       return charactersInfoList.size();
   }

   public class ViewHolder extends RecyclerView.ViewHolder
   {
       public TextView tvName,tvDescription, tvId, tvComicCount;
       public ImageView ivImage;

       public ViewHolder(@NonNull View itemView)
       {
           super(itemView);
           tvName = itemView.findViewById(R.id.tvName);
           tvDescription = itemView.findViewById(R.id.tvDescription);
           tvId = itemView.findViewById(R.id.tvId);
           tvComicCount = itemView.findViewById(R.id.tvComicCount);
           ivImage = itemView.findViewById(R.id.ivImage);
       }

       public void bind(CharactersInfo charactersInfo)
       {
           tvName.setText(charactersInfo.getName());
           tvDescription.setText(charactersInfo.getDescription());
           if(charactersInfo.getDescription().equals(""))
               tvDescription.setVisibility(View.GONE);
           else
               tvDescription.setVisibility(View.VISIBLE);
           tvId.setText("ID: "+ charactersInfo.getId() + "");
           tvComicCount.setText("Comics: "+ charactersInfo.getComicCount() + "");
           Glide.with(context).load(charactersInfo.getImage()).into(ivImage);
       }
   }

}
