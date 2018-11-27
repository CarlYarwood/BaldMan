import java.io.*;
import java.io.ObjectOutputStream.PutField;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer{
    private ServerSocket serverSocket = null;
    private DataInputStream inStream = null;
    private DataOutputStream outStream = null;
    private int port ;

    public TCPServer(int Port){
	this.port = Port;
    }

    public void createSocket()
    {
	try{
	    serverSocket = new ServerSocket(port);
	    System.out.println("Server Created ");
	}
	catch ( IOException io){
	    io.printStackTrace();
	}
    }
    public void listenForConnection(){
	try{
	    System.out.println("Server Listeneing");
	    Thread t = new Thread(new TCPSession(serverSocket.accept()));
	    t.run();
	}
	catch(Exception e){
	    System.out.println("Done fucked up on session creation");
	}
    }

}
