import java.net.Socket;
import java.io.*;

public class P2PClient {
	
	private Socket socket = null;
	private int port;
	
	P2PClient(int port) {
		this.port = port;
	}
	
	
	public static void main(String[] args) {		
		P2PClient client = new P2PClient(3389);
		client.createSocket();
	}

	
	public void createSocket() {
		 try 
	        {
			 	byte [] buffer = new byte[1024];
	            socket = new Socket( "150.243.160.92", port);
	            System.out.println("Connected");
	            InputStream input = socket.getInputStream();
	            OutputStream output = socket.getOutputStream();
	            
	            input.read(buffer);
	            
	            String s = new String(buffer);
	            System.out.println(s);
	            output.write("Hello".getBytes());
	            
	            input.read(buffer);
	            s = new String(buffer);
	            System.out.print(s);
	        } 
		 
	        catch (Exception u) 
	        {
	            System.out.println("not connected");
	        } 
	}

}