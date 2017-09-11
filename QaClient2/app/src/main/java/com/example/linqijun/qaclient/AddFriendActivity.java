package com.example.linqijun.qaclient;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import server.MyMessage;
import server.User;

public class AddFriendActivity extends AppCompatActivity {
    private User u;
    private EditText editText;
    private Button searchButton;
    private TextView findId;
    private TextView findName;
    private Button confirmButton;
    private Button cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_friend);

        Intent intent = getIntent();
        u = (User) intent.getSerializableExtra("User") ;
        editText = (EditText) findViewById(R.id.add_friend_id_input);
        searchButton = (Button) findViewById(R.id.search_friend);
        findId = (TextView) findViewById(R.id.find_id);
        findName = (TextView) findViewById(R.id.find_name);
        confirmButton = (Button) findViewById(R.id.add_confirm);
        cancelButton = (Button) findViewById(R.id.add_cancel);

        cancelButton.setVisibility(View.INVISIBLE);
        confirmButton.setVisibility(View.INVISIBLE);

        searchButton.setOnClickListener(new View.OnClickListener() {
            ProgressDialog progressDialog;
            @Override
            public void onClick(View v) {
                if((editText.getText().toString()).equals("")){
                    Toast.makeText(AddFriendActivity.this,"not empty",Toast.LENGTH_SHORT).show();
                    return;
                }
               double id =  Double.parseDouble(editText.getText().toString());
                MyMessage addmgs = new MyMessage(u);
                addmgs.setType(5);
                addmgs.wirteString(((Double)id).toString());

                Intent intent1 = new Intent("com.example.linqijun.qaclient.send");
                Bundle bundle = new Bundle();
                bundle.putSerializable("sendmgs",addmgs);
                intent1.putExtras(bundle);
                sendBroadcast(intent1);

                progressDialog = new ProgressDialog(AddFriendActivity.this);
                progressDialog.setTitle("Wait please");
                progressDialog.setMessage("Searching...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                IntentFilter intentFilter = new IntentFilter("com.example.linqijun.qaclient.resMgs");
              registerReceiver(new BroadcastReceiver() {
                  @Override
                  public void onReceive(Context context, Intent intent) {

                          if(intent.getAction().equals("com.example.linqijun.qaclient.resMgs")) {
                              MyMessage tempM = (MyMessage) intent.getSerializableExtra("newMgs");
                              if (tempM.getType() == 5) {
                                  if (tempM.getString().equals("ask")) {
                                      String name = tempM.getUser().getName();
                                      Double id = tempM.getUser().getID();

                                      findId.setText(id.toString());
                                      findName.setText(name);

                                      findId.setVisibility(View.VISIBLE);
                                      findName.setVisibility(View.VISIBLE);
                                      confirmButton.setVisibility(View.VISIBLE);
                                      cancelButton.setVisibility(View.VISIBLE);

                                      progressDialog.dismiss();
                                  }
                                  if(tempM.getString().equals("not found")){
                                      Toast.makeText(AddFriendActivity.this,"not found",Toast.LENGTH_SHORT).show();
                                      progressDialog.dismiss();
                                  }
                                  unregisterReceiver(this);
                              }
                          }
                  }
              },intentFilter);


            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyMessage mgs = new MyMessage(u);
                mgs.setType(5);
                mgs.wirteString("yes");
                Intent intent2 = new Intent("com.example.linqijun.qaclient.send");
                Bundle bundle = new Bundle();
                bundle.putSerializable("sendmgs",mgs);
                intent2.putExtras(bundle);
                sendBroadcast(intent2);
                finish();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
