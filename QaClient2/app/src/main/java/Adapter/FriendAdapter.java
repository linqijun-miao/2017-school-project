package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.linqijun.qaclient.R;

import java.util.ArrayList;

import server.ListUser;
import server.User;

/**
 * Created by linqijun on 2017/4/2.
 */

public class FriendAdapter extends ArrayAdapter<ListUser> {

    private int resource;

    public FriendAdapter(Context context, int resource, ArrayList<ListUser> u) {
        super(context, resource, u);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListUser listUser = getItem(position);
        User temp = listUser.getUser();
        View view = LayoutInflater.from(getContext()).inflate(resource,null);
        ImageView friendImage = (ImageView)view.findViewById(R.id.main_friend_photo);
        TextView friendName = (TextView)view.findViewById(R.id.main_friend_name);
        TextView mgsNum = (TextView)view.findViewById(R.id.main_friend_mgsnum);
        if(listUser.getMgsNum()!= 0){
            mgsNum.setText(""+listUser.getMgsNum());
        }else {
            mgsNum.setText("");
        }

        int photo = temp.getPhotoType();
        switch (photo){
            case 0:
                friendImage.setImageResource(R.drawable.f0);
                break;
            case 1:
                friendImage.setImageResource(R.drawable.f1);
                break;
            case 2:
                friendImage.setImageResource(R.drawable.f2);
                break;
            case 3:
                friendImage.setImageResource(R.drawable.f3);
                break;
            case 4:
                friendImage.setImageResource(R.drawable.f4);
                break;
            case 5:
                friendImage.setImageResource(R.drawable.f5);
                break;
            case 6:
                friendImage.setImageResource(R.drawable.f6);
                break;
            case 7:
                friendImage.setImageResource(R.drawable.f7);
                break;
            case 8:
                friendImage.setImageResource(R.drawable.f8);
                break;
            case 9:
                friendImage.setImageResource(R.drawable.f9);
                break;
            case 10:
                friendImage.setImageResource(R.drawable.f10);
                break;
            case 11:
                friendImage.setImageResource(R.drawable.f11);
                break;
        }
        String name = temp.getName() + " ( " + ((Double)temp.getID()).toString() + " )";
        friendName.setText(name);
        return view;
    }
}
