package com.divistant.konselorku.ui.lapor;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.divistant.konselorku.R;
import com.divistant.util.ImageModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LaporanAdapter extends RecyclerView.Adapter<LaporanAdapter.ViewHolder> {
    List<LaporanModel> laporans;

    public LaporanAdapter(List<LaporanModel> laporans) {
        this.laporans = laporans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LaporanAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.laporan_item,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LaporanModel laporan = laporans.get(position);
        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sfd2 = new SimpleDateFormat("dd/MM/yyy HH:mm");
        try {
            Date date = sfd.parse(laporan.getCreated_at());
            Log.e("ADAPTER",date.toString());
            holder.tanggal.setText(sfd2.format(date));
        } catch (ParseException e) {
            holder.tanggal.setText("~");
            e.printStackTrace();
        }
        if(laporan.getImages().size() > 0){
            ImageModel image = laporan.getImages().get(0);
        Log.e("LAPORAN ADAPTER", String.valueOf(image.isNull()) + "");
            if(!image.isNull()){
                Glide.with(holder.image.getContext())
                        .load(image.getUrl())
                        .into(holder.image);
            }
        }
        holder.about.setText(laporan.getAbout());
        holder.nama.setText(laporan.getUser_name());
    }

    @Override
    public int getItemCount() {
        return laporans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView sekolah, tanggal, nama, about;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.laporan_img);
            sekolah = (TextView) itemView.findViewById(R.id.laporan_sekolah);
            tanggal = (TextView) itemView.findViewById(R.id.laporan_waktu);
            nama = (TextView) itemView.findViewById(R.id.laporan_siswa);
            about = (TextView) itemView.findViewById(R.id.laporan_about);
        }
    }
}
