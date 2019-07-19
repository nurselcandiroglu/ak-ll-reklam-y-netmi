package com.example.deneme;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Giris<Firebase> extends AppCompatActivity implements LocationListener {
    DatabaseReference ref;
    TextView textViewA;
    ArrayAdapter<String>adapter;
    LinearLayout ln;
    Context jon;


    FirebaseDatabase firebaseDatabase;
    LocationManager lm;
    private FirebaseAuth mAuth;
    double lat1,lon1,el1,el2,lat2,lon2;

    @Override
    public void onLocationChanged(Location location) {

        System.out.println("LATITUDE:"+location.getLatitude());
        System.out.println("LONGTITUDE:"+location.getLongitude());
        lat1=location.getLatitude();
        lon1=location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu1){
            Intent intent=new Intent(getApplicationContext(),Profil.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    String tur;
    String uzak;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        textViewA=findViewById(R.id.textViewA);
         ln=findViewById(R.id.l1);
         jon=this;
        firebaseDatabase=FirebaseDatabase.getInstance();
        ref=firebaseDatabase.getReference();



        Bundle gelenVeri=getIntent().getExtras();
        tur=gelenVeri.getCharSequence("anahtar").toString();

        System.out.println("aha bu"+tur);


       /* Bundle gelenVeri2=getIntent().getExtras();
        uzak=gelenVeri2.getCharSequence("ana").toString();
        System.out.println("bak bu "+uzak);  */
        getData();






        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
        } else {

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
        }

        final int R = 6371; // Radius of the earth


        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        double uzaklik= Math.sqrt(distance);


    }
    public void getData(){


        DatabaseReference newr=firebaseDatabase.getReference("Magaza");
        newr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    System.out.println("giriyo");
                    HashMap<String, Object> hashMap = (HashMap<String, Object>) ds.getValue();
                    String kontrol=(String)hashMap.get("Icerik");
                    System.out.println("aha gelen:"+kontrol+" aha secilen:" +tur);
                    if (kontrol.equals(tur)) {
                        System.out.println("girdi");
                        TextView newTV = new TextView(jon);
                        newTV.setText((String) hashMap.get("Ad"));
                        ln.addView(newTV);
                        TextView newTV1 = new TextView(jon);
                        newTV1.setText((String) hashMap.get("Icerik"));
                        ln.addView(newTV1);
                        TextView newTV2 = new TextView(jon);
                        newTV2.setText((String) hashMap.get("Lat"));
                        ln.addView(newTV2);
                        TextView newTV3 = new TextView(jon);
                        newTV3.setText((String) hashMap.get("Long"));
                        ln.addView(newTV3);
                        TextView newTV4 = new TextView(jon);
                        newTV4.setText((String) hashMap.get("Sure"));
                        ln.addView(newTV4);
                        TextView newTV5 = new TextView(jon);
                        ln.addView(newTV5);
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}