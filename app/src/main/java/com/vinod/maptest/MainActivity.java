package com.vinod.maptest;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.vinod.dataaccess.MyDatabase;
import com.vinod.dataobjects.LocationDO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btAdd;
    ListView lv;
    SQLiteDatabase db =null;
    MyDatabase dbase =null;
    ArrayAdapter<String> adapter =null;
    ArrayList<String> list = new ArrayList<String>();
    public int  ADDED_LOCATION =10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btAdd =(Button) findViewById(R.id.btAdd);
        lv =(ListView) findViewById(R.id.lv);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list  );
        lv.setAdapter(adapter);
        dbase = new MyDatabase(MainActivity.this,"Location.db",null, 1);
        loadData();
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,  MapActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadData()
    {
         new Thread(new Runnable() {
             @Override
             public void run() {
                 ArrayList <LocationDO> l= new ArrayList<LocationDO>();
                 l = dbase.getLocationsList();
                 if(l!=null && l.size()>0)
                 {
                     list.clear();
                     for(LocationDO obj : l)
                     {
                         list.add(obj.address+"\n"+obj.lan+":"+obj.lat);
                     }
                     adapter.notifyDataSetChanged();

                 }

             }
         }).start();
    }

    void addDataToDatabase(LocationDO locationDO)
    {
        ContentValues val = new ContentValues();
        val.put(MyDatabase._ADDRESS,locationDO.address);
        list.add(locationDO.lan+" "+locationDO.lan);
        val.put(MyDatabase._LATTITUDE,locationDO.lat);
        val.put(MyDatabase._LONGITUDE,locationDO.lan);
        dbase.insert(val);

    }

  /*  public void addOnClick(View view)
    {
     *//*   new Thread(new Runnable() {
            @Override
            public void run() {
                addDataToDatabase(new LocationDO());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();*//*

//     startActivityForResult(intent,ADDED_LOCATION);


    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
