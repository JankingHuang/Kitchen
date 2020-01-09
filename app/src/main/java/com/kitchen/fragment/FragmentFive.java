package com.kitchen.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.kitchen.activity.R;
import com.kitchen.activity.SignupActivity;
import com.kitchen.bean.DeleteUser;
import com.kitchen.bean.GetUser;
import com.kitchen.utils.GlobalData;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;


public class FragmentFive extends Fragment {

    private static final String TAG = "FragmentFive";
    private GlobalData globalData;
    private GetUser getUser;
    private DeleteUser deleteUser;
    private ImageView userAvatar;
    private TextView nickName;
    private TextView photoTime;
    private LinearLayout btnTheme;
    private LinearLayout btnUserInfor;
    private LinearLayout btnDeleteUser;
    private TextView detailTitle;
    private TextView userId;
    private TextView userName;
    private View viewColor;
    private TextView mobliePhone;
    private TextView eMail;
    private Bitmap userImage;
    private EditText edtUserId;
    private EditText edtMail;
    private EditText edtMobile;
    private EditText edtName;
    private EditText EdtPasswd;
    private String userID;
    private String password;
    private String name;
    private String mobile;
    private String email;


    private final OkHttpClient client = new OkHttpClient();
    private MyHandler myHandler;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
    private final static int FILE_REQUEST_CODE = 1;
    private String filePath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_five, viewGroup, false);
        globalData = (GlobalData) getContext().getApplicationContext();
        initView(view);
        Listener();
        myHandler = new MyHandler(this);
        return view;
    }

    public void runUserDelete(String url, String userID) throws Exception {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userID", userID)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String result = response.body().string();
            Log.e(TAG, "response: " + result);
            deleteUser = globalData.gson.fromJson(result, DeleteUser.class);
            if ("SUCCESS".equals(deleteUser.getCode())) {
                Toast.makeText(globalData, "用户注销成功！！", Toast.LENGTH_SHORT).show();
                //TODO:退出app
            }
        }
    }

    private void Listener() {
        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userID = globalData.getUserID();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (userID == null)
                                return;
                            runDeleteUser("http://121.199.22.121:8080/kit/logout?userID=" + userID);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    private String getUserID() {
                        return globalData.getUserID();
                    }
                }).start();
            }
        });
        btnTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnUserInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: ");
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View view = inflater.inflate(R.layout.edit_user_layout, null);
                initDialogView(view);
                new AlertDialog.Builder(getContext())
                        .setView(view)
                        .create()
                        .show();
                viewListener(view, R.id.btn_save_infor);
                viewListener(view, R.id.select_image_infor);
            }
        });
    }

    private void initDialogView(View view) {
        edtUserId = view.findViewById(R.id.input_id_infor);
        edtMail = view.findViewById(R.id.input_email_infor);
        edtMobile = view.findViewById(R.id.input_mobile_infor);
        edtName = view.findViewById(R.id.input_name_infor);
        EdtPasswd = view.findViewById(R.id.input_password_infor);
    }

    private void viewListener(View view, int p) {
        view.findViewById(p).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_save_infor:
                        postUserData(filePath);
                        break;
                    case R.id.select_image_infor:
                        Intent intent = new Intent(getContext(), FilePickerActivity.class);
                        intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                                .setCheckPermission(true)
                                .enableImageCapture(true)
                                .setShowVideos(false)
                                .setSkipZeroSizeFiles(true)
                                .setMaxSelection(10)
                                .build());
                        startActivityForResult(intent, FILE_REQUEST_CODE);
                        break;
                }
            }
        });
    }


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

    private void runDeleteUser(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String result = response.body().string();
            Log.e(TAG, "runDeleteUser: " + result);
        }
    }

    private void getUserPhoto() throws IOException {
        String imageUrl = getDataBean().getUserPhoto();
        Request request = new Request.Builder()
                .url(imageUrl)
                .build();
        try (Response response = client.newCall(request).execute()) {
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
                eMail.setText(eMail.getText() + ":" + getDataBean().getUserEmail());
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
        btnTheme = (LinearLayout) view.findViewById(R.id.btn_theme);
        btnUserInfor = (LinearLayout) view.findViewById(R.id.btn_user_infor);
        btnDeleteUser = (LinearLayout) view.findViewById(R.id.btn_delete_user);
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

    private void postUserData(final String filePath) {
        /*
         *@Author Jankin
         *@Description 上传用户修改后的信息
         */
        Log.e(TAG, "postData: " + filePath);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        boolean result = userAlter(filePath);
                        Message message = myHandler.obtainMessage();
                        message.what = 2;
                        message.obj = result;
                        myHandler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    class MyHandler extends Handler {
        private final WeakReference<FragmentFive> fragmentFiveWeakReference;

        public MyHandler(FragmentFive fragmentFive) {
            this.fragmentFiveWeakReference = new WeakReference<>(fragmentFive);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            FragmentFive fragmentFive = fragmentFiveWeakReference.get();
            if (fragmentFive != null) {
                if (msg.what == 2) {
                    if ((boolean) msg.obj) {
                        Toast.makeText(globalData, "修改用户信息成功", Toast.LENGTH_SHORT).show();
                        onResume();
                    } else {
                        Toast.makeText(globalData, "修改用户信息失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    public boolean userAlter(String filePath) throws Exception {
        // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userID", userID)
                .addFormDataPart("userName", name)
                .addFormDataPart("userPwd", password)
                .addFormDataPart("telNum", mobile)
                .addFormDataPart("userEmail", email)
                .addFormDataPart("img", userID + ".jpg",
                        RequestBody.create(MEDIA_TYPE_PNG, new File(
                                filePath)))
                .build();

        Request request = new Request.Builder()
                .url("http://121.199.22.121:8080/kit/userRegister?")
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String result = Objects.requireNonNull(response.body()).string();
            Log.e(TAG, "userAlter: " + result);
//            UserAlter userAlter = globalData.gson.fromJson(response.body().string(), UserAlter.class);
//            if ("SUCCESS".equals(userAlter.getCode())) {
//                return true;
//            } else {
//                return false;
//            }
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null) {
            filePath = (data.<MediaFile>getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES)
                    .get(0))
                    .getPath();
        }
    }
}