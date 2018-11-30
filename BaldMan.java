import java.io.File;
import java.io.InputFileStream;
import java.io.FileNotFoundException;
import java.io.IOException;
public class BaldMan{

    private string imagePath = null;
    private string message = null;
    private string messagePath = null;
    //method not done
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
	
	
    }
    public void getMessageOutOfImage(String messageFile){

    }

    public void setImagePath(String imagePath){
	this.imagepath = imagePath;
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
		fis = new FileInputStream(fis);
		content = new byte[(int)file.length()];
		fis.read(content);
	    }catch(FileNotFoundException e){
	        System.out.println("File not found");
		return null;
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
    //takes and int and convertes into a byta array by shifting the int
    // and anding it with the byte 11111111, as 1 and 1 = 1, and 1 and 0 = 0
    private convertInt(int i){
	byte[] transfer = new byte[4];
	transfer[0] = (byte) (i >> 24) & 0xFF;
	transfer[1] = (byte) (i >> 16) & 0xFF;
	transfer[2] = (byte) (i >> 8) & 0xFF;
	transfer[3] = (byte) (i) & 0xFF;
	return transfer;
    }
	

}
