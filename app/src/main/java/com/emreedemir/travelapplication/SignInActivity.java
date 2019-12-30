package com.emreedemir.travelapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.emreedemir.travelapplication.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private Button signInButton;
    private Button signUpActivityButton;

    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);

        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

        initiliazeUI();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        if(user!=null)
        {
            sendToUserActivity();
        }
    }

    void initiliazeUI()
    {
        emailEditText=findViewById(R.id.userEmail_si);
        passwordEditText =findViewById(R.id.userPassword_si);

        signInButton =findViewById(R.id.signInButton_si);
        signUpActivityButton=findViewById(R.id.signUpActivity_si);

        signInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(isVerify())
                    signIn();
                else
                    Toast.makeText(SignInActivity.this,"AnyUser cant find,rety",Toast.LENGTH_LONG).show();

            }
        });

        signUpActivityButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sendToSignUpActivity();
            }
        });
    }

    void signIn()
    {
        User user =getUser();

        mAuth.signInWithEmailAndPassword(user.getEmail(),user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            sendToUserActivity();
                        }
                        else
                        {
                            Toast.makeText(SignInActivity.this,"please write corret input",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    User getUser()
    {
       return  new User(emailEditText.getText().toString(),passwordEditText.getText().toString());
    }

    boolean isVerify()
    {
        String mail =emailEditText.getText().toString();
        String password =passwordEditText.getText().toString();

        if(TextUtils.isEmpty(mail))
        {
            return false;
        }
        else if(TextUtils.isEmpty(password))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    void sendToUserActivity()
    {
        Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    void sendToSignUpActivity()
    {
        Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
        startActivity(intent);
    }
}
