package com.opennxt.clearscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
//import android.app.Activity;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.scanlibrary.PickImageFragment;
import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

//import java.io.IOException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static com.opennxt.clearscanner.R.layout.pick_image_fragment;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    private List<Uri> scannedBitmaps = new ArrayList<>();

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(pick_image_fragment);



        //fucking permission Dispatcher
        ImageButton buttonOpenCamera = findViewById(R.id.cameraButton);
        buttonOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivityPermissionsDispatcher.openCameraWithPermissionCheck(MainActivity.this);
            }
        });

        listView = findViewById(R.id.listView);

        ArrayList<String> arrayList = new ArrayList<>();

        //Preview Image thumbnails in list view
        arrayList.add("Sample");


        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
    }


    @NeedsPermission(Manifest.permission.CAMERA)
   void openCamera()
   {
       scannedBitmaps.clear();
       Intent intent = new Intent(this, ScanActivity.class);
       intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, ScanConstants.OPEN_CAMERA);
       startActivityForResult(intent, ScanConstants.START_CAMERA_REQUEST_CODE);
   }

    //fucking permission Dispatcher
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this,requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void showRationaleForCamera (final PermissionRequest request)
    {
        new AlertDialog.Builder(this)
                .setTitle("Permission Needed")
                .setMessage("This Permission is needed for scan documents ")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request.proceed();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request.cancel();
                    }
                })
                .show();
    }
    @OnPermissionDenied(Manifest.permission.CAMERA)
    void onCameraDenied() {
        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
    }
    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void onNeverAsk () {
        Toast.makeText(this, "Never Asking Again", Toast.LENGTH_SHORT).show();
    }

    // public void openCamera(View v){}

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
