import java.util.Scanner;
import java.io.FileOutputStream;
public class Main{

    public static void main(String[] args){
	boolean keepGoing = true;
	BaldMan stego = new BaldMan();
	AESEncryption aes = new AESEncryption();
	Scanner scan = new Scanner(System.in);
	String command = "";
	String subCommand = "";
	String CommandLineState = "BaldManStego(Command): ";
	while(keepGoing){
	    System.out.print(CommandLineState);
	    command = scan.nextLine();
	    if(command.equals("state")){
		stego.getState();
		aes.getState();
	    }
	    else if(command.equals("quit") || command.equals("q")){
		keepGoing = false;
	    }
	    else if(command.equals("set imagePath")){
		System.out.print("type imagePath: ");
		String imagePath = scan.nextLine();
		stego.setImagePath(imagePath);
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
		String message = scan.nextLine();
		stego.setMessage(message);
	    }
	    else if(command.equals("set messageDestinationPath")){
		System.out.print("type messageDestinagionPath: ");
		String MessageDestination = scan.nextLine();
		stego.setMessageDestinationPath(MessageDestination);
	    }
	    else if(command.equals("set bitSteg")){
		System.out.print("specify ONE, TWO, or FOUR: ");
		String newBitSteg = scan.nextLine();
		if(newBitSteg.equals("ONE")){
		    stego.setStegBits(Bits.ONE);
		}else if(newBitSteg.equals("TWO")){
		    stego.setStegBits(Bits.TWO);
		}else if(newBitSteg.equals("FOUR")){
		    stego.setStegBits(Bits.FOUR);
		}else{
		    System.out.println("no valid entry made");
		}
	    }
	    else if(command.equals("putMessageInImage")){
		System.out.println("enter a name for the new image where the message will be hidden: ");
		String newImgFileName = scan.nextLine();
		stego.putMessageInImage(newImgFileName);
	    }
	    else if(command.equals("getMessageOutOfImage")){
		stego.getMessageOutOfImage();
	    }
	    else if(command.equals("encrypt")){
		System.out.print("enter file you would like encrypt: ");
		String encryptFile = scan.nextLine();
		System.out.print("enter the name of the new encrypted file: ");
		String newFileName = scan.nextLine();
		byte[] content = aes.encrypt(aes.getImageBytes(encryptFile));
		try(FileOutputStream fos = new FileOutputStream(newFileName)){
		    fos.write(content);
		}catch(Exception e){
		    System.out.println("could not write file");
		}
	    }
	    else if(command.equals("decrypt")){
		System.out.print("enter the name of the file you would like to decrypt: ");
		String decryptFile = scan.nextLine();
		System.out.print("enter the name of the file you would like to store the new decrypted file in: ");
		String newFile = scan.nextLine();
		byte[] content = aes.decrypt(aes.getImageBytes(decryptFile));
		try(FileOutputStream fos = new FileOutputStream(newFile)){
		    fos.write(content);
		}catch(Exception e){
		    System.out.println("could not write file");
		}
	    }
	    else if(command.equals("setKey")){
		System.out.print("Type what you would like your key to be");
		String key = scan.nextLine();
		aes.setKey(key);
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
		System.out.println("encrypt");
		System.out.println("decrypt");
		System.out.println("setKey");
	    }	
	    else{
		System.out.println("Use -h to get a list of valid commands");
	    }
	    
	}
    }

}
