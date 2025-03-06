// Update if being necessary
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

// ** This class is useful for question (e)

public final class Util {

	/** Ensure that we will not create an instance of this class */
	private Util() {
		
	}

	/**
	 * Creates Hash using SHA-256 algorithm
	 * 
	 * @param str
	 * 		The given sequence of characters
	 * @return
	 * 		String Hash
	 * 
	 * @see https://www.baeldung.com/sha-256-hashing-java
	 */
	public static String getHash256(final String str) {
		if (str == null) return null;
		try {
			final MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
			return bytesToHex(encodedhash);
		} catch(NoSuchAlgorithmException e) {
			throw new RuntimeException("getHash256() problem !", e);
		}
	}
	
	private static String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}

	/** 
	 * For testing purposes...
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("For testing purposes...\n");
		
		final List<String> passList = new ArrayList<String>();
		passList.add("pa");
		passList.add("pb");
		
		for (String pass : passList) {
			System.out.println("String: " + pass + " --> HASH: " + getHash256(pass));
		}
		
	}
	
}
