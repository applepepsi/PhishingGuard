package com.example.capstone2;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class Inspection_results extends AppCompatActivity {
    Button scan_stop;
    Button Join_link;
    TextView input_link;
    TextView result;

    String qr_recognize=null;
    String link_insert_recognize=null;

    String link_scan_result;
    String QR_link_result;
    String input_link_QR_String;
    String input_link_insert_String;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_result);

        Intent intent = getIntent();

        link_scan_result = intent.getStringExtra("scan_result");    //QR이나 직접넣은 링크에서 받은 결과값 0,1

        input_link_QR_String=intent.getStringExtra("input_link_QR");    //qr코드로 인식한 링크
        input_link_insert_String=intent.getStringExtra("input_link_insert");    //직접넣은 링크
        result = (TextView) findViewById(R.id.scan_result);
        input_link = (TextView) findViewById(R.id.input_link);

        qr_recognize=intent.getStringExtra("qr_recognize");
        link_insert_recognize=intent.getStringExtra("link_insert_recognize");


        scan_stop = (Button) findViewById(R.id.close);      //접속하지 않고 돌아가기
        Join_link = (Button) findViewById(R.id.join_site);  //접속하기
        View result_view = findViewById(R.id.result_view);


//qr검사인지 링크를 직접넣는 방식인지 식별을 위해 식별값을 넣음(직접넣기link_insert,qr)
        if (qr_recognize != null && qr_recognize.equals("qr")) {
            input_link.setText(input_link_QR_String);
            Log.d("DEBUG", "QR recognized." + input_link_QR_String);
        }

        if (link_insert_recognize != null && link_insert_recognize.equals("link_insert")) {
            input_link.setText(input_link_insert_String);
            Log.d("DEBUG", "link_insert recognized." + link_insert_recognize);
        }


        if (link_scan_result.equals("0"))
        {

            result_view.setBackgroundResource(R.drawable.safe);
            result.setText("해당 링크는 정상사이트일 확률이 높습니다.");
        } else if (link_scan_result.equals("1"))
        {
            result_view.setBackgroundResource(R.drawable.fail);
            result.setText("해당 링크는 피싱사이트일 확률이 높습니다. " +
                    "\n주의하세요!");
        } else {
            System.out.println("올바른 값이 아닙니다.");
        }
        Log.d("RESPONSE: ", "id: " + link_scan_result);

        scan_stop.setOnClickListener(view ->
        {
            Intent mainback = new Intent(Inspection_results.this, MainActivity.class);
            startActivity(mainback);
        });

        Join_link.setOnClickListener(view->
        {
            String linkText = input_link.getText().toString();
            if (!linkText.isEmpty()) {
                Intent join_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkText)); // qr주소를 인터넷으로 연결
                startActivity(join_intent);
            }
        });

    }
}
