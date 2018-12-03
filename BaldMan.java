import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.Raster;
import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;



/**
BaldMan is a Stegnography class used for hiding files or messages in png images,
it is meant to be easily implemented in a commandline or gui enviorment
 */
public class BaldMan{

    private String imagePath = null;
    private String message = null;
    private String messagePath = null;
    private String messageDestinationPath = null;
    private Bits bitSteg = Bits.ONE;

    public BaldMan(){
    }
    /**
     *Prints the current state of the program to the console
     */
    public void getState(){
	System.out.println("imagePath => " + imagePath);
	System.out.println("message => " + message);
	System.out.println("messagePath => " + messagePath);
	System.out.println("messageDestinationPath => " + messageDestinationPath);
	System.out.println("bitSteg => " + bitSteg);
    }
    /**
     *Uses the current state of the program to try to put a message in an image
     *@param String representing path to new image
    */
    
    public void putMessageInImage(String newImageName){
	if(imagePath == null){
	    System.out.println("Must set imagePath");
	    return;
	}
	else if ( message == null && messagePath == null){
	    System.out.println("must set message or message Path");
	    return;
	}
	byte[] encodeMessage = getMessage();
	if(encodeMessage == null){
	    System.out.println("Message Not found");
	    return;
	}
	BufferedImage img = getImageCopy(getImage());
	byte [] image =  convertImage(img);
	if(bitSteg == Bits.ONE){
	    System.out.println("This image can hold " + image.length + " bits.");
	}
	else if(bitSteg == Bits.TWO){
	    System.out.println("This image can hold " + image.length * 2 + " bits.");
	}
	else if(bitSteg == Bits.FOUR){
	    System.out.println("This image can hold " + image.length * 4 + " bits.");
	}
	System.out.println("Your message is "+ encodeMessage.length * 8 + " bits.");
	if(image == null){
	    System.out.println("could not get image");
	    return;
	}
	try{
	    encodeMessage(encodeMessage, image);
	}catch(IOException e){
	    System.out.println("Message too large for image");
	    return;
	}
	try{
	    ImageIO.write(img,"png",new File(newImageName));
	}catch(Exception e){
	    System.out.println("could not write file");
	    return;
	}
	
	
    }


    /**
     *uses the current state of the program to 
     *try and pull an image form a message
    */
    
    public void getMessageOutOfImage(){
	byte letIn = 0;
	byte divisor = 1;
	if(imagePath == null){
	    System.out.println("Must set an Image Path");
	}
	if(bitSteg == Bits.ONE){
	    divisor = 1;
	    letIn = 1;
	}
	else if (bitSteg == Bits.TWO){
	    divisor = 2;
	    letIn = 3;
	}
	else if (bitSteg == Bits.FOUR){
	    divisor = 4;
	    letIn = 15;
	}
	byte[] img = convertImage(getImage());
	int length = 0;
	int posInImage = 0;
	for(int i = 0; i<(32/divisor); i++){
	    length =length << divisor;
	    length = length | img[i] & letIn;
	    posInImage++;
	}
	byte[] msg = new byte[length/(8/divisor)];
	for(int i = 0; i < msg.length; i++){
	    for(int c = 0; c< (8/divisor); c++){
		msg[i] = (byte)(msg[i] << divisor);
		msg[i] = (byte)(msg[i]| (img[posInImage]& letIn));
		posInImage++;
	    }
	}
	if(messageDestinationPath == null){
            String message = new String(msg);
	    System.out.println(message);
	}
	else{
	    try(FileOutputStream fos = new FileOutputStream(messageDestinationPath)){
		fos.write(msg);
	    }catch(Exception e){
		System.out.println("cannot write message to destination");
	    }
	}
    }
    
    /**
     *Sets imagePath in state of program
     *@param string representing path to image
     */	    
    public void setImagePath(String imagePath){
	this.imagePath = imagePath;
    }
    /**
     *Sets the path to the ouput file for message
     *@param string representing message output
     */

    public void setMessageDestinationPath(String Path){
	this.messageDestinationPath = Path;
    }
    /**
     *sets path to message
     *@param string represent path to file holding message
     */
    public void setMessagePath(String messagePath){
	this.messagePath = messagePath;
	this.message = null;
    }
    /**
     *sets Message
     *@param String representing message
     */
    public void setMessage(String message){
	this.message = message;
	this.messagePath = null;
    }
    /**
     *set type of stnography, leastSignificant, two least, four least
     *@parm Bit enum which represents type of encryption
     */
    public void setStegBits( Bits b){
	this.bitSteg = b;
    }
    /**
     *gets message based on object state
     *@return byte[] for message
     */
    private byte[] getMessage(){
	byte[] content = null;
	if(messagePath == null && message == null){
	    System.out.println("need to set message or messagePath");
	    return null;
	}
	else if( messagePath == null){
	    return message.getBytes();
	}
	else{
	    File file = new File(messagePath);
	    FileInputStream fis = null;
	    try{
		fis = new FileInputStream(file);
		content = new byte[(int)file.length()];
		fis.read(content);
	    }catch(FileNotFoundException e){
	        System.out.println("File not found");
		return null;
	    }catch(IOException e){
		System.out.println("Early IOException");
	    }
	    finally{
		try{
		    if(fis != null){
			fis.close();
		    }
		}
		catch(IOException e){
		    System.out.println("IOException");
		}
	    }
	    return content;
	}
    }
    /**
     *gets copy of buffer image
     *@param image to be coppied
     *@return copy of image
     */
    private BufferedImage getImageCopy(BufferedImage image){
	BufferedImage imageCopy = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
	Graphics2D draw = imageCopy.createGraphics();
	draw.drawRenderedImage(image,null);
	draw.dispose();
	return imageCopy;
    }
    /**
     *gets buffered image base on program state
     *@return image
     */
    private BufferedImage getImage(){
	BufferedImage img = null;
	try{
	    File imageFile = new File(imagePath);
	    img = ImageIO.read(imageFile);
	}catch(FileNotFoundException e){
	    System.out.println("Image File Not Found");
	    return null;
	}catch(IOException e){
	    System.out.println("Error on Read Try New Image");
	    return null;
	}
	return img;
    }
    /**
     *converts int ot byte array
     *@param int to be converted
     *@return byte[] array for int
     */
    private byte[] convertInt(int i){
	ByteBuffer buff = ByteBuffer.allocate(4);
	buff.putInt(i);
	return buff.array();
    }
    /**
     *Converts a byte array to an int
     *@param byte array to be converted
     *@return int
     */
    private int convertBackInt(byte[] num){
	ByteBuffer buff = ByteBuffer.wrap(num);
	return buff.getInt();
    }
    /**
     *converts bufferd image to byte array representing pixels with Raster
     *@param image to be converted
     *@return byte[] representing image
     */
    private byte[] convertImage(BufferedImage img){
        Raster raster = (Raster)img.getRaster();
	DataBufferByte buffer = (DataBufferByte) raster.getDataBuffer();
	return buffer.getData();
    }
    /**
     *does the heavy lifting of putting the message into the image
     *@param byte array representing message
     *@param byte array representing image
     */
    private void encodeMessage(byte[] message, byte[] img)throws IOException{
	byte mask = 0;
	byte letIn = 0;
	int divisor = 1;
	if(bitSteg == Bits.ONE){
	    mask = (byte)254;
	    letIn = 1;
	    divisor = 1;
	}
	else if(bitSteg == Bits.TWO){
	    mask = (byte)252;
	    letIn = 3;
	    divisor = 2;
	}
	else if (bitSteg == Bits.FOUR){
	    mask = (byte)240;
	    letIn = 15;
	    divisor = 4;
	}
	if(img.length < (message.length * (8/divisor) + (32/divisor))){
	    System.out.println("Message too large for given image");
	    throw new IOException("image not big enough for message");
	}
	byte[] messageLength = convertInt(message.length * (8/divisor));
	int currentPosInImage = 0;
	for(int i = 0; i< 4; i++){
	    for(int bits = (8/divisor) - 1; bits >= 0 ; bits--){
		img[currentPosInImage] =(byte)(img[currentPosInImage] & mask);
		img[currentPosInImage] =(byte) (img[currentPosInImage] | ((messageLength[i] >> (bits * divisor)) & letIn ));
		currentPosInImage ++;
	    }
	}
	for(int i = 0; i< message.length; i++){
	    for(int bits = (8/divisor) - 1 ; bits >= 0 ; bits--){
		img[currentPosInImage] =(byte)(img[currentPosInImage] & mask);
		img[currentPosInImage] =(byte) (img[currentPosInImage] | ((message[i] >> (bits*divisor)) & letIn ));
		currentPosInImage ++;
	    }
	}
    }	
}
