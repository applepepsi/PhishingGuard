package com.example.capstone2;

import static com.example.capstone2.server_connection.Tokken_Manager.Token_Check;

import com.example.capstone2.server_connection.Tokken_Manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.capstone2.server_connection.Link;
import com.example.capstone2.server_connection.RetrofitBuilder;
import com.example.capstone2.server_connection.TokenCallback;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Link_Insert extends AppCompatActivity
{
    private String scan_link = "";
    TextView result_box;
    private String token_check="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_link);

        Button button = findViewById(R.id.scan_link_btn);
        EditText link_insert_box = findViewById(R.id.link_insert);


        button.setOnClickListener(view -> {
            Token_Check(new TokenCallback() {
                @Override
                public void onSuccess(String result)
                {
                    scan_link = link_insert_box.getText().toString();
                    Link link = new Link(scan_link);
                    Log.d("BUTTON CLICKED", "id: " + link);
                    Post_Link(link);

                }

                @Override
                public void onFailure(String errorMessage) {
                }
            });
        });
    }






    private void Post_Link(Link link) {
        Call<ResponseBody> call = RetrofitBuilder.getApi().getLinkResponse(link);
        call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if (response.isSuccessful()) {
                    try {
                        String scan_result=response.body().string();
                        Log.d("RESPONSE","통신 성공");
                        Intent intent = new Intent(Link_Insert.this, Inspection_results.class);
                        intent.putExtra("scan_result",scan_result);
                        intent.putExtra("input_link_insert",scan_link);
                        intent.putExtra("link_insert_recognize","link_insert");
                        startActivity(intent);


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



