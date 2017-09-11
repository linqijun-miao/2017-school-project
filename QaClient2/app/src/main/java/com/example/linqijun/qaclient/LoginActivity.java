package com.example.linqijun.qaclient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import server.MyMessage;
import server.MySocket;
import server.User;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class LoginActivity extends AppCompatActivity { //minimum

    private EditText accountEdit;

    private EditText passwordEdit;

    private Button loginB;

    private Button registerB;

    LocalRceiver  localRceiver;
    LocalRceiver  localRceiver2;


    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            Toast.makeText(LoginActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        accountEdit = (EditText) findViewById(R.id.account);
        passwordEdit = (EditText) findViewById(R.id.password);

        registerB = (Button) findViewById(R.id.register);

        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动注册
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginB = (Button) findViewById(R.id.login);

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(accountEdit.getText().toString().equals("") || passwordEdit.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"can't be empty",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Intent  intent = new Intent(LoginActivity.this,NetService.class);
                    double id = Double.parseDouble(accountEdit.getText().toString()) ;
                    String password = passwordEdit.getText().toString();
                    User temp = new User(id,"0");
                    temp.setPassWord(password);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",temp);
                    intent.putExtras(bundle);

                    localRceiver2 = new LocalRceiver();
                    IntentFilter intentFilter2 = new IntentFilter();
                    intentFilter2.addAction("com.example.linqijun.qaclient.LOGIN_EXIT");
                    registerReceiver(localRceiver2,intentFilter2);

                    localRceiver = new LocalRceiver();
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("com.example.linqijun.qaclient.LOGINSS");
                    registerReceiver(localRceiver,intentFilter);
                    startService(intent);


                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(localRceiver);
        unregisterReceiver(localRceiver2);
        super.onDestroy();
    }


    class LocalRceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("com.example.linqijun.qaclient.LOGINSS")){
                User temp = (User) intent.getSerializableExtra("logu");
                Intent intent1 = new Intent(LoginActivity.this,MainPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",temp);
                intent1.putExtras(bundle);
                startActivity(intent1);
                return;
            }
            if(intent.getAction().equals("com.example.linqijun.qaclient.LOGIN_EXIT")){
                finish();
                return;
            }
                Toast.makeText(LoginActivity.this,"失败",Toast.LENGTH_SHORT).show();

        }
    }




}
