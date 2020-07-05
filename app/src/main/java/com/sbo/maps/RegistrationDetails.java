package com.sbo.maps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationDetails extends AppCompatActivity {
    private EditText First_Name, Surname, Email_Address, UserPassword;
    private Button LOGIN_PAGE, RegistrationUser_Button;
    //Firebase stuff
    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("UserData");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_details);

        INITIALIZE();
        REGISTRATION_BUTTON();
        TAKES_YOU_TO_LOGIN();
    }
    private void INITIALIZE(){
        First_Name = findViewById(R.id.firstNameEditText);
        Surname = findViewById(R.id.suNameEditText);
        Email_Address = findViewById(R.id.emailEditText);
        UserPassword = findViewById(R.id.passwordEditText);
        LOGIN_PAGE = findViewById(R.id.LogInBtn);
        RegistrationUser_Button = findViewById(R.id.RegistarUserBtn);
        //firebase authentication
        mAuth = FirebaseAuth.getInstance();
    }
    private void TAKES_YOU_TO_LOGIN(){
        LOGIN_PAGE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LogInPage.class);
                startActivity(intent);
            }
        });
    }
    private void REGISTRATION_BUTTON(){
        RegistrationUser_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                REGISTER_NEW_USER();
            }
        });
    }
    //signs up the new user
    private void REGISTER_NEW_USER(){
        String email = Email_Address.getText() + "";
        String password = UserPassword.getText() + "";

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegistrationDetails.this, "createUserWithEmail: success", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(RegistrationDetails.this, "createUserWithEmail:failure", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        Toast.makeText(this, "User Added to Database", Toast.LENGTH_SHORT).show();
        REGISTER_NEW_USER_FULLY();
    }
    private void REGISTER_NEW_USER_FULLY(){
        RegistrationData RegData = new RegistrationData();
        String firstName, surName, email, password;

        firstName = First_Name.getText() + "";
        surName = Surname.getText() + "";
        password = UserPassword.getText() + "";
        email = Email_Address.getText() + "";

        RegData.setFirst_name(firstName);
        RegData.setSurname(surName);
        RegData.setEmail(email);
        RegData.setPassword(password);

        myRef.push().setValue(RegData);
        Toast.makeText(this, "User Added to Database", Toast.LENGTH_SHORT).show();
    }
}