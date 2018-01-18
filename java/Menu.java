package com.example.danielrefael.leumi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity implements View.OnClickListener{

    Button readMe,updateData,map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        readMe = (Button)findViewById(R.id.read_me);
        updateData = (Button)findViewById(R.id.update_data);
        map = (Button)findViewById(R.id.map);
        readMe.setOnClickListener(this);
        updateData.setOnClickListener(this);
        map.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){

            case R.id.map:
                Intent myMap = new Intent(this,MapsActivity.class);
                startActivity(myMap);
                break;

            case R.id.update_data:
                Intent update = new Intent(this, Update.class);
                startActivity(update);
                break;

            case R.id.read_me:
                Intent info = new Intent(this, Readme.class);
                startActivity(info);
                break;
        }

    }
}
