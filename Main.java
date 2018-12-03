import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileInputStream;
public class Main{

    public static void main(String[] args){
	boolean keepGoing = true;
	BaldMan stego = new BaldMan();
	AESEncryption aes = new AESEncryption();
	Scanner scan = new Scanner(System.in);
	String command = "";
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
	    }
	    else if(command.equals("set messagePath")){
		    System.out.print("type messagePath: ");
		    String messagePath = scan.nextLine();
		    stego.setMessagePath(messagePath);
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
		System.out.print("enter a name for the new image where the message will be hidden: ");
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
	    else if(command.equals("set key")){
		System.out.print("Type what you would like your key to be");
		String key = scan.nextLine();
		aes.setKey(key);
	    }
	    else if(command.equals("sendFile")){
		System.out.print("Enter the number of files you would like to send: ");
		int numFiles = scan.nextInt();
		String dummy = scan.nextLine();
		System.out.print("Enter the port you wold like to use: ");
		int port = scan.nextInt();
		dummy = scan.nextLine();
		String[] fileNames = new String[numFiles];
		for(int i = 0; i < numFiles; i++){
		    System.out.print("Enter the name of file " + i + ": ");
		    fileNames[i] = scan.nextLine();
		}
		TCPServerFile fs = new TCPServerFile();
		fs.createSocket(port);
		for(int i = 0; i < numFiles; i++){
		    fs.sendFile(fileNames[i]);
		}
		fs.shutDown();
	    }
	    else if(command.equals("getFile")){
		System.out.print("Enter the number of Files you would like to receive: ");
		int numFile = scan.nextInt();
		String dummy = scan.nextLine();
		System.out.print("Enter the ip address you want to receive form: ");
		String ip = scan.nextLine();
		System.out.print("Enter the port of connection: ");
		int port = scan.nextInt();
		dummy = scan.nextLine();
		System.out.print("Enter the Name of the new file you want to get: ");
		String[] newFileName = new String[numFile];
		for(int i = 0; i < numFile; i++){
		    System.out.print("Enter the name of recived File " + i +": ");
		    newFileName[i] = scan.nextLine();
		}
		TCPClientFile soc = new TCPClientFile();
		soc.createSocket(ip, port);
		for(int i = 0; i< numFile; i++){
		    soc.receiveFile(newFileName[i]);
		}
		soc.shutDown();
	    }
	    else if(command.equals("readKeyFromFile")){
		System.out.print("Enter path to file containing key: ");
		String keyFileName = scan.nextLine();
		File file = new File(keyFileName);
		FileInputStream fis = null;
		byte[] content = null;
		try{
		    fis = new FileInputStream(file);
		    content = new byte[(int) file.length()];
		    fis.read(content);
		}catch(Exception e){
		    System.out.println("Could not read file");
		}finally{
		    try{
			if(fis != null){
			    fis.close();
			}
		    }catch(Exception e){
			System.out.println("Issue closing File");
		    }
		}
		String key = new String(content);
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
		System.out.println("set key");
		System.out.println("putMessageInImage");
		System.out.println("getMessageOutOfImage");
		System.out.println("encrypt");
		System.out.println("decrypt");
		System.out.println("sendFile");
		System.out.println("getFile");
		System.out.println("readKeyFromFile");
	    }	
	    else{
		System.out.println("Use -h to get a list of valid commands");
	    }
	    
	}
    }

}
