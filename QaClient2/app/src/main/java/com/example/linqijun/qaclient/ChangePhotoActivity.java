package com.example.linqijun.qaclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.MyMessage;
import server.User;

public class ChangePhotoActivity extends AppCompatActivity {
    private User u;
    private GridView gview;
    private int chosenID = 0;
    private Button confirm ;
    int photoId;
    private TextView chosenName;
    private int[] photos = {R.drawable.f0,R.drawable.f1,R.drawable.f2,R.drawable.f3,R.drawable.f4,R.drawable.f5,R.drawable.f6,R.drawable.f7,R.drawable.f8,R.drawable.f9,R.drawable.f10,R.drawable.f11};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_change_photo);
        gview = (GridView) findViewById(R.id.change_photo);

        u = (User) getIntent().getSerializableExtra("User");

        List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();
        Map<String,Object> item = new HashMap<String,Object>();
        item.put("imageItem",R.drawable.f0);
        item.put("textItem","Bart");
        items.add(item);

        item = new HashMap<String,Object>();
        item.put("imageItem",R.drawable.f1);
        item.put("textItem","Homer");
        items.add(item);

        item = new HashMap<String,Object>();
        item.put("imageItem",R.drawable.f2);
        item.put("textItem","Maggie");
        items.add(item);

        item = new HashMap<String,Object>();
        item.put("imageItem",R.drawable.f3);
        item.put("textItem","Lisa");
        items.add(item);

        item = new HashMap<String,Object>();
        item.put("imageItem",R.drawable.f4);
        item.put("textItem","Marge");
        items.add(item);

        item = new HashMap<String,Object>();
        item.put("imageItem",R.drawable.f5);
        item.put("textItem","FatTony");
        items.add(item);

        item = new HashMap<String,Object>();
        item.put("imageItem",R.drawable.f6);
        item.put("textItem","Flanders");
        items.add(item);

        item = new HashMap<String,Object>();
        item.put("imageItem",R.drawable.f7);
        item.put("textItem","Krusty");
        items.add(item);

        item = new HashMap<String,Object>();
        item.put("imageItem",R.drawable.f8);
        item.put("textItem","Moe");
        items.add(item);

        item = new HashMap<String,Object>();
        item.put("imageItem",R.drawable.f9);
        item.put("textItem","Martin");
        items.add(item);

        item = new HashMap<String,Object>();
        item.put("imageItem",R.drawable.f10);
        item.put("textItem","Milhouse");
        items.add(item);

        item = new HashMap<String,Object>();
        item.put("imageItem",R.drawable.f11);
        item.put("textItem","Burns");
        items.add(item);

        SimpleAdapter simpleAdapter = new SimpleAdapter(this,items,R.layout.photo_item, new String[]{"imageItem","textItem"} ,new int[]{R.id.gridview_image,R.id.gridview_name});
        chosenName = (TextView) findViewById(R.id.be_chosen) ;
        gview.setAdapter(simpleAdapter);

        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String,Object> item = (HashMap<String,Object>) parent.getItemAtPosition( position );
                chosenName.setText((String) item.get("textItem"));
                 photoId = (Integer) item.get("imageItem");
                switch (photoId){
                    case R.drawable.f0:
                        chosenID = 0;
                        break;
                    case R.drawable.f1:
                        chosenID = 1;
                        break;
                    case R.drawable.f2:
                        chosenID = 2;
                        break;
                    case R.drawable.f3:
                        chosenID = 3;
                        break;
                    case R.drawable.f4:
                        chosenID = 4;
                        break;
                    case R.drawable.f5:
                        chosenID = 5;
                        break;
                    case R.drawable.f6:
                        chosenID = 6;
                        break;
                    case R.drawable.f7:
                        chosenID = 7;
                        break;
                    case R.drawable.f8:
                        chosenID = 8;
                        break;
                    case R.drawable.f9:
                        chosenID = 9;
                        break;
                    case R.drawable.f10:
                        chosenID = 10;
                        break;
                    case R.drawable.f11:
                        chosenID = 11;
                        break;
                }
            }
        });

        confirm = (Button) findViewById(R.id.gridview_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.linqijun.qaclient.send");

                u.setPhoto(chosenID);
                MyMessage changeP = new MyMessage(u);
                changeP.setType(6);

                Bundle bundle = new Bundle();
                bundle.putSerializable("sendmgs",changeP);
                intent.putExtras(bundle);
                sendBroadcast(intent);

                Intent intent1 = new Intent();
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("photo",  photoId);
                intent1.putExtras(bundle1);
                setResult(RESULT_OK,intent1);
                finish();

            }
        });
    }
}
