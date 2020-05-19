package com.kitchen.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.kitchen.bean.Login;
import com.kitchen.bean.Register;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.kitchen.activity.Welcome.gson;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    private EditText nameText;
    private EditText addressText;
    private EditText emailText;
    private EditText mobileText;
    private EditText passwordText;
    private EditText reEnterPasswordText;
    private Button signupButton;
    private TextView loginLink;
    private ImageButton imageButton;
    private MyHandler myHandler;


    private final OkHttpClient client = new OkHttpClient();
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
    private final static int FILE_REQUEST_CODE = 1;
    private String userID;
    private String name;
    private String email;
    private String mobile;
    private String password;
    private String reEnterPassword;
    private String filePath;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        nameText = findViewById(R.id.input_name);
        addressText = findViewById(R.id.input_address);
        emailText = findViewById(R.id.input_email);
        mobileText = findViewById(R.id.input_mobile);
        passwordText = findViewById(R.id.input_password);
        reEnterPasswordText = findViewById(R.id.input_reEnterPassword);
        signupButton = findViewById(R.id.btn_signup);
        loginLink = findViewById(R.id.link_login);
        imageButton = findViewById(R.id.select_image);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, FilePickerActivity.class);
                intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                        .setCheckPermission(true)
                        .enableImageCapture(true)
                        .setShowVideos(false)
                        .setSkipZeroSizeFiles(true)
                        .setMaxSelection(10)
                        .build());
                startActivityForResult(intent, FILE_REQUEST_CODE);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        myHandler = new MyHandler(this);
    }

    public void signup() {
        Log.d(TAG, "Signup");

//        if (!validate()) {
//            //TODO:数据合法性验证完成
//            onSignupFailed();
//            return;
//        }

        signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        userID = nameText.getText().toString();
        name = addressText.getText().toString();
        email = emailText.getText().toString();
        mobile = mobileText.getText().toString();
        password = passwordText.getText().toString();

        // TODO: Implement your own signup logic here.


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        postDataSignUp(filePath);
                        progressDialog.dismiss();
                        Toast.makeText(SignupActivity.this, "账户创建成功", Toast.LENGTH_SHORT).show();
                    }
                }, 3000);

    }


    class MyHandler extends Handler {
        private final WeakReference<SignupActivity> mWeakReference;

        public MyHandler(SignupActivity activity) {
            this.mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SignupActivity signupActivity = mWeakReference.get();
            if (signupActivity != null) {
                if (msg.what == 1) {
                    if ((boolean) msg.obj) {
                        onSignupSuccess();
                    } else {
                        onSignupFailed();
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myHandler != null) {
            myHandler.removeCallbacksAndMessages(null);
            myHandler = null;
        }
    }

    private void postDataSignUp(final String filePath) {
        /*
         *@Author Jankin
         *@Description 上传用户注册信息
         */
        Log.e(TAG, "postData: " + filePath);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        boolean result = runOk(filePath);
                        Message message = myHandler.obtainMessage();
                        message.what = 1;
                        message.obj = result;
                        myHandler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public boolean runOk(String filePath) throws Exception {
        // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userID", userID)
                .addFormDataPart("userName", name)
                .addFormDataPart("userPwd", password)
                .addFormDataPart("telNum", mobile)
                .addFormDataPart("userEmail", email)
                .addFormDataPart("img", userID+".jpg",
                        RequestBody.create(MEDIA_TYPE_PNG, new File(
                                filePath)))
                .build();

        Request request = new Request.Builder()
                .url("http://121.199.22.121:8080/kit/userRegister?")
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            Register register = gson.fromJson(response.body().string(), Register.class);
            Log.e(TAG, "Register: "+register.toString() );
            if ("SUCCESS".equals(register.getCode())) {
                return true;
            } else {
                return false;
            }
        }
    }

    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        Toast.makeText(this, "Sign up Success!!!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Sign up Failed!!!", Toast.LENGTH_LONG).show();
        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        userID = nameText.getText().toString();
        name = addressText.getText().toString();
        email = emailText.getText().toString();
        mobile = mobileText.getText().toString();
        password = passwordText.getText().toString();
        reEnterPassword = reEnterPasswordText.getText().toString();

        if (userID.isEmpty() || userID.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (name.isEmpty()) {
            addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            addressText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email name");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length() != 11) {
            mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            reEnterPasswordText.setError(null);
        }
        if (filePath == null || filePath.isEmpty()) {
            valid = false;
            Toast.makeText(this, "请选择头像", Toast.LENGTH_SHORT).show();
        }

        return valid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
