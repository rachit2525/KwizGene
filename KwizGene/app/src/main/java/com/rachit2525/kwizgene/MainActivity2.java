package com.rachit2525.kwizgene;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//import com.rachit2525.kwizgene.Teacher.ui.login.LoginActivity;

public class MainActivity2 extends AppCompatActivity {

    Button stuLogin;
    Button teachLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        stuLogin = findViewById(R.id.StudentBtn);
        teachLogin = findViewById(R.id.teacherBtn);


    }
    public void loginTeacher(View view) {
//        Intent i=new Intent(this,
//                teacherLoginActivity.class);
//        //Intent is used to switch from one activity to another.
//
//        startActivity(i);
    }
    public void loginStudent(View view) {
//        Intent i=new Intent(this,
//                studentLoginActivity.class);
//        //Intent is used to switch from one activity to another.
//
//        startActivity(i);
    }
}