package com.example.danielrefael.leumi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Saggi on 05/12/2017.
 */

public class Readme extends AppCompatActivity implements View.OnClickListener {


    Button getBackToMenu;
    EditText myInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readme);
        getBackToMenu = (Button)findViewById(R.id.back_to_menu);
        getBackToMenu.setOnClickListener(this);
        myInfo = (EditText)findViewById(R.id.app_info);
        myInfo.setText("The application provides a response to the registration of a new bank account as well as handles existing customers, for example updating information, and the application allows you to see where you are. ");
        myInfo.setEnabled(false);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.back_to_menu:
                Intent info = new Intent(this, Menu.class);
                startActivity(info);
                break;
        }
    }
}
