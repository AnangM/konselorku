package com.divistant.konselorku.ui.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.divistant.konselorku.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom extends AppCompatActivity {
    ChatRoomModel room;
    FirebaseDatabase db;
    DatabaseReference ref;
    FirebaseAuth mAuth;
    EditText message;
    ImageView send;
    RecyclerView rv;
    FirebaseUser user;
    ChatAdapter adapter;
    List<ChatModel> chats = new ArrayList<>();
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        Intent intent = getIntent();
        room = (new Gson()).fromJson(intent.getStringExtra("target"),ChatRoomModel.class);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance();
        rv = (RecyclerView) findViewById(R.id.chat_rv);
        send = (ImageView)findViewById(R.id.sendButton);
        message = (EditText)findViewById(R.id.messageArea);
        RecyclerView rv = (RecyclerView) findViewById(R.id.chat_rv);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        adapter = new ChatAdapter(chats,pref.getString("UID","def"));
        final LinearLayoutManager manager = new LinearLayoutManager(ChatRoom.this,
                LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);

        ref = db.getReference()
                .child("konselorku")
                .child("messages")
                .child(room.getRoom_id())
                .child("chats");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatModel chat = new ChatModel();
                chat.setMessage(message.getText().toString());
                chat.setSenderUid(pref.getString("UID","None"));
                chat.setIs_available(true);
                chat.setTimestamp(System.currentTimeMillis());
                DatabaseReference key = ref.push();
                chat.setKey(key.getKey());
                key.setValue(chat);
                message.setText("");
                Log.e("CLICKED",room.getRoom_id());
            }
        });

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chats.clear();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    ChatModel chat = new ChatModel();
                    chat.setMessage(snap.child("message").getValue(String.class));
                    chat.setKey(snap.getKey());
                    chat.setSenderUid(snap.child("senderUid").getValue(String.class));
                    chat.setIs_available(snap.child("is_available").getValue(Boolean.class));
                    chat.setTimestamp(snap.child("timesamp").getValue(Long.class));
                    if (snap.hasChild("image")) {
                        chat.setImage(snap.child("image").getValue(String.class));
                    }
                    chats.add(chat);
                }
                adapter.setChats(chats);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("CHAT",error.getMessage());
                error.toException().printStackTrace();
            }
        });

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                scrollToBottom();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void scrollToBottom(){
        rv.scrollToPosition(adapter.getItemCount()-1);
    }
}
