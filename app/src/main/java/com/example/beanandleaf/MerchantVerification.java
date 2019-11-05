package com.example.beanandleaf;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;



public class MerchantVerification extends AppCompatActivity {

    private static int RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_verification);


        Button buttonLoadImage = (Button) findViewById(R.id.uploadButton);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT);
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

            ImageView iV = (ImageView) findViewById(R.id.userImage);
            iV.setImageBitmap(BitmapFactory.decodeFile(path));

        }


    }

}
