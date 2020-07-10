package com.divistant.konselorku.ui.chat;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.divistant.konselorku.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    String Uid;
    private List<ChatModel> chats;
    private static final int VIEW_TYPE_ME = 1;
    private static final int VIEW_TYPE_OTHER = 2;

    public ChatAdapter(List<ChatModel> chats, String Uid) {
        this.chats = chats;
        this.Uid = Uid;
        Log.e("Adapter","ADAPTER CREATED");
    }

    public void setChats(List<ChatModel> chats) {
        this.chats = chats;
        Log.e("Adapter","ADAPTER SET " + chats.size() + " DATA");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_ME:
                Log.e("Adapter","INFLATE MY LAYOUT ");
                View viewChatMine = layoutInflater.inflate(R.layout.my_chat_item, parent, false);
                viewHolder = new MyChatVH(viewChatMine);
                break;
            case VIEW_TYPE_OTHER:
                Log.e("Adapter","INFLATE OTHER LAYOUT ");
                View viewChatOther = layoutInflater.inflate(R.layout.other_chat_item, parent, false);
                viewHolder = new OtChatVH(viewChatOther);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(TextUtils.equals(chats.get(position).getSenderUid(), Uid)){
            configureMine((MyChatVH) holder, position);
        }else{
            configureOther((OtChatVH) holder,position);
        }
    }

    private void configureMine(final MyChatVH holder, int pos){
        ChatModel chat = chats.get(pos);
//        SimpleDateFormat sfd = new SimpleDateFormat("HH:mm");
//        String time = sfd.format(new Date(chat.getTimestamp() * 1000));

        holder.chat_tv.setText(chat.getMessage());
        holder.time.setText("08:21");
    }

    private void configureOther(final OtChatVH holder, int pos){
        ChatModel chat = chats.get(pos);
//        SimpleDateFormat sfd = new SimpleDateFormat("HH:mm");
//        String time = sfd.format(new Date(chat.getTimestamp() * 1000));
        holder.chat_tv.setText(chat.getMessage());
        holder.time.setText("08:22");
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(TextUtils.equals(chats.get(position).getSenderUid(), Uid)){
            Log.e("Adapter","RETURN ME TYPE");
            return VIEW_TYPE_ME;
        }else{
            Log.e("Adapter","RETURN OT TYPE");
            return VIEW_TYPE_OTHER;
        }
    }

    private static class MyChatVH extends RecyclerView.ViewHolder{
        TextView chat_tv,time;
        public MyChatVH(@NonNull View itemView) {
            super(itemView);
            chat_tv = itemView.findViewById(R.id.my_chat_tv);
            time = itemView.findViewById(R.id.my_time);

        }
    }

    private static class OtChatVH extends RecyclerView.ViewHolder{
        TextView chat_tv, time;
        public OtChatVH(@NonNull View itemView) {
            super(itemView);
            chat_tv = itemView.findViewById(R.id.ot_chat_tv);
            time = itemView.findViewById(R.id.ot_time);
        }
    }
}
