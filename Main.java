import java.util.Scanner;
public class Main{

    public static void main(String[] args){
	//Scanner scan = new Scanner(System.in);
	//	System.out.print("Enter the port you would like your server on: ");
	//	int port = scan.nextInt();
	//TCPServer server = new TCPServer(port);
	//server.createSocket();
	//	while(true){
	//   server.listenForConnection();
	//	}
	BaldMan stego = new BaldMan();
	stego.setImagePath("red.png");
        stego.setMessagePath("littleWizzard.png");
	stego.setMessageDestinationPath("outLittleWizzard.png");
	stego.setStegBits(Bits.FOUR);
	stego.putMessageInImage("secretRed.png");
	stego.setImagePath("secretRed.png");
	stego.getMessageOutOfImage();
    }

}
