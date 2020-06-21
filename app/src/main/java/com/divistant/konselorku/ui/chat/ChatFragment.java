package com.divistant.konselorku.ui.chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.divistant.konselorku.R;
import com.divistant.konselorku.auth.ui.signup.FinishEdu;
import com.divistant.util.GeneralResponse;
import com.divistant.util.RecyclerItemClickListener;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatFragment extends Fragment {
    private List<ChatRoomModel> rooms = new ArrayList<>();
    private ChatRoomAdapter adapter;
    SharedPreferences pref;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.chat_room_rv);
        final TextView tv = (TextView) view.findViewById(R.id.chat_room_tv);
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager);
        final ProgressBar loading = (ProgressBar) view.findViewById(R.id.chat_loading);
        loading.setVisibility(View.VISIBLE);
        adapter = new ChatRoomAdapter(rooms);
        rv.setAdapter(adapter);

        final ChatInterface service = ChatApi.getClient().create(ChatInterface.class);
        Call<GeneralResponse<ChatRoomModel>> call = service
                .getRooms(pref.getString("TOKEN","default"));
        call.enqueue(new Callback<GeneralResponse<ChatRoomModel>>() {
            @Override
            public void onResponse(Call<GeneralResponse<ChatRoomModel>> call, Response<GeneralResponse<ChatRoomModel>> response) {
                loading.setVisibility(View.GONE);
                if(response.code() == 200){
                    GeneralResponse<ChatRoomModel> gReponse = response.body();
                        if(!(gReponse.getListSize() < 1)){
                            tv.setVisibility(View.GONE);
                            List<ChatRoomModel> roomModelList = gReponse.getData();
                            for(ChatRoomModel room : roomModelList){
                                rooms.add(room);
                            }
                            adapter.notifyDataSetChanged();
                        }else{
                            tv.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity().getApplicationContext(),
                                    response.code() +" - " + gReponse.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse<ChatRoomModel>> call, Throwable t) {
                loading.setVisibility(View.GONE);
                t.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(),
                        t.getMessage(),
                        Toast.LENGTH_LONG)
                        .show();
            }
        });

        rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rv,
                new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ChatRoom.class);
                intent.putExtra("target",(new Gson().toJson(rooms.get(position))));
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));


        return view;
    }
}
