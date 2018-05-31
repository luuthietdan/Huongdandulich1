package com.example.lausecdan.huongdandulich;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.lausecdan.huongdandulich.Common.Common;
import com.example.lausecdan.huongdandulich.Model.User;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

public class Main_Travel extends AppCompatActivity {
    CardView cvdiadiem,cvanuong,cvthuexe,cvkhachsan;
    User user;
    CircleMenu circlemenu;
    //String arrayName[]={"Facebook","Twitter"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__travel);
        cvkhachsan=(CardView) findViewById(R.id.cvKhachsan);
        cvthuexe=(CardView) findViewById(R.id.cvThuexe);
        cvdiadiem=(CardView) findViewById(R.id.cvDiadiem);
        cvanuong=(CardView) findViewById(R.id.cvAnuong);
        circlemenu=(CircleMenu) findViewById(R.id.circle_menu);
        circlemenu.setMainMenu(Color.parseColor("#CDCDCD"), R.drawable.plus,R.drawable.minus)
                .addSubMenu(Color.parseColor("#258CFF"), R.drawable.gmail)
                .addSubMenu(Color.parseColor("#30A400"), R.drawable.phonecall)
                .addSubMenu(Color.parseColor("#30A400"), R.drawable.facebook)
                .addSubMenu(Color.parseColor("#30A400"), R.drawable.camera)
                .addSubMenu(Color.parseColor("#30A400"), R.drawable.twitter)


                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int index) {
                        switch (index){
                            case 0:
                                Intent mail=new Intent(Main_Travel.this,SendMail.class);
                                startActivity(mail);
                                break;
                            case 1:
                                Intent phone=new Intent(Main_Travel.this,Phone_Call.class);
                                startActivity(phone);
                                break;
                            case 2:
                                Intent face=new Intent(Main_Travel.this,Face_Book.class);
                                startActivity(face);
                                break;
                        }

                    }

                }).setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {

            @Override
            public void onMenuOpened() {}

            @Override
            public void onMenuClosed() {}

        });
        cvdiadiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent diadiem=new Intent(Main_Travel.this,Home.class);
                //Common.currentUser=user;
                startActivity(diadiem);
                //finish();
            }
        });
        cvanuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent anuong=new Intent(Main_Travel.this,AnUong.class);
                startActivity(anuong);
            }
        });
        cvthuexe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent thuexe=new Intent(Main_Travel.this,ThueXe.class);
                startActivity(thuexe);
            }
        });
        cvkhachsan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent khachsan=new Intent(Main_Travel.this,KhachSan.class);
                startActivity(khachsan);
            }
        });

    }
    public void onBackPressed() {
        if (circlemenu.isOpened())
            circlemenu.closeMenu();
        else
            finish();

    }

}
