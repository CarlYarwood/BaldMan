import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

//if have time implement this as a runnable for multi threading
public class BaldMan{

    private String imagePath = null;
    private String message = null;
    private String messagePath = null;

    public BaldMan(){
    }
    
    
    public void putMessageInImage(String newImageName){
	if(imagePath == null){
	    System.out.println("Must set imagePath");
	    return;
	}
	else if ( message == null && messagePath == null){
	    System.out.println("must set message or message Path");
	    return;
	}
	byte[] encode = getMessage();
	if(encode == null){
	    System.out.println("Message Not found");
	    return;
	}
	byte [] image =  convertImage(getImageCopy(getImage()));
	
    }


    
    public void getMessageOutOfImage(String messageFile){

    }
    public void setImagePath(String imagePath){
	this.imagePath = imagePath;
    }


    
    public void setMessagePath(String messagePath){
	this.messagePath = messagePath;
	this.message = null;
    }


    
    public void setMessage(String message){
	this.message = message;
	this.messagePath = null;
    }




    
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


    
    private BufferedImage getImageCopy(BufferedImage image){
	BufferedImage imageCopy = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
	Graphics2D draw = imageCopy.createGraphics();
	draw.drawRenderedImage(image,null);
	draw.dispose();
	return imageCopy;
    }

    
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
    
    //takes and int and convertes into a byta array by shifting the int
    // and anding it with the byte 11111111, as 1 and 1 = 1, and 1 and 0 = 0
    private byte[] convertInt(int i){
	ByteBuffer buff = ByteBuffer.allocate(4);
	buff.putInt(i);
	return buff.array();
    }


    
    private int convertBackInt(byte[] num){
	ByteBuffer buff = ByteBuffer.wrap(num);
	return buff.getInt();
    }
    private byte[] convertImage(BufferedImage img){
	WritableRaster raster = img.getRaster();
	DataBufferByte buffer = (DataBufferByte) raster.getDataBuffer();
	return buffer.getData();
    }
	
	

}
