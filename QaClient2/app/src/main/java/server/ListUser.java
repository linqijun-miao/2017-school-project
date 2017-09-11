package server;

/**
 * Created by linqijun on 2017/4/9.
 */

public class ListUser {
    User u;
    int mgsNum = 0;

    public ListUser(User u){
        this.u = u;

    }
    public User getUser(){
        return u;
    }

    public void setNum (int a){
        mgsNum = a;
    }

    public int getMgsNum(){
        return mgsNum;
    }
}
