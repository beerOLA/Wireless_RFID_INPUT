package com.example.rfid_input;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.ktx.Firebase;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView timeview;
    TextView nameview;
    EditText idfield;
    ImageView imageView;

    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idfield = (EditText) findViewById(R.id.idfield);
        idfield.setInputType(0);
        idfield.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                input();

            }
        });
    }




    public void input(){
        timeview = (TextView) findViewById(R.id.timeview);
        nameview = (TextView) findViewById(R.id.nameview);
        imageView = (ImageView) findViewById(R.id.imageView);
        String IDdata = idfield.getText().toString();
       // Log.d("ddddddddddddddddddd", IDdata);
        final String currentTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
        ref  = FirebaseDatabase.getInstance().getReference("student").child(IDdata);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Getdata getdata = (Getdata) dataSnapshot.getValue(Getdata.class);
                if (getdata == null) {
                    idfield.setText("");
                    new AlertDialog.Builder(MainActivity.this).setTitle("ไม่พบข้อมูล").setCancelable(false).setNegativeButton("ปิด", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create().show();
                    return;
                }
                else{
                    String parentdata = getdata.getParent();
                    String namedata = getdata.getName();
                    String link = getdata.getPic();
                    Picasso.get().load(link).into(imageView);
                    nameview.setText(namedata);
                    timeview.setText(currentTime);
                    idfield.setText("");
                    idfield.requestFocus();
                    settime(namedata,IDdata,currentTime,parentdata);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void settime(String namedata,String IDdata, String currentTime,String parentdata){
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

        ref = FirebaseDatabase.getInstance().getReference("ATD");
        ref.child("DATE").child(currentDate).child(IDdata).setValue(currentTime);
        ref.child(IDdata).child(currentDate).child(currentTime).setValue(currentTime);
        ref = FirebaseDatabase.getInstance().getReference("user").child(parentdata);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Getdata getdata = (Getdata) snapshot.getValue(Getdata.class);
                String phonedata = getdata.getPhone();
                Log.d("ddddddddddddddddddd", phonedata);
                smssent(namedata,IDdata,currentDate,currentTime,parentdata,phonedata);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ;
    }
    public void smssent(String namedata, String IDdata, String currentDate, String currentTime, String parentdata,String phonedata){
        SmsManager mysmsManager = SmsManager.getDefault();

//        Log.d("ddddddddddddddddddd", parentdata);
  //      Log.d("ddddddddddddddddddd", namedata);
    //    Log.d("ddddddddddddddddddd", currentTime);
      //  Log.d("ddddddddddddddddddd", currentDate);
       // Log.d("ddddddddddddddddddd", phonedata);
        String message = "Dear  "+parentdata+ "Your child    " +namedata+" ID  "+ IDdata +" scan at  "+ currentTime+ "    " + currentDate;
        mysmsManager.sendTextMessage(phonedata, null,message, null,null);
    }




}