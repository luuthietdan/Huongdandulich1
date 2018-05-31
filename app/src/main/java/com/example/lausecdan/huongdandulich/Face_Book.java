package com.example.lausecdan.huongdandulich;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Face_Book extends AppCompatActivity {
    Button btnface;
    String uriString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face__book);
        btnface=(Button) findViewById(R.id.btnface);

        btnface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent=new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                uriString="http://www.opencv-tutorial-codes.blogspot.in";
                sharingIntent.putExtra(Intent.EXTRA_TEXT,uriString);
                sharingIntent.setPackage("com.facebook.katana");
                startActivity(sharingIntent);
            }
        });
    }

}
