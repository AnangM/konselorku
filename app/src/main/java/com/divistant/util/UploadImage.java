package com.divistant.util;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Base64;
import android.util.Log;

import com.divistant.net.API;
import com.divistant.net.UploadInterface;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadImage {
    private Bitmap image;
    private String token;
    private String name;
    private String alt;

    public UploadImage( String token, Bitmap image) {
        this.image = image;
        this.token = token;
    }

    public UploadImage(Bitmap image, String token, String name, String alt) {
        this.image = image;
        this.token = token;
        this.name = name;
        this.alt = alt;
    }

    public UploadImage(Bitmap image, String token, String name) {
        this.image = image;
        this.token = token;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public ImageModel upload(){
        final ImageModel imgr = new ImageModel();
        UploadInterface service = API.getClient().create(UploadInterface.class);
        Map<String, Object> jsonParam = new ArrayMap<>();
        jsonParam.put("name",this.name);
        jsonParam.put("input_img", this.getBase64());
        if(!TextUtils.isEmpty(this.alt)){
            jsonParam.put("alt",this.alt);
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType
                        .parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParam))
                        .toString());
        Call<GeneralResponse<ImageModel>> call = service.uploadImage(token, body);
        call.enqueue(new Callback<GeneralResponse<ImageModel>>() {
            @Override
            public void onResponse(Call<GeneralResponse<ImageModel>> call, Response<GeneralResponse<ImageModel>> response) {
                GeneralResponse<ImageModel> general = response.body();
                if(general.isSuccess()){
                    if(general.getSingle_data() != null){
                        ImageModel img = general.getSingle_data();
                        imgr.setAlt(img.getAlt());
                        imgr.setImage_id(img.getImage_id());
                        imgr.setName(img.getName());
                        imgr.setUrl(img.getUrl());
                    }
                }else{
                    imgr.setAlt(null);
                    imgr.setImage_id(0);
                    imgr.setName(null);
                    imgr.setUrl(null);
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse<ImageModel>> call, Throwable t) {
                imgr.setAlt(null);
                imgr.setImage_id(0);
                imgr.setName(null);
                imgr.setUrl(null);
                Log.e("[UPLOAD]",t.getMessage());
            }
        });
        return null;
    }

    public String getBase64(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.image.compress(Bitmap.CompressFormat.JPEG,80,byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray,Base64.DEFAULT);
    }

}
