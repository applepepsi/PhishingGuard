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
import com.example.capstone2.utils.Utils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private String username = "";
    private String password = "";
    private Button login_button;
    private String login_result="";
//    private Button register_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_button = findViewById(R.id.Login_Button);
        EditText id_E=findViewById(R.id.User_Id);
        EditText password_E=findViewById(R.id.User_PassWord);
        TextView register_button =findViewById(R.id.register);

        login_button.setOnClickListener(view ->
        {
            username = id_E.getText().toString();
            password=password_E.getText().toString();

            User user=new User(username,password,null);
            user.setUsername(id_E.getText().toString());
            user.setPassword(password_E.getText().toString());

            Log.d("ButtonClicked","id="+username+      "PassWord="+password);
            Post_Login(user);
        });
        register_button.setOnClickListener(view ->
        {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });
    }

    private void Post_Login(User user)
    {
        Call<ResponseBody> call = RetrofitBuilder.getApi().getLoginResponse(user);
        call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if (response.isSuccessful()) {
                    try {

                        login_result=response.body().string();
                        if(login_result.equals("0"))
                        {
                            Toast.makeText(Login.this, "아이디 또는 비밀번호를 확인해주세요", Toast.LENGTH_LONG).show();
                        }
                        else if(login_result !=null)
                        {
                            Utils.init(getApplicationContext());
                            Utils.setAccessToken(login_result);
                            Log.d("Login_Result: ", "login_result: " + login_result);

                            Intent intent = new Intent(Login.this, MainActivity.class);
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