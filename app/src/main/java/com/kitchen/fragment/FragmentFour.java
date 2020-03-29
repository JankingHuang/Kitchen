package com.kitchen.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import com.kitchen.bean.DeleteUser;
import com.kitchen.bean.GetUser;
import com.kitchen.bean.UserAlter;
import com.kitchen.utils.GlobalData;

import java.io.File;
import java.io.FileOutputStream;
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


public class FragmentFour extends Fragment {

    private static final String TAG = "FragmentFive";
    private GlobalData globalData;
    private GetUser getUser;
    private DeleteUser deleteUser;
    private ImageView userAvatar;
    private LinearLayout btnUserInfor;
    private LinearLayout btnDeleteUser;
    private TextView userId;
    private TextView userName;
    private TextView mobliePhone;
    private TextView eMail;
    private Bitmap userImage;
    private EditText edtUserId;
    private EditText edtMail;
    private EditText edtMobile;
    private EditText edtName;
    private EditText edtPasswd;

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
                new AlertDialog.Builder(getContext())
                        .setTitle("警告")
                        .setMessage("该用户将会被注销！！！！")
                        .setIcon(R.drawable.ic_warning_black_24dp)
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
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
                                }).start();
                            }
                        })
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create()
                        .show();

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
        edtMail = view.findViewById(R.id.input_email_infor);
        edtMobile = view.findViewById(R.id.input_mobile_infor);
        edtName = view.findViewById(R.id.input_name_infor);
        edtPasswd = view.findViewById(R.id.input_password_infor);
        edtMail.setText(email);
        edtMobile.setText(mobile);
        edtName.setText(name);
        edtPasswd.setText(password);
    }

    private void viewListener(View view, int p) {
        view.findViewById(p).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_save_infor:
                        name = String.valueOf(edtName.getText());
                        email = String.valueOf(edtMail.getText());
                        password = String.valueOf(edtPasswd.getText());
                        mobile = String.valueOf(edtMobile.getText());
                        postUserData();
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
            DeleteUser deleteUser = globalData.gson.fromJson(result,DeleteUser.class);
            if("SUCCESS".equals(deleteUser.getCode())){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "用户注销成功", Toast.LENGTH_SHORT).show();
                        try {
                            Thread.sleep(3*1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Objects.requireNonNull(getActivity()).finish();
                    }
                });
            }
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
            //FIXME:由于生命周期的关系，filepaht会被原有的覆盖。即无法更换头像
            File dir = new File(String.valueOf(Environment.getExternalStorageDirectory()));
            if (!dir.exists())
                dir.mkdir();
            File file = new File(dir+"/imge.jpg");
            filePath = file.getPath();
            Log.e(TAG, "getUserPhotoPath: "+filePath);
            FileOutputStream fos = new FileOutputStream(file);
            userImage.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.flush();
            fos.close();
            refreshUserData();
        }
    }

    private void refreshUserData() {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userID = getDataBean().getUserID();
                name = getDataBean().getUserName();
                mobile = getDataBean().getTelNum();
                email = getDataBean().getUserEmail();
                password = getDataBean().getUserPwd();
                userName.setText("用户名:"+name );
                userId.setText("用户ID:" + userID);
                mobliePhone.setText("电话:" +mobile);
                eMail.setText("邮箱:" +email);
                userAvatar.setImageBitmap(userImage);
            }
        });
    }

    private GetUser.DataBean getDataBean() {
        return getUser.getData().get(0);
    }

    private void initView(View view) {
        userAvatar = (ImageView) view.findViewById(R.id.userAvatar);
        btnUserInfor = (LinearLayout) view.findViewById(R.id.btn_user_infor);
        btnDeleteUser = (LinearLayout) view.findViewById(R.id.btn_delete_user);
        userId = (TextView) view.findViewById(R.id.user_id);
        userName = (TextView) view.findViewById(R.id.user_name);
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

    private void postUserData() {
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
                        boolean result = userAlter();
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
        private final WeakReference<FragmentFour> fragmentFiveWeakReference;

        public MyHandler(FragmentFour fragmentFive) {
            this.fragmentFiveWeakReference = new WeakReference<>(fragmentFive);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            FragmentFour fragmentFive = fragmentFiveWeakReference.get();
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

    public boolean userAlter() throws Exception {
        // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userID",userID)
                .addFormDataPart("userName", name)
                .addFormDataPart("userPwd", password)
                .addFormDataPart("telNum", mobile)
                .addFormDataPart("userEmail", email)
                .addFormDataPart("img", userID + ".jpg",
                        RequestBody.create(MEDIA_TYPE_PNG, new File(filePath)))
                .build();

        Request request = new Request.Builder()
                .url("http://121.199.22.121:8080/kit/userAlter?")
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String result = Objects.requireNonNull(response.body()).string();
            Log.e(TAG, "userAlter: " + result);
            UserAlter userAlter = globalData.gson.fromJson(result, UserAlter.class);
            return "SUCCESS".equals(userAlter.getCode());
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
            Log.e(TAG, "onActivityResult: "+filePath);
        }
    }
}