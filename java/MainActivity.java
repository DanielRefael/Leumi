package com.example.danielrefael.leumi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    FirebaseDatabase database;
    DatabaseReference myUserRef;
    Button login,register;
    EditText username,password;
    Vector<User> userVec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button)findViewById(R.id.login);
        register =(Button)findViewById(R.id.register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        database = FirebaseDatabase.getInstance();
        myUserRef = database.getReference("Users");



        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        userVec = new Vector<>();
        updateVec();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.login:
                if(checkFields()) {
                    /*checkIsAdmin(username){}*/
                    /*checkIfUserInDb(username){}*/
                    if(isAdmin()){
                        Toast.makeText(this,"Welcome Admin" + username,Toast.LENGTH_SHORT).show();
                        Intent loginIn = new Intent(this, Admin_Activity.class);
                        startActivity(loginIn);
                        break;
                    }
                   else if(searchUserInVec()){
                        Toast.makeText(this,"Welcome" + username,Toast.LENGTH_SHORT).show();
                        Intent loginIn = new Intent(this, Menu.class);
                        startActivity(loginIn);
                        break;
                    }
                    else{
                        Toast.makeText(this,"Errort "+ username.getText() +" is'nt exist",Toast.LENGTH_SHORT).show();
                        username.setText("");
                        password.setText("");
                    }

                }
                else{
                    Toast.makeText(this,"Error missing password or username",Toast.LENGTH_SHORT).show();
                    username.setText("");
                    password.setText("");
                }
                break;
            case R.id.register:
                Intent in = new Intent(this,Register.class);
                startActivity(in);
                break;
        }
    }


    public boolean checkFields(){
        if(username.getText().toString().length() == 0 || password.getText().toString().length() == 0){
            return false;
        }
        return true;
    }

    public boolean searchUserInVec() {
        String name = String.valueOf(username.getText());
        String pass = String.valueOf(password.getText());
        for (int i = 0; i<userVec.size(); i++){
            if(name.equals(userVec.elementAt(i).getUsername()) && pass.equals(userVec.elementAt(i).getPassword()) ){
                return true;
            }
        }
        return false;


    }

    public String getButtonID(){

        return String.valueOf(R.id.login);
    }

    public void updateVec(){
        myUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    String name = String.valueOf(dataSnapshot1.getKey());
                    String pass = String.valueOf(dataSnapshot1.child("password").getValue());
                    String addr = String.valueOf(dataSnapshot1.child("address").getValue());
                    String email = String.valueOf(dataSnapshot1.child("email").getValue());
                    String lname = String.valueOf(dataSnapshot1.child("lastName").getValue());
                    String pname = String.valueOf(dataSnapshot1.child("privateName").getValue());
                    User user = new User(name,pass,addr,email,lname,pname);

                    userVec.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

    }

    public boolean isAdmin(){
       if(String.valueOf(username.getText()).equals("refael") && String.valueOf(password.getText()).equals("1111")){
           return true;
       }
       return false;

    }
}