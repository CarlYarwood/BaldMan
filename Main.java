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
	boolean keepGoing = true;
	BaldMan stego = new BaldMan();
	Scanner scan = new Scanner(System.in);
	String command = "";
	String subCommand = "";
	String CommandLineState = "BaldManStego(Command): ";
	while(keepGoing){
	    System.out.print(CommandLineState);
	    command = scan.nextLine();
	    if(command.equals("state")){
		stego.getState();
	    }
	    else if(command.equals("quit") || command.equals("q")){
		keepGoing = false;
	    }
	    else if(command.equals("set imagePath")){
		System.out.print("type imagePath: ");
		subCommand = scan.nextLine();
		stego.setImagePath(subCommand);
		subCommand = "";
	    }
	    else if(command.equals("set messagePath")){
		    System.out.print("type messagePath: ");
		    subCommand = scan.nextLine();
		    stego.setMessagePath(subCommand);
		    subCommand = "";
	    }
	    else if(command.equals("set message")){
		System.out.print("type message: ");
		subCommand = scan.nextLine();
		stego.setMessage(subCommand);
		subCommand = "";
	    }
	    else if(command.equals("set messageDestinationPath")){
		System.out.print("type messageDestinagionPath: ");
		subCommand = scan.nextLine();
		stego.setMessageDestinationPath(subCommand);
		subCommand = "";
	    }
	    else if(command.equals("set bitSteg")){
		System.out.print("specify ONE, TWO, or FOUR: ");
		subCommand = scan.nextLine();
		if(subCommand.equals("ONE")){
		    stego.setStegBits(Bits.ONE);
		}else if(subCommand.equals("TWO")){
		    stego.setStegBits(Bits.TWO);
		}else if(subCommand.equals("FOUR")){
		    stego.setStegBits(Bits.FOUR);
		}else{
		    System.out.println("no valid entry made");
		}
	    }
	    else if(command.equals("putMessageInImage")){
		System.out.println("enter a name for the image with a hidden message: ");
		subCommand = scan.nextLine();
		stego.putMessageInImage(subCommand);
		subCommand = "";
	    }
	    else if(command.equals("getMessageOutOfImage")){
		stego.getMessageOutOfImage();
	    }
	    else if(command.equals("-h")){
		System.out.println("state (Recomended)");
		System.out.println("quit");
		System.out.println("set imagePath");
		System.out.println("set messagePath");
		System.out.println("set message");
		System.out.println("set messageDestinationPath");
		System.out.println("set bitSteg");
		System.out.println("putMessageInImage");
		System.out.println("getMessageOutOfImage");
	    }
	    else{
		System.out.println("Use -h to get a list of valid commands");
	    }
	    
	}
	//stego.setImagePath("red.png");
        //stego.setMessagePath("DickWizzard.png");
	//stego.setMessageDestinationPath("outWizzard.png");
	//stego.setStegBits(Bits.FOUR);
	//stego.putMessageInImage("secretRed.png");
	//stego.setImagePath("secretRed.png");
	//stego.getMessageOutOfImage();
    }

}
