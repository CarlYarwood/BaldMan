public class Main{

    public static void main(String[] args){
	TCPServer server = new TCPServer(3389);
	server.createSocket();
	while(true){
	    server.listenForConnection();
	}
    }

}
