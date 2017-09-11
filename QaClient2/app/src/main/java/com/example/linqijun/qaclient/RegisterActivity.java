package com.example.linqijun.qaclient;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import server.MyMessage;
import server.MySocket;
import server.User;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class RegisterActivity extends AppCompatActivity {
    private MySocket so;

    private EditText accountEdit;

    private EditText passwordEdit;

    private EditText nameEdit;

    private RadioGroup sexGroup;

    private Button registerButton;

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                Toast.makeText(RegisterActivity.this,"fail",Toast.LENGTH_SHORT).show();
            }
            if(msg.what == 0){
                Toast.makeText(RegisterActivity.this,"success",Toast.LENGTH_SHORT).show();
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        accountEdit = (EditText) findViewById(R.id.accountR);
        passwordEdit = (EditText) findViewById(R.id.passwordR);
        nameEdit = (EditText) findViewById(R.id.nameR);
        registerButton = (Button) findViewById(R.id.registerB);
        sexGroup = (RadioGroup) findViewById(R.id.radioGroupSex);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (accountEdit.getText().toString().equals("") || passwordEdit.getText().toString().equals("") || nameEdit.getText().toString().equals("")) {
                        Toast.makeText(RegisterActivity.this, "can't be empty", Toast.LENGTH_SHORT).show();
                        return;
                }

                new Thread(new RegisterThread()).start();

            }
        });
    }
    private class RegisterThread implements Runnable{

        @Override
        public void run() {
            try {
                so = new MySocket("120.24.90.211",6870);


                double id = Double.parseDouble(accountEdit.getText().toString());
                String password = passwordEdit.getText().toString();
                String name = nameEdit.getText().toString();
                User newUser = new User(id,name);
                newUser.setPassWord(password);

                MyMessage mgs = new MyMessage(newUser);
                mgs.setType(0);
                mgs.send(so);

                DataInputStream dis = new DataInputStream(so.getInputStream());
                ObjectInputStream ois = new ObjectInputStream(dis);
                MyMessage res = (MyMessage)ois.readObject();

                if(res.getString().equals("success")){
                    handler.sendEmptyMessage(0);//成功
                    finish();
                }else{
                    handler.sendEmptyMessage(1);//失败
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
