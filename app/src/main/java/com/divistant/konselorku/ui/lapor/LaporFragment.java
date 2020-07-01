package com.divistant.konselorku.ui.lapor;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.divistant.konselorku.R;
import com.divistant.net.API;
import com.divistant.util.GeneralResponse;
import com.divistant.util.ImageModel;
import com.divistant.util.UploadImage;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.acl.LastOwnerException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaporFragment extends Fragment{
    private Bitmap bitmap;
    private ImageView image, imageHolder;
    private EditText text;

    private SharedPreferences pref;

    private static final int REQUEST_IMAGE_GALERY = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;

    public LaporFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lapor, container, false);

        if(ContextCompat
                .checkSelfPermission(getActivity(), Manifest.permission.CAMERA)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE},1
            );
        }

        ConstraintLayout layout = (ConstraintLayout)  view.findViewById(R.id.lapor_img_container);
        image = (ImageView) view.findViewById(R.id.lapor_image);
        imageHolder = (ImageView) view.findViewById(R.id.lapor_image_holder);
        text = (EditText) view.findViewById(R.id.lapor_text);
        Button lapor = (Button) view.findViewById(R.id.laporkan_go);
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        final AlertDialog.Builder loading = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater1 = getActivity().getLayoutInflater();
        loading.setView(inflater1.inflate(R.layout.general_loading,null));
        loading.setTitle("Melaporkan...");
        final AlertDialog loadingdialog = loading.create();
        lapor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingdialog.show();
               new UploadImage(pref.getString("TOKEN", "key"),
                       bitmap, "Laporan", "Laporan", new UploadImage.UploadImageResponse() {
                   @Override
                   public void OnUploadDone(ImageModel img) {
                       if(!img.isNull()){
                           Log.e("LAPOR","UPLOAD DONE");
                           report(img);
                            loadingdialog.dismiss();
                       }
                   }

                   @Override
                   public void onUploadFailed(String message) {
                       loadingdialog.dismiss();
                       Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                   }
               }).upload();
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String[] options = {"Pilih dari galery","Ambil foto","Batal"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilih gambar");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case 0:
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_IMAGE_GALERY);
                                break;
                            case 1:
                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                                }
                                break;
                            default:
                                dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == getActivity().RESULT_OK){
            if(requestCode == REQUEST_IMAGE_GALERY){
                Uri uri = data.getData();
                try{
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    Toast.makeText(getActivity(), "hey you selected image" + bitmap, Toast.LENGTH_LONG).show();
                    image.setImageBitmap(bitmap);
                    imageHolder.setVisibility(View.INVISIBLE);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }else if(requestCode == REQUEST_IMAGE_CAPTURE){
                Bundle extras = data.getExtras();
                Bitmap img = (Bitmap) extras.get("data");
                image.setImageBitmap(img);
                imageHolder.setVisibility(View.INVISIBLE);
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "K_" + timeStamp + ".jpg";
                File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                try{
                    File myFile = new File(storageDir,imageFileName);
                    FileOutputStream fos = new FileOutputStream(myFile);
                    fos.flush();
                    img.compress(Bitmap.CompressFormat.JPEG,100,fos);
                    fos.close();
                    MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),img,myFile.getPath(), imageFileName);
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),Uri.fromFile(myFile));
                }catch(IOException e){
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "CANNOT SAVE IMAGE", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    private void report(ImageModel img){
        LaporInterface service = API.getClient().create(LaporInterface.class);
        Map<String, Object> jsonParam = new ArrayMap<>();
        jsonParam.put("about",text.getText().toString());
        jsonParam.put("image_id",img.getImage_id());

        RequestBody body = RequestBody.create(okhttp3.MediaType
                        .parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParam))
                        .toString());
        Call<GeneralResponse<LaporModel>> call = service.laporkan(pref.getString("TOKEN","def"),body);
        call.enqueue(new Callback<GeneralResponse<LaporModel>>() {
            @Override
            public void onResponse(Call<GeneralResponse<LaporModel>> call, Response<GeneralResponse<LaporModel>> response) {
                if(response.code()==201){
                    text.setText("");
                    image.setImageDrawable(null);
                    imageHolder.setVisibility(View.VISIBLE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    builder.setView(inflater.inflate(R.layout.lapor_ok_dialog,null))
                            .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.show();
                }else {
                    Log.e("LAPOR","ERROR " + response.code());
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse<LaporModel>> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }
}
