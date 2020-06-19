package com.divistant.konselorku.ui.chat;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.divistant.konselorku.R;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ViewHolder> {

    private List<ChatRoomModel> rooms;

    public ChatRoomAdapter(List<ChatRoomModel> rooms) {
        this.rooms = rooms;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.chat_room_card,parent,false);
        return new ChatRoomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatRoomModel room = rooms.get(position);

        if(room.getRoom_image() == null){
            holder.imageText.setVisibility(View.VISIBLE);
            String ava = room.getRoom_name().toUpperCase();
            char use= ava.charAt(0);
            holder.imageText.setText(String.valueOf(use));
            holder.image.setVisibility(View.INVISIBLE);
        }else{
            holder.imageText.setVisibility(View.INVISIBLE);
            holder.image.setVisibility(View.VISIBLE);
            Glide.with(holder.image.getContext())
                    .load(room.getRoom_image())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.image);
        }
        holder.name.setText(room.getRoom_name());
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, imageText;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.room_name);
            imageText = (TextView) itemView.findViewById(R.id.room_image_text);
            image = (ImageView) itemView.findViewById(R.id.room_image);
        }
    }
}
