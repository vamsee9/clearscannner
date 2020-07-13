package com.opennxt.clearscanner;

import androidx.appcompat.app.AppCompatActivity;
//import android.app.Activity;
import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

//import java.io.IOException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.opennxt.clearscanner.R.layout.pick_image_fragment;

public class MainActivity extends AppCompatActivity {

    private List<Uri> scannedBitmaps = new ArrayList<>();

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pick_image_fragment);

        listView = findViewById(R.id.listView);

        ArrayList<String> arrayList = new ArrayList<>();

        //Preview Image thumbnails in list view
        arrayList.add("Sample");
        arrayList.add("Sample");
        arrayList.add("Sample");
        arrayList.add("Sample");
        arrayList.add("Sample");
        arrayList.add("Sample");
        arrayList.add("Sample");
        arrayList.add("Sample");
        arrayList.add("Sample");
        arrayList.add("Sample");
        arrayList.add("Sample");
        arrayList.add("Sample");
        arrayList.add("Sample");
        arrayList.add("Sample");
        arrayList.add("Sample");
        arrayList.add("Sample");
        arrayList.add("Sample");
        arrayList.add("Sample");


        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
    }

    public void openCamera(View v)
    {
        scannedBitmaps.clear();
        Intent intent = new Intent(this, ScanActivity.class);
        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, ScanConstants.OPEN_CAMERA);
        startActivityForResult(intent, ScanConstants.START_CAMERA_REQUEST_CODE);
    }

    public void openGallery(View v)
    {
        int REQUEST_CODE = 99;
        int preference = ScanConstants.OPEN_MEDIA;
        Intent intent = new Intent(this, ScanActivity.class);
        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

}
