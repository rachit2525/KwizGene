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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rachit2525.kwizgene.student.StudentMainActivity;
import com.rachit2525.kwizgene.student.StudentModel;
import com.rachit2525.kwizgene.teacher.TeacherMainActivity;

public class SignUpActivity extends AppCompatActivity {

    // initialization of buttons, etc
    EditText txtName, txtEmail, txtPass, txtConfirmPass;
    RadioButton rbStudent, rbTeacher;
    Button btnRegister;

    String stud = "student";
    String teach = "teacher";


    String isTeacher;

    private FirebaseAuth mAuth;

    DatabaseReference databaseReferenceStudent, databaseReferenceTeacher;
//    FirebaseDatabase firebaseDatabase;

    private String TAG = " SignUp Activity : ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //declarations of buttons, textFields, etc
        txtName = (EditText)findViewById(R.id.txtName);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtPass = (EditText)findViewById(R.id.txtPass);
        txtConfirmPass = (EditText)findViewById(R.id.txtConfirmPass);
        rbStudent = (RadioButton) findViewById(R.id.rbStudent);
        rbTeacher = (RadioButton)findViewById(R.id.rbTeacher);
        btnRegister = (Button)findViewById(R.id.btnRegister);

        // Check for if user is a student or teacher



//        rbTeacher.setActivated(false);
//        rbStudent.setActivated(false);
        // by default if no radio button is checked the student role is assigned to the user
            isTeacher = "teacher";

        // making object of firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // creating references for firebase database for Students
//        databaseReferenceStudent = FirebaseDatabase.getInstance().getReference("Student");

        // creating references for firebase database for Teachers
//        databaseReferenceTeacher = FirebaseDatabase.getInstance().getReference("Teacher");

        btnRegister.setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View view) {
//                   Toast.makeText(SignUpActivity.this, isTeacher, Toast.LENGTH_SHORT).show();
                   final String email = txtEmail.getText().toString();
                   String pass = txtPass.getText().toString();
                   final String fullName = txtName.getText().toString();
                   String cnfPass = txtConfirmPass.getText().toString();
                   if (rbTeacher.isChecked()) {
                       isTeacher = teach;
                       Toast.makeText(SignUpActivity.this, "Teacher", Toast.LENGTH_SHORT).show();
                   }
                   if (rbStudent.isChecked()) {
                       isTeacher = stud;
                       Toast.makeText(SignUpActivity.this, "Student", Toast.LENGTH_SHORT).show();
                   }

                   if (TextUtils.isEmpty(fullName)) {
                       Toast.makeText(SignUpActivity.this, "Please enter your Name!", Toast.LENGTH_SHORT).show();
                       return;
                   }
                   if (TextUtils.isEmpty(email)) {
                       Toast.makeText(SignUpActivity.this, "Please enter an email!", Toast.LENGTH_SHORT).show();
                       return;
                   }
                   if (TextUtils.isEmpty(pass)) {
                       Toast.makeText(SignUpActivity.this, "Please enter a Password!", Toast.LENGTH_SHORT).show();
                       return;
                   }
                   if (TextUtils.isEmpty(cnfPass) || !cnfPass.equals(pass)) {
                       Toast.makeText(SignUpActivity.this, "Please confirm the Password!", Toast.LENGTH_SHORT).show();
                       return;
                   }
                   System.out.println("/////**********************************************////////////////////////////////********************//////////////////////////////////////isTeacher = " + isTeacher);
                   if(cnfPass.equals(pass)) {
                       mAuth.createUserWithEmailAndPassword(email, pass)
                               .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                   @Override
                                   public void onComplete(@NonNull Task<AuthResult> task) {
                                       if (task.isSuccessful()) {

                                           StudentModel info = new StudentModel(fullName, email, isTeacher);
                                           if(isTeacher.equals(stud)) {
                                               FirebaseDatabase.getInstance().getReference("Student")
                                                       .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                       .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                   @Override
                                                   public void onComplete(@NonNull Task<Void> task) {
                                                       Toast.makeText(SignUpActivity.this, "Successfully Registered as Student!", Toast.LENGTH_SHORT).show();
                                                       startActivity(new Intent(getApplicationContext(), StudentMainActivity.class));
                                                   }
                                               });
                                           }
                                           else if(isTeacher.equals(teach)) {
                                               FirebaseDatabase.getInstance().getReference("Teacher")
                                                       .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                       .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                   @Override
                                                   public void onComplete(@NonNull Task<Void> task) {
                                                       Toast.makeText(SignUpActivity.this, "Successfully Registered as Teacher!", Toast.LENGTH_SHORT).show();
                                                       startActivity(new Intent(getApplicationContext(), TeacherMainActivity.class));
                                                   }
                                               });
                                           }
                                       } else {
                                           Toast.makeText(SignUpActivity.this, "Registration Failed! Try Again.", Toast.LENGTH_SHORT).show();
                                           Log.i(TAG, "onComplete: ");
                                       }

                                       // ...
                                   }
                               });
                   }
               }
           }
        );

    }
}