//
//  TGServerThread.java
//
//  Written by : Priyank Patel <pkpatel@cs.stanford.edu>
//
//  Accepts connection requests and processes them
package Chat;

// socket
import java.net.*;
import java.io.*;

// Swing
import javax.swing.JTextArea;

//  Crypto
import java.security.*;
import java.security.spec.*;
import java.security.interfaces.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import javax.crypto.interfaces.*;

public class TGServerThread extends Thread {

    private TGServer _tgs;
    private ServerSocket _serverSocket = null;
    private int _portNum;
    private String _hostName;
    private JTextArea _outputArea;

    public TGServerThread(TGServer tgs) {

        super("AuthServerThread");
        _tgs = tgs;
        _portNum = tgs.getPortNumber();
        _outputArea = tgs.getOutputArea();
        _serverSocket = null;

        try {

            InetAddress serverAddr = InetAddress.getByName(null);
            _hostName = serverAddr.getHostName();

        } catch (UnknownHostException e) {
            _hostName = "0.0.0.0";
        }
    }
    
    //  Accept connections and service them one at a time
    public void run() {
        try {
            _serverSocket = new ServerSocket(_portNum);
            _outputArea.append("AS waiting on " + _hostName + " port " + _portNum);
            while (true) {
                Socket socket = _serverSocket.accept();
                //
                //  Got the connection, now do what is required
                //  
            }
        } catch (Exception e) {
            System.out.println("AS thread error: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
