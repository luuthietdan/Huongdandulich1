package com.example.lausecdan.huongdandulich;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SendMail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);
        Button gonder = (Button) findViewById(R.id.btn_mail);
        gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Message to be sent by mailspan>
                TextView metin = (TextView) findViewById(R.id.txt_mesaj);
                String mesaj = metin.getText().toString();

                // Subject to mail
                TextView konu = (TextView) findViewById(R.id.txt_konu);
                String konusu = konu.getText().toString();

                // To whom mail will be sent
                TextView kime = (TextView) findViewById(R.id.txt_kime);
                String alici = kime.getText().toString();

                //Lets check

                if (mesaj.isEmpty() && konusu.isEmpty() && alici.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Chưa nhập email", Toast.LENGTH_SHORT).show();
                } else {
                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.setType("message/rfc822");
                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{alici});
                    email.putExtra(Intent.EXTRA_SUBJECT, konusu);
                    email.putExtra(Intent.EXTRA_TEXT, mesaj);

                    // Let's look at an application that will send
                    try {
                        startActivity(Intent.createChooser(email, "Email"));

                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(), "Email không truy cập", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}
