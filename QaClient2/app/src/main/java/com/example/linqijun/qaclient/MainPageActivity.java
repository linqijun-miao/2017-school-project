package com.example.linqijun.qaclient;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Adapter.FriendAdapter;
import server.HttpUtils;
import server.ListUser;
import server.MyMessage;

import server.User;

public class MainPageActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    FriendAdapter adapter;
    private MyMessage mgs;
    mgsRceiver mgsRceiver;
    private User u;
    private User inTalk;
    private ImageButton userHead;
    private Button exitButton;


    String cityName;
    String qw;
    String tq;
    String fl;
    String numtq;
    String lastUpdate;

    private ImageButton userHeadInside;
    private TextView userName;

    private ListView friends;

    private ArrayList<ListUser> ListfriendList = new ArrayList<ListUser>();

    private TextView city;
    private TextView qwt;
    private TextView tqText;
    private TextView flText;

    private ArrayList<MyMessage> temp = new ArrayList<MyMessage>();

    private MyHandler handler = null;

    private Button addFriend;
    private ImageButton tempPhoto;
    private TextView lastUpdateText;
    private ImageButton refeshButton;

    private LocationManager locationManager;
    private String locationProvider;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    ArrayList<MyMessage> temp2 = (ArrayList<MyMessage>) data.getSerializableExtra("otherMgs");
                    for (int i = 0; i < temp2.size(); i++) {
                        temp.add(temp2.get(i));
                    }
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    int chosenid = (Integer) data.getSerializableExtra("photo");
                    userHeadInside.setImageResource(chosenid);
                    userHead.setImageResource(chosenid);
                }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_page_mark2);

        final Intent intent = getIntent();
        u = (User) intent.getSerializableExtra("user");

        if (savedInstanceState != null) {
            temp = (ArrayList<MyMessage>) savedInstanceState.getSerializable("tempMessage");
        }

        ArrayList<User> friendList = u.getFriends();
        for (int i = 0; i < friendList.size(); i++) {
            ListfriendList.add(new ListUser(friendList.get(i)));
        }

        handler = new MyHandler();
        //天气

        qwt = (TextView)  findViewById(R.id.qw) ;
        city = (TextView)  findViewById(R.id.city);
        tqText = (TextView) findViewById(R.id.tq);
        flText = (TextView) findViewById(R.id.fl) ;
        tempPhoto = (ImageButton) findViewById(R.id.tem_photo);
        lastUpdateText = (TextView) findViewById(R.id.update_time);

        //名字
        String nameTemp = u.getName() + " ( " + ((Double) u.getID()).toString() + " )";
        userName = (TextView) findViewById(R.id.main_userName);
        userName.setText(nameTemp);

        drawerLayout = (DrawerLayout) findViewById(R.id.main_left_drawer);

        //头像
        userHead = (ImageButton) findViewById(R.id.main_head_photo);
        int photo = u.getPhotoType();
        userHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        switch (photo) {
            case 0:
                userHead.setImageResource(R.drawable.f0);
                break;
            case 1:
                userHead.setImageResource(R.drawable.f1);
                break;
            case 2:
                userHead.setImageResource(R.drawable.f2);
                break;
            case 3:
                userHead.setImageResource(R.drawable.f3);
                break;
            case 4:
                userHead.setImageResource(R.drawable.f4);
                break;
            case 5:
                userHead.setImageResource(R.drawable.f5);
                break;
            case 6:
                userHead.setImageResource(R.drawable.f6);
                break;
            case 7:
                userHead.setImageResource(R.drawable.f7);
                break;
            case 8:
                userHead.setImageResource(R.drawable.f8);
                break;
            case 9:
                userHead.setImageResource(R.drawable.f9);
                break;
            case 10:
                userHead.setImageResource(R.drawable.f10);
                break;
            case 11:
                userHead.setImageResource(R.drawable.f11);
                break;
        }

        userHeadInside = (ImageButton) findViewById(R.id.main_draw_photo);

        switch (photo) {
            case 0:
                userHeadInside.setImageResource(R.drawable.f0);
                break;
            case 1:
                userHeadInside.setImageResource(R.drawable.f1);
                break;
            case 2:
                userHeadInside.setImageResource(R.drawable.f2);
                break;
            case 3:
                userHeadInside.setImageResource(R.drawable.f3);
                break;
            case 4:
                userHeadInside.setImageResource(R.drawable.f4);
                break;
            case 5:
                userHeadInside.setImageResource(R.drawable.f5);
                break;
            case 6:
                userHeadInside.setImageResource(R.drawable.f6);
                break;
            case 7:
                userHeadInside.setImageResource(R.drawable.f7);
                break;
            case 8:
                userHeadInside.setImageResource(R.drawable.f8);
                break;
            case 9:
                userHeadInside.setImageResource(R.drawable.f9);
                break;
            case 10:
                userHeadInside.setImageResource(R.drawable.f10);
                break;
            case 11:
                userHeadInside.setImageResource(R.drawable.f11);
                break;
        }
        userHeadInside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainPageActivity.this, ChangePhotoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("User", u);
                intent1.putExtras(bundle);
                startActivityForResult(intent1, 2);
            }
        });

        exitButton = (Button) findViewById(R.id.main_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent("com.example.linqijun.qaclient.exit");
                sendBroadcast(intent1);
            }
        });
        //好友列表
        adapter = new FriendAdapter(MainPageActivity.this, R.layout.friend_list, ListfriendList);
        friends = (ListView) findViewById(R.id.main_friendList);
        friends.setAdapter(adapter);
        friends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListUser inTalkL = ListfriendList.get(position);
                inTalkL.setNum(0);
                adapter.notifyDataSetChanged();
                inTalk = inTalkL.getUser();
                Intent intent = new Intent(MainPageActivity.this, ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("friend", inTalk);
                bundle.putSerializable("user", u);

                ArrayList<MyMessage> beSent = new ArrayList<MyMessage>();
                for (int i = 0; i < temp.size(); i++) {
                    if (temp.get(i).getFriend() == inTalk.getID()) {
                        beSent.add(temp.get(i));

                    }
                }
                for (int i = 0; i < temp.size(); i++) {
                    if (temp.get(i).getFriend() == inTalk.getID()) {
                        temp.remove(i);

                    }
                }
                bundle.putSerializable("mgs", beSent);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });
        new Thread(new UpdateMgsNum()).start();

        addFriend = (Button) findViewById(R.id.main_grid_add);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainPageActivity.this, AddFriendActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("User", u);
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });

        Log.d("QQ","PP");
        Intent intent1  = new Intent("com.example.linqijun.qaclient.LOGIN_EXIT");
        sendBroadcast(intent1);
        getLocation();
        Log.d("ss","dd");

        refeshButton = (ImageButton) findViewById(R.id.temp_refresh);
        refeshButton.setImageResource(R.drawable.refresh);
        refeshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                Toast.makeText(MainPageActivity.this,"Temprature has been updated",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onRestart() {
        inTalk = null;
        super.onRestart();
    }


    @Override
    protected void onDestroy() {

        unregisterReceiver(mgsRceiver);
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("tempMessage",temp);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        getLocation();
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    class mgsRceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("com.example.linqijun.qaclient.resMgs")){
                MyMessage tempM = (MyMessage) intent.getSerializableExtra("newMgs");
                if(tempM.getType() == 3){
                    if(inTalk !=null){
                        if(inTalk.getID() == tempM.getFriend()){
                            return;
                        }
                    }
                    //chat
                    temp.add(tempM);
                    for(int i = 0 ; i < ListfriendList.size() ; i++){
                        double fid = tempM.getFriend();
                        if(ListfriendList.get(i).getUser().getID() == fid){
                            int num = ListfriendList.get(i).getMgsNum();
                            ListfriendList.get(i).setNum(num + 1);
                            break;
                        }
                    }
                }
            }
        }
    }


    public void getLocation(){
        //Location
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        locationProvider = locationManager.getBestProvider(criteria, true);
        Log.d("ok","1");
        //6.0之后权限动态获得
       if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            Log.d("location","false");
           ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }else{
           if(locationManager.getLastKnownLocation(locationProvider) == null ){
               locationProvider = locationManager.NETWORK_PROVIDER;
           }
           Location location = locationManager.getLastKnownLocation(locationProvider);

           double latitude = location.getLatitude();
           double longitude = location.getLongitude();
           Log.d("ok","2");
           final String path ="http://api.yytianqi.com/observe?city="+latitude+","+longitude+"&key=fwkprmbln6f5i0h3" ;
           new Thread(new Runnable(){

               @Override
               public void run() {
                   int code;
                   try {

                       //城市
                       URL url = new URL(path);
                       HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                       connection.setRequestMethod("GET");
                       connection.setConnectTimeout(10000);
                       code = connection.getResponseCode();
                       if(code == 200 ){
                           InputStream is = connection.getInputStream();
                           String result = HttpUtils.readMyInputStream(is);

                           JSONObject jsonobject = new JSONObject(result);


                           JSONObject jsonObject = jsonobject.getJSONObject("data");
                           cityName = jsonObject.optString("cityName");
                           qw = jsonObject.optString("qw");
                           tq = jsonObject.optString("tq");
                           fl = jsonObject.optString("fl");
                           numtq = jsonObject.optString("numtq");
                           lastUpdate = jsonObject.optString("lastUpdate");
                           Message message = new Message();
                           message.what = 2;
                           MainPageActivity.this.handler.sendMessage(message);
                           Log.d("cityName",cityName);
                           Log.d("qw",qw);
                           //天气

                       }else{
                           Log.d("requestcode",""+code);
                           return;
                       }
                   } catch (MalformedURLException e) {
                       e.printStackTrace();
                   } catch (IOException e) {
                       e.printStackTrace();
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }
           }).start();
       }

    }


    class UpdateMgsNum implements Runnable{

        public UpdateMgsNum(){
            mgsRceiver = new mgsRceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.example.linqijun.qaclient.resMgs");
            registerReceiver(mgsRceiver,intentFilter);
        }
        @Override
        public void run() {
            while(true){

               Message m = new Message();
               m.what = 1;
                MainPageActivity.this.handler.sendMessage(m);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class MyHandler extends Handler{

        public MyHandler() {
            super();
        }

        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                adapter.notifyDataSetChanged();
            }
            if(msg.what == 2){
                city.setText(cityName);
                qwt.setText(""+qw+"℃");
                tqText.setText(tq);
                flText.setText(fl);
                lastUpdateText.setText("Latest update: " + lastUpdate);
                getTemPhoto();
            }
        }
    }

    public void getTemPhoto(){
        if(numtq.equals("00")){
            tempPhoto.setImageResource(R.drawable.f00_0);
            return;
        }
        if(numtq.equals("01")){
            tempPhoto.setImageResource(R.drawable.t01_0);
            return;
        }
        if(numtq.equals("02")){
            tempPhoto.setImageResource(R.drawable.t02_0);
            return;
        }
        if(numtq.equals("03")){
            tempPhoto.setImageResource(R.drawable.t03_0);
            return;
        }
        if(numtq.equals("04")){
            tempPhoto.setImageResource(R.drawable.t04_0);
            return;
        }
        if(numtq.equals("05")){
            tempPhoto.setImageResource(R.drawable.t05_0);
            return;
        }
        if(numtq.equals("06")){
            tempPhoto.setImageResource(R.drawable.t06_0);
            return;
        }
        if(numtq.equals("07")){
            tempPhoto.setImageResource(R.drawable.t07_0);
            return;
        }
        if(numtq.equals("08")){
            tempPhoto.setImageResource(R.drawable.t08_0);
            return;
        }
        if(numtq.equals("09")){
            tempPhoto.setImageResource(R.drawable.t09_0);
            return;
        }
        if(numtq.equals("10")){
            tempPhoto.setImageResource(R.drawable.t10_0);
            return;
        }
        if(numtq.equals("11")){
            tempPhoto.setImageResource(R.drawable.t11_0);
            return;
        }
        if(numtq.equals("12")){
            tempPhoto.setImageResource(R.drawable.t12_0);
            return;
        }
        if(numtq.equals("13")){
            tempPhoto.setImageResource(R.drawable.t13_0);
            return;
        }
        if(numtq.equals("14")){
            tempPhoto.setImageResource(R.drawable.t14_0);
            return;
        }
        if(numtq.equals("15")){
            tempPhoto.setImageResource(R.drawable.t15_0);
            return;
        }
        if(numtq.equals("16")){
            tempPhoto.setImageResource(R.drawable.t16_0);
            return;
        }
        if(numtq.equals("17")){
            tempPhoto.setImageResource(R.drawable.t17_0);
            return;
        }
        if(numtq.equals("18")){
            tempPhoto.setImageResource(R.drawable.t18_0);
            return;
        }
        if(numtq.equals("19")){
            tempPhoto.setImageResource(R.drawable.t19_0);
            return;
        }
        if(numtq.equals("20")){
            tempPhoto.setImageResource(R.drawable.t20_0);
            return;
        }
        if(numtq.equals("21")){
            tempPhoto.setImageResource(R.drawable.t21_0);
            return;
        }
        if(numtq.equals("22")){
            tempPhoto.setImageResource(R.drawable.t22_0);
            return;
        }
        if(numtq.equals("23")){
            tempPhoto.setImageResource(R.drawable.t23_0);
            return;
        }
        if(numtq.equals("24")){
            tempPhoto.setImageResource(R.drawable.t24_0);
            return;
        }
        if(numtq.equals("25")){
            tempPhoto.setImageResource(R.drawable.t25_0);
            return;
        }
        if(numtq.equals("26")){
            tempPhoto.setImageResource(R.drawable.t26_0);
            return;
        }
        if(numtq.equals("27")){
            tempPhoto.setImageResource(R.drawable.t27_0);
            return;
        }
        if(numtq.equals("28")){
            tempPhoto.setImageResource(R.drawable.t28_0);
            return;
        }
        if(numtq.equals("29")){
            tempPhoto.setImageResource(R.drawable.t29_0);
            return;
        }
        if(numtq.equals("30")){
            tempPhoto.setImageResource(R.drawable.t30_0);
            return;
        }
        if(numtq.equals("31")){
            tempPhoto.setImageResource(R.drawable.t31_0);
            return;
        }
        if(numtq.equals("32")){
            tempPhoto.setImageResource(R.drawable.t32_0);
            return;
        }
        tempPhoto.setImageResource(R.drawable.t49_0);
    }



}
