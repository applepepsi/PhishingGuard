package com.example.capstone2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.capstone2.utils.Utils;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button scanQRBtn;
    private Button scanlinkBtn;
//    private Button loginBtn;
    String val = null;
    TextView link_test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        scanQRBtn = (Button) findViewById(R.id.scanQR);
        scanQRBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, ScanQR.class);
                startActivity(intent);
            }
        });
        scanlinkBtn = (Button) findViewById(R.id.link_scan_btn);
        scanlinkBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, Link_Insert.class);
                startActivity(intent);
            }
        });
        TextView logoutBtn=findViewById(R.id.logout);
        logoutBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Utils.clearToken();
                Log.d("RESPONSE","tokken: "+Utils.getAccessToken(null));
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);

            }
        });
    }

}