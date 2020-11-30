package com.example.muf;
//Author: Guangyu Wang
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Storage extends AppCompatActivity {

    ArrayList<String> _datas = new ArrayList<>();
    GridView _gridView;
    String _userName;

    JSONObject _userData;
    String _curKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        FloatingActionButton fab = findViewById(R.id.addBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });

        String json = getIntent().getStringExtra("data");
        String key = getIntent().getStringExtra("name");
        _userName = getIntent().getStringExtra("user");
        _curKey = key;

        GridView gridview = (GridView) findViewById(R.id.gridview);
        _gridView = gridview;

        try {
            JSONObject jsonRoot = new JSONObject(json);
            if (!jsonRoot.has(_curKey)) {
                jsonRoot.put(_curKey, new JSONArray());
            }
            JSONArray jsonArray = jsonRoot.getJSONArray(key);
            _userData = jsonRoot;

            for (int i = 0; i < jsonArray.length(); i++){
                String name = jsonArray.getString(i);
                _datas.add(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        gridview.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return _datas.size();
            }

            @Override
            public Object getItem(int position) {
                return _datas.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = new TextView(Storage.this);
                tv.setText(_datas.get(position));
                return tv;
            }
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        gridview.setNumColumns(1);
    }

    void showAddDialog() {

        LayoutInflater layoutInf = LayoutInflater.from(this);
        View loginView = layoutInf.inflate(R.layout.add_clothes, null);

        final EditText etUserName = (EditText)loginView.findViewById(R.id.et_user_name);
        final EditText etPwd = (EditText)loginView.findViewById(R.id.et_password);

        MaterialAlertDialogBuilder dialog=new MaterialAlertDialogBuilder(this);
        dialog.setTitle("Add " + _curKey);

        dialog.setView(loginView);
        dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = etUserName.getText() + "(" + etPwd.getText() + ")";
                //Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
                Log.d("test", "=========> text " + text);
                _datas.add(text);
                _gridView.invalidate();
                try {
                    if (_userData.getJSONArray(_curKey) == null) {
                        _userData.put(_curKey, new JSONArray());
                    }
                    _userData.getJSONArray(_curKey).put(text);
                    saveUserData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.create().show();
    }

    void saveUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_storage_" + _userName, _userData.toString());
        editor.commit();
    }
}