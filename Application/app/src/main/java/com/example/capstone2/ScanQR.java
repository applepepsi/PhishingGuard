package com.example.capstone2;

import static com.example.capstone2.server_connection.Tokken_Manager.Token_Check;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone2.server_connection.Link;
import com.example.capstone2.server_connection.RetrofitBuilder;
import com.example.capstone2.server_connection.TokenCallback;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanQR extends AppCompatActivity {
    private String qr_link;
    private String scan_link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        new IntentIntegrator(this).initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent);

            } else {
                qr_link=result.getContents();
                Link link=new Link(qr_link);
                Toast.makeText (this, "Scanned: " + result.getContents (), Toast.LENGTH_LONG).show ();
                Token_Check(new TokenCallback()
                {
                    @Override
                    public void onSuccess(String result)
                    {
                        Log.d("BUTTON CLICKED", "id: " + link);
                        Post_Link(link);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                    }
                });

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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

//                      result_box = findViewById(R.id.Scan_result);
                        String QR_scan_result=response.body().string();

                        Intent intent = new Intent(ScanQR.this, Inspection_results.class);
                        intent.putExtra("scan_result",QR_scan_result);
                        intent.putExtra("input_link_QR",qr_link);
                        intent.putExtra("qr_recognize","qr");
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