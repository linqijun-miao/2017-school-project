package server;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketImpl;
import java.net.UnknownHostException;

/**
 * Created by linqijun on 2017/4/2.
 */

public class MySocket extends Socket implements Serializable {

    public MySocket() {
    }

    public MySocket(Proxy proxy) {
        super(proxy);
    }

    public MySocket(SocketImpl impl) throws SocketException {
        super(impl);
    }

    public MySocket(String host, int port) throws IOException {
        super(host, port);
    }

    public MySocket(InetAddress address, int port) throws IOException {
        super(address, port);
    }

    public MySocket(String host, int port, InetAddress localAddr, int localPort) throws IOException {
        super(host, port, localAddr, localPort);
    }

    public MySocket(InetAddress address, int port, InetAddress localAddr, int localPort) throws IOException {
        super(address, port, localAddr, localPort);
    }
}
