import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryption {

	private static String theKey = "woahthisissecret";
	private static String salt = "thisisamessage";
	
	public static String encrypt(String strToEncrypt, String secret) {
		
		try {
			//This initializes the 16 byte of plain-text to be used in the 4x4 state matrix for AES to zero
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			//This specifies the initialization vector that we use for DES in Cipher Block Chaining mode 
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			//Password-Based Key Derivation function with Hash-based message Authentication code using SHA-256 as the secure hash Algorithm
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			//Constructor that takes the password, the byte array of the salt, the iteration sound, and the key length)
			KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), 65536, 256);
			//generates a secretKey object from the key specification 
			SecretKey temp = factory.generateSecret(spec);
			//Constructs the secret key given the hashed key
			SecretKeySpec secretKey = new SecretKeySpec(temp.getEncoded(), "AES");
			//Creating a Cipher using the AES algorithm in CBC mode with PKCS5Padding
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			//Initializes the cipher to encrypt the secret key to the Initialization Vector
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String decrypt(String strToDecrypt, String secret) {
	    try
	    {
	        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	        IvParameterSpec ivspec = new IvParameterSpec(iv);
	         
	        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	        KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), 65536, 256);
	        SecretKey temp = factory.generateSecret(spec);
	        SecretKeySpec secretKey = new SecretKeySpec(temp.getEncoded(), "AES");
	         
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
	        return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
	    }
	    catch (Exception e) {
	        System.out.println("Error while decrypting: " + e.toString());
	    }
	    return null;
	}
	
	public static void main(String[] args)
	{
	    String originalString = "test";
	     
	    String encryptedString = AESEncryption.encrypt(originalString, theKey) ;
	    String decryptedString = AESEncryption.decrypt(encryptedString, theKey) ;
	      
	    System.out.println(originalString);
	    System.out.println(encryptedString);
	    System.out.println(decryptedString);
	}
	
	
}

