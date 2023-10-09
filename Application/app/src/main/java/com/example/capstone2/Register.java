package com.example.capstone2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone2.server_connection.RetrofitBuilder;
import com.example.capstone2.server_connection.User;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    private String register_username;
    private String register_userpassword;
    private String register_userrealname;
    private Button register_button;
    private String register_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_button = findViewById(R.id.register);
        EditText register_username_E=findViewById(R.id.register_username);
        EditText register_userpassword_E=findViewById(R.id.register_userpassword);
        EditText register_userrealname_E=findViewById(R.id.register_userrealname);
        register_button.setOnClickListener(view ->
        {
            register_username = register_username_E.getText().toString();
            register_userpassword=register_userpassword_E.getText().toString();
            register_userrealname=register_userrealname_E.getText().toString();
            User user=new User(register_username,register_userpassword,register_userrealname);
            user.setUsername(register_username_E.getText().toString());
            user.setPassword(register_userpassword_E.getText().toString());
            user.setRealname(register_userrealname_E.getText().toString());

            Log.d("ButtonClicked","register_User_Name="+register_username+      "register_PassWord="+register_userpassword+
                    "register_Real_Name="+register_userrealname);
            Post_Register(user);
        });
    }

    private void Post_Register(User user)
    {
        Call<ResponseBody> call = RetrofitBuilder.getApi().getRegisterResponse(user);
        call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if (response.isSuccessful()) {
                    try {
                        register_result=response.body().string();
                        if(register_result.equals("0"))
                        {
                            Toast.makeText(Register.this, "이미 사용중인 ID입니다.", Toast.LENGTH_LONG).show();
                        }
                        else if(register_result.equals("1")) {
                            Log.d("Register_Result: ", "Register_result: " + register_result);
                            register_result = response.body().string();
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);
                        }
                    } catch (IOException e) {
                        Log.d("RESPONSE", "Failed to read response data");}

                } else {
                    Log.d("RESULT", response.message());
                    Log.d("RESPONSE", "FAILURE");}}

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.d("CONNECTION FAILURE: ", t.getLocalizedMessage());
            }
        });
    }

}