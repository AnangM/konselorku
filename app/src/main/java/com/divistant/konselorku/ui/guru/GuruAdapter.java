package com.divistant.konselorku.ui.guru;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.divistant.konselorku.R;

import java.util.ArrayList;
import java.util.List;

public class GuruAdapter extends RecyclerView.Adapter<GuruAdapter.ViewHolder> {
    List<GuruModel> listGuru;
    public GuruAdapter(){}

    public GuruAdapter(List<GuruModel> listGuru) {
        this.listGuru = listGuru;
    }

    @NonNull
    @Override
    public GuruAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.guru_item,parent,false);
        return new GuruAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuruAdapter.ViewHolder holder, int position) {
        GuruModel guru = listGuru.get(position);
       if(guru != null){
           if(guru.getAvatar()==null){
               holder.avatar.setVisibility(View.INVISIBLE);
               String name = "K";
               if(guru.getName() != null){
                   name = guru.getName().toUpperCase();
               }
               char ava = name.charAt(0);
               holder.image_txt.setText(String.valueOf(ava));
           }else{
               holder.image_txt.setVisibility(View.GONE);
               holder.image_txt_layout.setVisibility(View.GONE);
               Glide.with(holder.avatar.getContext())
                       .load(guru.getAvatar())
                       .apply(RequestOptions.circleCropTransform())
                       .into(holder.avatar);
           }

           if(TextUtils.isEmpty(guru.getTitle())){
               holder.title.setText("~");
           }else{
               holder.title.setText(guru.getTitle());
           }

           holder.name.setText(guru.getName());
       }
    }

    @Override
    public int getItemCount() {
        return listGuru.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout image_txt_layout;
        TextView image_txt, name, title;
        ImageView avatar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_txt_layout = (LinearLayout) itemView.findViewById(R.id.item_guru_ava_text_layout);
            image_txt = (TextView) itemView.findViewById(R.id.item_guru_ava_text);
            name = (TextView) itemView.findViewById(R.id.guru_item_name);
            title = (TextView) itemView.findViewById(R.id.guru_item_desc);
            avatar = (ImageView) itemView.findViewById(R.id.guru_item_image);
        }
    }
}
