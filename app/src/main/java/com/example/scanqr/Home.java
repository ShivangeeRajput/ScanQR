package com.example.scanqr;

import static com.example.scanqr.R.menu.menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Home extends AppCompatActivity  {

    Button qrbtn;
    ImageView pic;
    TextView qrtext;
    TextView wel;

    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        FirebaseFirestore db = FirebaseFirestore.getInstance();

        qrbtn=findViewById(R.id.qrbtn);
        qrtext=findViewById(R.id.qrtext);
        pic=findViewById(R.id.pic);
        wel=findViewById(R.id.wel);

        qrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(Home.this);
                intentIntegrator.setPrompt("Scan a barcode or QR Code");
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult intentResult=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (intentResult != null){
            String contents=intentResult.getContents();
            if (contents != null){
                qrtext.setText(intentResult.getContents());
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:{
                mAuth.signOut();
                return true;
               Intent intent=new Intent(getApplicationContext(),LogIn.class);
               startActivity(intent);

               break;

            }
        }

        return super.onOptionsItemSelected(item);
    }
}