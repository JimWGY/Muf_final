package com.example.muf;
//Author: Guangyu Wang
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class Images extends AppCompatActivity implements View.OnClickListener {

    private String _userName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        String userName = getIntent().getStringExtra("user");
        _userName = userName;
        Log.d("test", "==========> user " + userName);

        Button recommend = findViewById(R.id.buttonRecom);
        Button storage = findViewById(R.id.buttonSt);
        recommend.setOnClickListener(this);
        storage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonRecom:
                Log.d("test", "----------> OnClickRecommend");
                showRecommend();
                break;
            case R.id.buttonSt:
                Log.d("test", "----------> OnClickStorage");
                showStorage();
                break;
            default:
                break;
        }
    }

    void showStorage() {

        SharedPreferences sp = getSharedPreferences("data", Context.MODE_PRIVATE);
        String text = sp.getString("user_storage_" + _userName, "{}");

        new AlertDialog.Builder(this)
                .setTitle("Storage")
                .setMessage("Choose Storage Type")
                .setPositiveButton("Shoes", new DialogInterface.OnClickListener() {// 添加确定按钮
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent i = new Intent(Images.this, Storage.class);
                        i.putExtra("data", text);
                        i.putExtra("name", "shoes");
                        i.putExtra("user", _userName);
                        startActivity(i);
                    }
                })
                .setNeutralButton("Pants", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent i = new Intent(Images.this, Storage.class);
                        i.putExtra("data", text);
                        i.putExtra("name", "pants");
                        i.putExtra("user", _userName);
                        startActivity(i);
                    }
                })
                .setNegativeButton("Tops", new DialogInterface.OnClickListener()  {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent i = new Intent(Images.this, Storage.class);
                        i.putExtra("data", text);
                        i.putExtra("name", "tops");
                        i.putExtra("user", _userName);
                        startActivity(i);
                    }
                }).show();
    }

    void showRecommend() {
        SharedPreferences sp = getSharedPreferences("data", Context.MODE_PRIVATE);
        String text = sp.getString("user_storage_" + _userName, "{}");
        if (text.equals("{}")) {
            Toast.makeText(this, "not has storage, should save storage first!", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            JSONObject data = new JSONObject(text);

            Random rand = new Random();
            rand.setSeed(System.currentTimeMillis());

            int idx1 = rand.nextInt();
            int idx2 = rand.nextInt();
            int idx3 = rand.nextInt();

            JSONArray tops = data.getJSONArray("tops");
            JSONArray pants = data.getJSONArray("pants");
            JSONArray shoes = data.getJSONArray("shoes");

            if (tops.length() <= 0) {
                Toast.makeText(this, "There has no Tops!", Toast.LENGTH_LONG).show();
                return;
            }
            if (pants.length() <= 0) {
                Toast.makeText(this, "There has no pants!", Toast.LENGTH_LONG).show();
                return;
            }

            if (shoes.length() <= 0) {
                Toast.makeText(this, "There has no shoes!", Toast.LENGTH_LONG).show();
                return;
            }

            String t1 = tops.getString(idx1 % tops.length());
            String t2 = pants.getString(idx2 % pants.length());
            String t3 = shoes.getString(idx3 % shoes.length());

            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
            builder.setMessage("Top:" + t1 + "\nPant:" + t2 + "\nShoe:" + t3);
            builder.setPositiveButton("Confirm", null);
            AlertDialog dialog = builder.create();
            dialog.show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}