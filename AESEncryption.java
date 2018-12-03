import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class AESEncryption {
	
	private static String theKey = "default";
	private static String salt = "E1F53135E559C253";
	
	public AESEncryption() {
	}
	
	public void setKey(String key) {
		theKey = key;
	}
	
	public static byte[] encrypt(byte[] byteToEncrypt ) {
		
		try {
			//This initializes the 16 byte of plain-text to be used in the 4x4 state matrix for AES to zero
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			//This specifies the initialization vector that we use for DES in Cipher Block Chaining mode 
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			//Password-Based Key Derivation function with Hash-based message Authentication code using SHA-256 as the secure hash Algorithm
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			//Constructor that takes the password, the byte array of the salt, the iteration int, and the key length)
			KeySpec spec = new PBEKeySpec(theKey.toCharArray(), salt.getBytes(), 65536, 256);
			//generates a secretKey object from the key specification 
			SecretKey temp = factory.generateSecret(spec);
			//Constructs the secret key given the hashed key
			SecretKeySpec secretKey = new SecretKeySpec(temp.getEncoded(), "AES");
			//Creating a Cipher using the AES algorithm in CBC mode with PKCS5Padding
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			//Initializes the cipher to encrypt the secret key to the Initialization Vector
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

			//return Base64.getEncoder().encode(cipher.doFinal(byteToEncrypt));
			return cipher.doFinal(byteToEncrypt);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] decrypt(byte[] byteToDecrypt ) {
	    try
	    {
	        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	        IvParameterSpec ivspec = new IvParameterSpec(iv);
	         
	        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	        KeySpec spec = new PBEKeySpec(theKey.toCharArray(), salt.getBytes(), 65536, 256);
	        SecretKey temp = factory.generateSecret(spec);
	        SecretKeySpec secretKey = new SecretKeySpec(temp.getEncoded(), "AES");
	         
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
	       // return cipher.doFinal(Base64.getDecoder().decode(byteToDecrypt));
	        return cipher.doFinal(byteToDecrypt);
	    }
	    catch (Exception e) {
	        System.out.println("Error while decrypting: " + e.toString());
	    }
	    return null;
	}
	
	public byte[] getImageBytes(String imagePath) {
		byte[] content = null;
		 File file = new File(imagePath);
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

