import java.io.*;
import java.io.ObjectOutputStream.PutField;
import java.net.Socket;
import java.net.SocketException;

public class TCPSession implements Runnable{
    private Socket client = null;

    public TCPSession(Socket socket){
	System.out.println("Starting connection");
	this.client = socket;
    }
    public void run(){
	try{
	    client.getOutputStream().write("Connected".getBytes());
	    byte[] data = new byte[1024];
 	    client.getInputStream().read(data);
	    String output = new String(data);
	    System.out.println(output);
	    client.getOutputStream().write("Message Recived".getBytes());
	    client.close();
	}
	catch(Exception e){
	    System.out.println("done fucked up in session");
	}
    }
}
	
