package com.emreedemir.travelapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.emreedemir.travelapplication.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private FirebaseUser user;


    private EditText email;
    private EditText password;
    private EditText passwordRety;

    private Button signUpButton;

    private DatabaseReference RootRef;

    private Button signInActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mAuth = FirebaseAuth.getInstance();

        user =mAuth.getCurrentUser();
        RootRef = FirebaseDatabase.getInstance().getReference();

        initiliazeUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(user !=null)
        {
            sendToUserActivity();
        }
    }

    void initiliazeUI()
    {
        email=findViewById(R.id.userEmail_su);
        password=findViewById(R.id.userPassword_su);
        passwordRety=findViewById(R.id.userPasswordRety_su);

        signUpButton=findViewById(R.id.signUpButton);

        signInActivityButton=findViewById(R.id.signInPageButton_su);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (isVerifiyed())
                {
                    signUp();
                }

                else
                {
                    Toast.makeText(SignUpActivity.this,"Please fill corretyl",Toast.LENGTH_LONG).show();
                }
            }
        });

        signInActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                sendToSignInActivity();
            }
        });
    }

    User getUser()
    {
       return new User(email.getText().toString(),password.getText().toString());
    }

    boolean isVerifiyed()
    {
        if(TextUtils.isEmpty(email.getText().toString()))
        {
            return  false;
        }
        else if(TextUtils.isEmpty(password.getText().toString()))
        {
            return false;
        }
        else if(!password.getText().toString().equals(passwordRety.getText().toString()))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    void signUp()
    {
        final User user =getUser();

        mAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword())
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            String currentUserID = mAuth.getCurrentUser().getUid();
                            RootRef.child("Users").child(currentUserID).setValue("");

                            sendToUserActivity();
                        }
                        else
                        {
                            Toast.makeText(SignUpActivity.this,"cant sign up",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    void sendToUserActivity()
    {
        Intent intent = new Intent(SignUpActivity.this,UserActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    void sendToSignInActivity()
    {
        Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
        startActivity(intent);
    }
}


