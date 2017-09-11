package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.linqijun.qaclient.R;

import java.util.List;

/**
 * Created by linqijun on 2017/4/7.
 */

public class MgsAdapter extends ArrayAdapter<SelfMessgaeForMgsA> {
    private int resourceId;
    public MgsAdapter(Context context, int textViewResourceId , List<SelfMessgaeForMgsA> objects) {
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SelfMessgaeForMgsA selfMessgaeForMgsA = getItem(position);
        View view;
        ViewHolder viewHolder;

        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.leftLayout = (LinearLayout) view.findViewById(R.id.chat_list_view_left);
            viewHolder.rightLayout = (LinearLayout) view.findViewById(R.id.chat_list_view_right);
            viewHolder.leftMgs = (TextView) view.findViewById(R.id.chat_mgs_left);
            viewHolder.rightMgs = (TextView) view.findViewById(R.id.chat_mgs_right);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        //发送隐藏左边，接受隐藏右边
        if(selfMessgaeForMgsA.getType() == 0) {
            viewHolder.leftLayout.setVisibility(View.VISIBLE);
            viewHolder.rightLayout.setVisibility(View.GONE);
            viewHolder.leftMgs.setText(selfMessgaeForMgsA.getContent());

        }else{
            viewHolder.rightLayout.setVisibility(View.VISIBLE);
            viewHolder.leftLayout.setVisibility(View.GONE);
            viewHolder.rightMgs.setText(selfMessgaeForMgsA.getContent());
        }
        return view;
    }

    class ViewHolder{

        LinearLayout leftLayout;

        LinearLayout rightLayout;

        TextView leftMgs;

        TextView rightMgs;

    }
}
