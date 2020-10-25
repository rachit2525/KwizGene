package com.rachit2525.kwizgene;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rachit2525.kwizgene.teacher.TeacherMainActivity;

public class LoginActivity extends AppCompatActivity {

    // initialization of buttons, etc
    EditText txtLoginEmail, txtLoginPass;
//    RadioButton rbStudent, rbTeacher;
    Button btnLogin;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String TAG = " Login Activity : ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtLoginEmail = (EditText)findViewById(R.id.txtLoginEmail);
        txtLoginPass = (EditText)findViewById(R.id.txtLoginPass);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailLogin = txtLoginEmail.getText().toString();
                String passLogin = txtLoginPass.getText().toString();

                if(TextUtils.isEmpty(emailLogin)) {
                    Toast.makeText(LoginActivity.this, "Please enter registered email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passLogin)) {
                    Toast.makeText(LoginActivity.this, "Please enter correct password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(emailLogin, passLogin)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    Toast.makeText(LoginActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startActivity(new Intent(getApplicationContext(), TeacherMainActivity.class));
//                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Incorrect Password!",
                                            Toast.LENGTH_SHORT).show();
//                                    updateUI(null);
                                }

                                // ...
                            }
                        });

            }
        });

    }

    public void register_form(View view) {
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }
}