package com.example.deneme;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Filtrele extends AppCompatActivity implements LocationListener {

    ArrayAdapter<String> adapter;
    String tur;
    String uzaklik;
    TextView uzak;
    EditText editText2,editText3;
    String selected;

    private LocationManager lm;
    Button btnİleri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrele);

        editText2=findViewById(R.id.editText2);
        editText3=findViewById(R.id.editText3);
        btnİleri=(Button)findViewById(R.id.button);


        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Yiyecek");
        spinnerArray.add("Giyim");
        spinnerArray.add("Kozmetik");
        spinnerArray.add("İçecek");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner sItems = (Spinner) findViewById(R.id.spinner1);
        sItems.setAdapter(adapter);



        btnİleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected= sItems.getSelectedItem().toString();
                if (selected.equals("Yiyecek")) {
                    tur="Yiyecek";
                }
                else if (selected.equals(("Giyim"))){
                    tur="Giyim";
                }
                else if(selected.equals("Kozmetik")){
                    tur="Kozmetik";
                }
                else if (selected.equals("İçecek")){
                    tur="İçecek";
                }

                CharSequence gönderilenYazi=tur;//Yazı dizisi oluşturup kullanıcının yazdığı yazıyı buraya attık.


                Intent ıntent=new Intent(Filtrele.this,Giris.class);///İntent ouşturup 2. activity'e gideceğini belirledik.

                ıntent.putExtra("anahtar",gönderilenYazi);//Gönderilecek veriyi ve bir anahtar belirledik.

                startActivity(ıntent);

               /* uzak=findViewById(R.id.txtUzak);
                CharSequence gonderilenuzak=uzak.getText().toString();
                Intent ıntent1=new Intent(Filtrele.this,Giris.class);
                ıntent1.putExtra("ana",gonderilenuzak);//Gönderilecek veriyi ve bir anahtar belirledik.
                startActivity(ıntent1);*/

            }
        });



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
        editText2.setText(Double.toString(location.getLatitude()));
        editText3.setText(Double.toString(location.getLongitude()));

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
}
