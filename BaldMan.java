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

}
