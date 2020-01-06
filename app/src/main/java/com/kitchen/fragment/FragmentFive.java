package com.kitchen.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.kitchen.activity.R;
import com.kitchen.bean.GetUser;
import com.kitchen.utils.GlobalData;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FragmentFive extends Fragment {

    private static final String TAG = "FragmentFive";
    private GlobalData globalData;
    private GetUser getUser;
    private ImageView userAvatar;
    private TextView nickName;
    private TextView photoTime;
    private LinearLayout btnDownload;
    private LinearLayout btnShare;
    private LinearLayout btnWallpaper;
    private TextView detailTitle;
    private TextView userId;
    private TextView userName;
    private View viewColor;
    private TextView mobliePhone;
    private TextView eMail;
    private Bitmap userImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        View view = inflater.inflate(R.layout.fg_five, viewGroup, false);
        initView(view);
        globalData = (GlobalData) getContext().getApplicationContext();
        return view;
    }

    private OkHttpClient client = new OkHttpClient();

    private void runGetUser(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String result = Objects.requireNonNull(response.body()).string();
            Log.e(TAG, "runGetUser: " + result);
            getUser = globalData.gson.fromJson(result, GetUser.class);
            getUserPhoto();
        }
    }

    private void getUserPhoto() throws IOException {
        String imageUrl = getDataBean().getUserPhoto();
        Request request = new Request.Builder()
                .url(imageUrl)
                .build();
        try(Response response = client.newCall(request).execute()){
            InputStream inputStream = Objects.requireNonNull(response.body()).byteStream();
            userImage = BitmapFactory.decodeStream(inputStream);
            refreshUserData();
        }
    }

    private void refreshUserData() {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userName.setText(userName.getText() + ":" + getDataBean().getUserName());
                userId.setText(userId.getText() + ":" + getDataBean().getUserID());
                mobliePhone.setText(mobliePhone.getText() + ":" + getDataBean().getTelNum());
//            eMail.setText(getUser.getData().get(0).get);
                userAvatar.setImageBitmap(userImage);
            }
        });
    }

    private GetUser.DataBean getDataBean() {
        return getUser.getData().get(0);
    }

    private void initView(View view) {
        userAvatar = (ImageView) view.findViewById(R.id.userAvatar);
        nickName = (TextView) view.findViewById(R.id.nickName);
        photoTime = (TextView) view.findViewById(R.id.photoTime);
        btnDownload = (LinearLayout) view.findViewById(R.id.btn_download);
        btnShare = (LinearLayout) view.findViewById(R.id.btn_share);
        btnWallpaper = (LinearLayout) view.findViewById(R.id.btn_wallpaper);
        detailTitle = (TextView) view.findViewById(R.id.detail_title);
        userId = (TextView) view.findViewById(R.id.user_id);
        userName = (TextView) view.findViewById(R.id.user_name);
        viewColor = (View) view.findViewById(R.id.view_color);
        mobliePhone = (TextView) view.findViewById(R.id.moblie_phone);
        eMail = (TextView) view.findViewById(R.id.e_mail);
    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String userID = globalData.getUserID();
                try {
                    Log.e(TAG, "run: " + userID);
                    if (userID == null)
                        return;
                    runGetUser("http://121.199.22.121:8080/kit/getUser?userID=" + userID);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
