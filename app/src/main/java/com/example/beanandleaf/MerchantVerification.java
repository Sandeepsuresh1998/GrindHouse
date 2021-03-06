package com.example.beanandleaf;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import database.DatabaseHelper;


public class MerchantVerification extends AppCompatActivity {

    private static int RESULT = 1;
    boolean photoSet;
    Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_verification);
        photoSet = false;

        Button buttonLoadImage = findViewById(R.id.uploadButton);
        Button buttonSubmit = findViewById(R.id.submitButton);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (ActivityCompat.checkSelfPermission(MerchantVerification.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MerchantVerification.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (photoSet) {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                    SharedPreferences.Editor editor = pref.edit();
                    DatabaseHelper db = new DatabaseHelper(getApplicationContext());

                    int userID = pref.getInt("userID", 0);
                    String storeName = pref.getString("storeName", null);
                    float lat = pref.getFloat("lat",0f);
                    float lon = pref.getFloat("lon",0f);

                    if (db.insertStore(userID, lat, lon, storeName, photo)) {
                        editor.putBoolean("addStore", true);
                        Toast toast = Toast.makeText(getApplicationContext(), storeName + " has been added. Your store will be live once admins approve your verification.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Error: your store was not added", Toast.LENGTH_LONG).show();
                    }

                    Intent navIntent = new Intent(MerchantVerification.this, Navigation.class);
                    startActivity(navIntent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please upload an image to verify your store", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT && resultCode == RESULT_OK && null != data) {
            Uri current = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor c = getContentResolver().query(current,
                    filePathColumn, null, null, null);
            c.moveToFirst();

            int columnIndex = c.getColumnIndex(filePathColumn[0]);
            String path = c.getString(columnIndex);
            c.close();

            ImageView iV = findViewById(R.id.userImage);
            photo = BitmapFactory.decodeFile(path);
            iV.setImageBitmap(photo);
            photoSet = true;
        }
    }



}
