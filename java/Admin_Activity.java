package com.example.danielrefael.leumi;

import android.content.Intent;
import android.os.Bundle;
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

/**
 * Created by daniel refael on 18/01/2018.
 */

public class Admin_Activity extends AppCompatActivity implements View.OnClickListener {

    Button add_teller, to_menu;
    EditText name_of_teller, password_teller;
    FirebaseDatabase database;
    DatabaseReference myTellerRef, myRefUser;
    Vector<User> tellerVec , userTellerVec;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);
        database = FirebaseDatabase.getInstance();
        myTellerRef = database.getReference("Tellers");
        myRefUser = database.getReference("Users");
        user = new User("","","","","","");
        tellerVec = new Vector<>();
        userTellerVec = new Vector<>();
        add_teller = (Button) findViewById(R.id.add_teller);
        to_menu = (Button) findViewById(R.id.to_menu);
        add_teller.setOnClickListener(this);
        to_menu.setOnClickListener(this);
        name_of_teller = (EditText) findViewById(R.id.add_teller_to_db);
        password_teller = (EditText) findViewById(R.id.password_teller);

        updateVecs();

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {


            case R.id.add_teller:
                if (isTellerInDb()) {
                    Toast.makeText(this, "Error Teller " + name_of_teller.getText() + " is already exist", Toast.LENGTH_SHORT).show();
                    name_of_teller.setText("");
                    break;
                }
                else {


                    writeTellerInDb();
                    Toast.makeText(this, name_of_teller.getText() + " Successfully registered", Toast.LENGTH_SHORT).show();
                    Intent loginIn = new Intent(this, Admin_Activity.class);
                    startActivity(loginIn);
                    break;

                }


            case R.id.to_menu:
                Intent in3 = new Intent(this, Menu.class);
                startActivity(in3);
                break;
        }
    }

    public boolean isTellerInDb() {

        String name = String.valueOf(name_of_teller.getText());
        for (int i = 0; i < tellerVec.size(); i++) {
            if (tellerVec.elementAt(i).getUsername().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void updateVecs() {

        myTellerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String name = String.valueOf(dataSnapshot1.getKey());
                    String pass = String.valueOf(dataSnapshot1.child("password").getValue());
                    String addr = String.valueOf(dataSnapshot1.child("address").getValue());
                    String email = String.valueOf(dataSnapshot1.child("email").getValue());
                    String lname = String.valueOf(dataSnapshot1.child("lastName").getValue());
                    String pname = String.valueOf(dataSnapshot1.child("privateName").getValue());
                     user = new User(name, pass, addr, email, lname, pname);
                    tellerVec.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });


    }

    public boolean isUserInDb(){
        for (int i = 0 ; i < userTellerVec.size(); i++){
            if(userTellerVec.elementAt(i).getUsername().equals(name_of_teller.getText())){
                name_of_teller.setText(userTellerVec.elementAt(i).getUsername());
                return true;
            }

        }
        return false;
    }


    public void writeTellerInDb() {

              myTellerRef.child("name").setValue(String.valueOf(name_of_teller.getText()));
              myTellerRef.child("password").setValue(String.valueOf(password_teller.getText()));


      }


}
