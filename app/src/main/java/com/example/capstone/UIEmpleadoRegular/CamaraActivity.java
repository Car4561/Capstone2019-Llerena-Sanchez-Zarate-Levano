package com.example.capstone.UIEmpleadoRegular;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.example.capstone.R;

public class CamaraActivity extends AppCompatActivity {

    ImageView imgCamara;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);
        imgCamara=findViewById(R.id.imgCamara);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgCamara.setImageBitmap(bitmap);
        }

    }
}
