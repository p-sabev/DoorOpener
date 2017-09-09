package com.example.malkyatmuk.dooropener;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ID_hardware_divice extends AppCompatActivity {
TextView txt;
    String s="NOHASH";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_hardware_divice);

         txt= (TextView) findViewById(R.id.textpass);
        s= getIntent().getStringExtra("passhesh");
        txt.setText(s);

    }

}
