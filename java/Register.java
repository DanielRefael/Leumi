package com.example.danielrefael.leumi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
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


public class Register extends AppCompatActivity implements View.OnClickListener {
    // Write a message to the database

    //    String username;
    EditText username, password, pName, lName, myAddr, mail;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Button submit, upload;
    Vector<User> userVec;
    User user;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        database = FirebaseDatabase.getInstance();

        user = new User("", "", "", "", "", "");
        userVec = new Vector<>();
        myRef = database.getReference("Users");
        username = (EditText) findViewById(R.id.reg_username);
        password = (EditText) findViewById(R.id.reg_password);
        myAddr = (EditText) findViewById(R.id.reg_address);
        mail = (EditText) findViewById(R.id.reg_email);
        lName = (EditText) findViewById(R.id.last_name);
        pName = (EditText) findViewById(R.id.private_name);
        submit = (Button) findViewById(R.id.upload);
        submit.setOnClickListener(this);
        upload = (Button) findViewById(R.id.submit);
        upload.setOnClickListener(this);
        updateVec();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.upload:
                Intent Upload = new Intent(this, add_pic.class);
                startActivity(Upload);
                break;
            case R.id.submit:
                if (checkFields()) {
                  if(searchUserInVec()){
                      Toast.makeText(this, "Error --> user " + username.getText()+ " already exist ", Toast.LENGTH_SHORT).show();
                      Intent backAgain = new Intent(this, Register.class);
                      startActivity(backAgain);
                      break;
                  }

                  else {
                        setUserInDb();
                        Toast.makeText(this, username.getText()+" Registered Successfully", Toast.LENGTH_SHORT).show();
                        Intent back = new Intent(this, MainActivity.class);
                        startActivity(back);
                        break;
                    }
                }
                else {
                    Toast.makeText(this, "Error enter username and password   ", Toast.LENGTH_SHORT).show();
                    username.setText("");
                    password.setText("");
                    break;
                }
        }
    }


    public boolean checkFields() {
        if (username.getText().equals("") || password.getText().equals("")) {
            return false;
        }
        return true;
    }

    public void setUserInDb() {
        String name = String.valueOf(username.getText());
        String pass = String.valueOf(password.getText());
        String privateName = String.valueOf(pName.getText());
        String lastName = String.valueOf(lName.getText());
        String email = String.valueOf(mail.getText());
        String addr = String.valueOf(myAddr.getText());
        user = new User(name, pass, privateName, lastName, email, addr);
        myRef.child(name).setValue(user);


    }

    public boolean searchUserInVec() {
        String name = String.valueOf(username.getText());
        for (int i = 0; i<userVec.size(); i++){
            if(name.equals(userVec.elementAt(i).getUsername())){
                return true;
            }
        }
        return false;


    }

    public void updateVec(){
        myRef.addValueEventListener(new ValueEventListener() {
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

}