package com.example.deneme;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Patterns;
import android.location.LocationManager;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements LocationListener{


    private FirebaseAuth mAuth;
    EditText etAd, etPass;
    Button btnKaydol, btnGiris;
    private LocationManager lm;
    // DatabaseReference reff;
    // member Member;
    //  ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAuth = FirebaseAuth.getInstance();
        Toast.makeText(MainActivity.this, "Veritabanına Bağlanıldı!", Toast.LENGTH_LONG).show();
        etAd = (EditText) findViewById(R.id.etAd);
        etPass = (EditText) findViewById(R.id.etPass);
        btnKaydol = (Button) findViewById(R.id.btnKaydol);
        btnGiris = (Button) findViewById(R.id.btnGiris);
        //   progressBar = (ProgressBar) findViewById(R.id.progressbar);
        // Member=new member();
        //  reff=FirebaseDatabase.getInstance().getReference().child("member");

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
        } else {

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
        }


    }


  @Override
    public void onLocationChanged(Location location) {
      System.out.println("LATITUDE:" + location.getLatitude());
      System.out.println("LONGTITUDE:" + location.getLongitude());
        /*latituteField.setText(Double.toString(location.getLatitude()));
        longitudeField.setText(Double.toString(location.getLongitude()));
        gpslat = location.getLatitude();
        gpslong = location.getLongitude();*/
  }

      @Override
      public void onProviderDisabled (String provider){
          Log.d("Latitude", "disable");
      }

      @Override
      public void onProviderEnabled (String provider){
          Log.d("Latitude", "enable");
      }

      @Override
      public void onStatusChanged (String provider,int status, Bundle extras){
          Log.d("Latitude", "status");
      }



    public void signIn(View view){



        mAuth.signInWithEmailAndPassword(etAd.getText().toString(),etPass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
            Toast.makeText(MainActivity.this, "HOŞGELDİNİZ !", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),Filtrele.class);
            startActivity(intent);
        }
    }
}).addOnFailureListener(this, new OnFailureListener() {
@Override
public void onFailure(@NonNull Exception e) {
        Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();


        }

        });
        }


    public void signup(View view) {
        mAuth.createUserWithEmailAndPassword(etAd.getText().toString(),etPass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "KULLANICI OLUŞTURULDU!", Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(getApplicationContext(),Giris.class);
                            startActivity(intent);
                        }
                    }
                } ).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,"BU KULLANICI ZATEN MEVCUT !",Toast.LENGTH_LONG).show();


            }
        });
    } 
 }
