package com.example.muf;
//Author: LIN YUAN
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class Match extends AppCompatActivity {

    private ImageView upper;
    private ImageView lower;
    public Uri imageUri;
    public Uri imageUrii;

    private FirebaseStorage storage;

    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();

        upper=findViewById(R.id.Upper);
        lower=findViewById(R.id.Lowwer);

        lower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicturelow();
            }
        });

        upper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });
    }

    private void choosePicturelow() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,2);
    }


    private void choosePicture() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData() != null ){
            imageUri =data.getData();
            upper.setImageURI(imageUri);
            uploadPicture();

        }
        if (requestCode==2 && resultCode==RESULT_OK && data!=null && data.getData() !=null){
            imageUrii=data.getData();
            lower.setImageURI(imageUrii);
            uploadpictureL();
        }
    }

    private void uploadpictureL() {
        final String randomkey=UUID.randomUUID().toString();
        StorageReference riverReff= storageReference.child("images/" +randomkey);
        riverReff.putFile(imageUrii)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Snackbar.make(findViewById(android.R.id.content),"Image upload",Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Failed to upload",Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void uploadPicture() {
        final String randomKey= UUID.randomUUID().toString();
        StorageReference riversRef= storageReference.child("images/"+randomKey);
        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Snackbar.make(findViewById(android.R.id.content),"Image upload",Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Failed to upload",Toast.LENGTH_LONG).show();
                    }
                });

    }

}