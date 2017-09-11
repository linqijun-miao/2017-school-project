package com.example.linqijun.qaclient;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import server.MyMessage;
import server.User;

/**
 * Created by linqijun on 2017/4/6.
 */

public class NetService extends Service {
    private double inTalkFriend = 0;

    private Socket so;
    User login;
    private User u;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        login = (User) intent.getSerializableExtra("user");
        new Thread(new netThread()).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        new Thread(new Runnable(){

            @Override
            public void run() {
                MyMessage exitmsg = new MyMessage(u);
                exitmsg.setType(2);
                exitmsg.send(so);
                Log.d("netservice","close has been sent");
            }
        }).start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onDestroy();
        System.exit(0);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public User returnUser(){
        return u;
    }
    class netThread implements Runnable{

        mgsRceiver mgsRceiverExit;
        mgsRceiver mgsRceiver;
        public netThread(){
             mgsRceiver = new mgsRceiver();
            IntentFilter intentFilter = new IntentFilter("com.example.linqijun.qaclient.send");
            registerReceiver(mgsRceiver,intentFilter);
            mgsRceiverExit = new mgsRceiver();
            IntentFilter intentFilter1 = new IntentFilter("com.example.linqijun.qaclient.exit");
            registerReceiver(mgsRceiverExit,intentFilter1);
        }

        @Override
        protected void finalize() throws Throwable {
            unregisterReceiver(mgsRceiver);
            unregisterReceiver(mgsRceiverExit);
            super.finalize();
        }

        @Override
        public void run() {
            try{
            so = new Socket("120.24.90.211",6870);

            MyMessage mgs = new MyMessage(login);
            mgs.setType(1);
            mgs.send(so);

            DataInputStream dis = new DataInputStream(so.getInputStream());
            ObjectInputStream ois = new ObjectInputStream(dis);
            MyMessage res = (MyMessage)ois.readObject();

            if(res.getString().equals("success")){

                Intent loginss = new Intent("com.example.linqijun.qaclient.LOGINSS");
                u = res.getUser();
                Bundle bdl = new Bundle();
                bdl.putSerializable("logu",u);
                loginss.putExtras(bdl);
                sendBroadcast(loginss);

            }else{
                Intent loginff = new Intent("com.example.linqijun.qaclient.LOGINFF");
                sendBroadcast(loginff);
                stopSelf();
                return;

            }

                while(true){

                    MyMessage myMessage = MyMessage.receive(so);

                    if(myMessage == null){
                        continue;
                    }

                    Intent resMgs = new Intent("com.example.linqijun.qaclient.resMgs");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("newMgs",myMessage);
                    resMgs.putExtras(bundle);
                    sendBroadcast(resMgs);

                }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        }
    }

    class mgsRceiver extends BroadcastReceiver {
        MyMessage myMessage;
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("com.example.linqijun.qaclient.send")){

                 myMessage = (MyMessage) intent.getSerializableExtra("sendmgs");
                new Thread(new Runnable(){

                    @Override
                    public void run() {
                        myMessage.send(so);
                        Log.d("socket",so.toString());
                        Log.d("send","res send");
                    }
                }).start();
            }
            if(intent.getAction().equals("com.example.linqijun.qaclient.exit")){
                NetService.this.onDestroy();
            }
        }
    }


}
