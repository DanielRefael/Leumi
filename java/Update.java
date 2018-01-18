package com.example.danielrefael.leumi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Update extends AppCompatActivity implements View.OnClickListener{
    Button update_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        update_me = (Button)findViewById(R.id.update_me);
        update_me.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.update_me:
                Intent updateNow = new Intent(this, Menu.class);
                startActivity(updateNow);
                Toast.makeText(this,"Details Update Successfully",Toast.LENGTH_SHORT).show();


        }
    }
}
