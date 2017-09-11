package Adapter;

/**
 * Created by linqijun on 2017/4/7.
 */

public class SelfMessgaeForMgsA {
    private String mgs;
    private int type; // 0 为收到 1为自己发送

    public SelfMessgaeForMgsA(String mgs , int type){
        this.mgs = mgs;
        this.type = type;
    }

    public String getContent(){
        return mgs;
    }

    public int getType(){
        return type;
    }

}
