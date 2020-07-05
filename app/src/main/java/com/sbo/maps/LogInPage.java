package com.sbo.maps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInPage extends AppCompatActivity {
    private EditText emailText, passwordText;
    private TextView LoginUser;
    //Firebase Stuff
    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("UserData");
    private String currentUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        mAuth = FirebaseAuth.getInstance();
        INITIALIZE();
        LOGIN();
    }
    private void INITIALIZE(){
        emailText = findViewById(R.id.editTextTextEmailAddress);
        passwordText = findViewById(R.id.editTextTextPassword);
        LoginUser = findViewById(R.id.LoginTextView);
    }
        private void LOGIN() {
        LoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SIGN_IN();
            }
        });
    }
    //Signs into the database by using email and password and when signed in goes in the database again to get the specific user data
    private void SIGN_IN(){
        mAuth.signInWithEmailAndPassword(emailText.getText() + "", passwordText.getText()+ "")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LogInPage.this, "signInWithEmail:success", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = mAuth.getCurrentUser();
                            currentUserEmail = user.getEmail();
                            myRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        RegistrationData data = dataSnapshot.getValue(RegistrationData.class);

                                        if(data.getEmail().equals(currentUserEmail)){
                                            Toast.makeText(LogInPage.this, "You are logged in go search for a location", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                                            startActivity(intent);
                                        }else {
                                            Toast.makeText(LogInPage.this, "There is nothing in the there", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LogInPage.this, "signInWithEmail:failure", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}