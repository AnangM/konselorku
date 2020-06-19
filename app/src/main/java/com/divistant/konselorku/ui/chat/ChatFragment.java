package com.divistant.konselorku.ui.chat;

import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.divistant.konselorku.R;
import com.divistant.konselorku.auth.ui.signup.FinishEdu;
import com.divistant.util.GeneralResponse;

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

        adapter = new ChatRoomAdapter(rooms);
        rv.setAdapter(adapter);

        final ProgressDialog loadingDialog = new ProgressDialog(getActivity());
        loadingDialog.setMax(100);
        loadingDialog.setMessage("Mengambil data chat");
        loadingDialog.setTitle("Tunggu sebentar ya");
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingDialog.show();

        final ChatInterface service = ChatApi.getClient().create(ChatInterface.class);
        Call<GeneralResponse<ChatRoomModel>> call = service
                .getRooms(pref.getString("TOKEN","default"));
        call.enqueue(new Callback<GeneralResponse<ChatRoomModel>>() {
            @Override
            public void onResponse(Call<GeneralResponse<ChatRoomModel>> call, Response<GeneralResponse<ChatRoomModel>> response) {
                if(response.code() == 200){
                    GeneralResponse<ChatRoomModel> gReponse = response.body();
                        if(!(gReponse.getListSize() < 1)){
                            tv.setVisibility(View.INVISIBLE);
                            List<ChatRoomModel> roomModelList = gReponse.getData();
                            for(ChatRoomModel room : roomModelList){
                                rooms.add(room);
                            }
                            adapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(getActivity().getApplicationContext(),
                                    response.code() +" - " + gReponse.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GeneralResponse<ChatRoomModel>> call, Throwable t) {
                loadingDialog.dismiss();
                t.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(),
                        t.getMessage(),
                        Toast.LENGTH_LONG)
                        .show();
            }
        });


        return view;
    }
}
