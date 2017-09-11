package com.example.linqijun.qaclient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Adapter.MgsAdapter;
import Adapter.SelfMessgaeForMgsA;
import server.MyMessage;
import server.User;

public class ChatActivity extends AppCompatActivity {
    private Button back;

    private TextView friendName;

    private ListView mgsListView;

    private EditText intputText;

    private  Button send;

    private MgsAdapter mgsAdapter;

    private ArrayList<MyMessage> mgsList = null;

    private ArrayList<SelfMessgaeForMgsA> mgsListLocal = new ArrayList<SelfMessgaeForMgsA>();

    private ArrayList<MyMessage> otherMgsList = new ArrayList<MyMessage>();

    private User friend;

    private User u ;

    private mgsRceiver mgsRceiver;

    @Override
    public void onBackPressed() {
        unregisterReceiver(mgsRceiver);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("otherMgs",otherMgsList);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_chat);
        mgsRceiver = new mgsRceiver();
        IntentFilter intentFilter = new IntentFilter("com.example.linqijun.qaclient.resMgs");
        registerReceiver(mgsRceiver,intentFilter);

        Intent intent = getIntent();
        mgsList = (ArrayList<MyMessage>) intent.getSerializableExtra("mgs");
        friend = (User) intent.getSerializableExtra("friend");
        u = (User) intent.getSerializableExtra("user");

        back = (Button)findViewById(R.id.chat_title_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        send = (Button)findViewById(R.id.chat_sendbutton);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String willsent = intputText.getText().toString();
                MyMessage wsend = new MyMessage(u);
                wsend.setType(3);
                wsend.setFriend(friend.getID());
                wsend.wirteString(willsent);
                mgsListLocal.add(new SelfMessgaeForMgsA(willsent,1));
                mgsAdapter.notifyDataSetChanged();
                Intent sendBD = new Intent("com.example.linqijun.qaclient.send");
                Bundle bundle = new Bundle();
                bundle.putSerializable("sendmgs",wsend);
                sendBD.putExtras(bundle);
                sendBroadcast(sendBD);
                intputText.setText("");
            }
        });
        intputText = (EditText)findViewById(R.id.chat_inputtext);

        friendName = (TextView)findViewById(R.id.chat_title_name);
        friendName.setText(friend.getName());
        mgsListView = (ListView)findViewById(R.id.chat_list_view);
        if(mgsList != null){
            for(int i = 0 ; i < mgsList.size() ;i++){
                SelfMessgaeForMgsA selfMessgaeForMgsA = new SelfMessgaeForMgsA(mgsList.get(i).getString(),0);
                mgsListLocal.add(selfMessgaeForMgsA);
            }
        }
        mgsAdapter = new MgsAdapter(ChatActivity.this,R.layout.mgs_item,mgsListLocal);
        mgsListView.setAdapter(mgsAdapter);
    }

    class mgsRceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("com.example.linqijun.qaclient.resMgs")) {
                MyMessage tempM = (MyMessage) intent.getSerializableExtra("newMgs");
                if(tempM.getType() == 3){
                    if(tempM.getFriend()==friend.getID()){
                        SelfMessgaeForMgsA temp = new SelfMessgaeForMgsA(tempM.getString(),0);
                        Log.d("res",temp.getContent());
                        mgsListLocal.add(temp);
                        mgsAdapter.notifyDataSetChanged();
                    }else{
                        //otherMgsList.add(tempM);
                    }
                }
            }
        }
    }
}
