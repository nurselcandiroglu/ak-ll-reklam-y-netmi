package com.example.deneme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profil extends AppCompatActivity {
    EditText e1,e2;
    FirebaseAuth auth;
    ProgressDialog d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        d=new ProgressDialog(this);
        e1=(EditText)findViewById(R.id.etYeni);
        e2=(EditText)findViewById(R.id.etAd);
        auth=FirebaseAuth.getInstance();
        e2.setText(auth.getCurrentUser().getEmail());

    }
    public void change(View v){
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){

            d.setMessage("changing pass, please wait");
            d.show();
            user.updatePassword(e1.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Profil.this,"Şifre Değiştirildi!",Toast.LENGTH_LONG).show();


                            }
                            else{
                                d.dismiss();
                                Toast.makeText(Profil.this,"Şifre Değiştirilemedi !",Toast.LENGTH_LONG).show();
                            }}
                    });
        }
        else{
            d.setMessage("error!!");
        }
    }



}