package com.example.capstone2.server_connection;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.capstone2.Link_Insert;
import com.example.capstone2.Login;
import com.example.capstone2.utils.Utils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import okhttp3.ResponseBody;

public class Tokken_Manager
{
    public static void Token_Check(TokenCallback callback) {
        String token = Utils.getAccessToken(null);
        Call<ResponseBody> call = RetrofitBuilder.getApi().getWithBearerToken("Bearer " + token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String user_check = response.body().string();
                        if (user_check.equals("0")) {
                            callback.onFailure("유저 인증 실패");
                        } else if (user_check.equals("1")) {
                            callback.onSuccess("1");
                        }
                    } catch (IOException e) {
                        callback.onFailure("IOException occurred");
                    }
                } else {
                    callback.onFailure("Response unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure("Connection failure: " + t.getLocalizedMessage());
            }
        });
    }
}
