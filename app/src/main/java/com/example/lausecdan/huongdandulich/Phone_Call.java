package com.example.lausecdan.huongdandulich;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class Phone_Call extends AppCompatActivity {
    private static final int REQUEST_CALL=1;
    private EditText edtNumber;
   /* ImageView imgcall;
    EditText edtnumber;
    String sNum;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone__call);
  /*      imgcall=(ImageView) findViewById(R.id.image_call);
        edtnumber=(EditText) findViewById(R.id.edtPhone);

    }
    public void btnClick(View v){
        Intent i=new Intent(Intent.ACTION_CALL);
        sNum=edtnumber.getText().toString();
        if(sNum.trim().isEmpty()){
            i.setData(Uri.parse("tell:7788994455"));

        }
        else {
            i.setData(Uri.parse("tel:"+sNum));

        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"Please permission to call",Toast.LENGTH_SHORT).show();
            requestPermission();
        }
        else {
            startActivity(i);
        }

    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new  String[]{Manifest.permission.CALL_PHONE},1);
    }*/
        edtNumber=findViewById(R.id.editText);
        ImageView imgcall=findViewById(R.id.image_call);
        imgcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });
    }
    private void makePhoneCall(){
        String number=edtNumber.getText().toString();
        if(number.trim().length()>0){
            if (ContextCompat.checkSelfPermission(Phone_Call.this, Manifest.permission.CALL_PHONE) !=PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Phone_Call.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }
            else {
                String dial="tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dial)));
            }
        }
        else {
            Toast.makeText(Phone_Call.this,"Nhập Số Điện Thoại",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CALL)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }
            else {
                Toast.makeText(this,"Permission Dented",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
