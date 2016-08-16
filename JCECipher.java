package stegano.image.processing;

import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

public class JCECipher {
	private final javax.crypto.Cipher cipher;
	private SecretKeySpec spec;
	private SecretKey skey;
	private final String name;
	private final String keygenAlgorithm;
	private final int defaultKeysize;

	
	public JCECipher(int keysize, String keygenAlgorithm, String transformation)
			throws NoSuchAlgorithmException, NoSuchPaddingException {
		this.name = transformation;
		this.keygenAlgorithm = keygenAlgorithm;
		this.defaultKeysize = keysize;
		initKey();
		cipher = javax.crypto.Cipher.getInstance(transformation);
	}

	public String encrypt(String plainText) {
		try {
			cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, spec);
			byte[] encrypted = cipher.doFinal(plainText.getBytes());
			return convertByteToHex(encrypted);
		} catch (InvalidKeyException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Invalid Key",
					JOptionPane.ERROR_MESSAGE);
		} catch (IllegalBlockSizeException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),
					"Illegal Blocksize", JOptionPane.ERROR_MESSAGE);
		} catch (BadPaddingException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Bad Padding",
					JOptionPane.ERROR_MESSAGE);
		}
		return "error";
	}

	public String decrypt(String code) {
		try {
			cipher.init(javax.crypto.Cipher.DECRYPT_MODE, spec);
			byte[] decrypted = cipher.doFinal(convertHextoByte(code));
			return new String(decrypted);
		} catch (InvalidKeyException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Invalid Key",
					JOptionPane.ERROR_MESSAGE);
		} catch (IllegalBlockSizeException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),
					"Illegal Blocksize", JOptionPane.ERROR_MESSAGE);
		} catch (BadPaddingException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Bad Padding",
					JOptionPane.ERROR_MESSAGE);
		}
		return "error";
	}

	
	
	public static String convertByteToHex(byte array[]) {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			if ((array[i] & 0xff) < 0x10) {
				buffer.append("0");
			}
			buffer.append(Integer.toString(array[i] & 0xff, 16));
		}
		return buffer.toString();
	}

	/**
	 * Helping method to convert a hex String to a byte array
	 * 
	 * @param hexString
	 * @return
	 */
	public static byte[] convertHextoByte(String hexString) {
		char[] hex = hexString.toCharArray();
		byte[] result = new byte[hex.length / 2];
		for (int i = 0; i < result.length; i++) {
			result[i] = (byte) ((Character.digit(hex[i * 2], 16) << 4) + Character
					.digit(hex[i * 2 + 1], 16));
		}
		return result;
	}

	
	public static Set<String> getCryptImplementations(String serviceType) {
		Set<String> result = new HashSet<String>();

		for (Provider provider : Security.getProviders()) {
			Set<Object> keys = provider.keySet();
			for (Object k : keys) {
				String key = ((String) k).split(" ")[0];

				if (key.startsWith(serviceType + ".")) {
					result.add(key.substring(serviceType.length() + 1));
				} else if (key.startsWith("Alg.Alias." + serviceType + ".")) {
					result.add(key.substring(serviceType.length() + 11));
				}
			}
		}
		return result;
	}

	
	public static Set<String> getServiceTypes() {
		Set<String> result = new HashSet<String>();

		for (Provider provider : Security.getProviders()) {
			Set<Object> keys = provider.keySet();
			for (Object k : keys) {
				String key = ((String) k).split(" ")[0];

				if (key.startsWith("Alg.Alias.")) {
					key = key.substring(10);
				}
				int ix = key.indexOf('.');
				result.add(key.substring(0, ix));
			}
		}
		return result;
	}

	
	private void initKey() throws NoSuchAlgorithmException {
		KeyGenerator keygen = KeyGenerator.getInstance(keygenAlgorithm);
		keygen.init(defaultKeysize);
		skey = keygen.generateKey();
		spec = new SecretKeySpec(skey.getEncoded(), keygenAlgorithm);
	}

	public String generateKey(int keysize) {
		try {
			KeyGenerator keygen = KeyGenerator.getInstance(keygenAlgorithm);
			keygen.init(keysize);
			skey = keygen.generateKey();
			spec = new SecretKeySpec(skey.getEncoded(), keygenAlgorithm);
			return convertByteToHex(skey.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),
					"Algorithm not found", JOptionPane.ERROR_MESSAGE);
		} catch (InvalidParameterException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),
					"Invalid Parameter", JOptionPane.ERROR_MESSAGE);
		}
		return "error";
	}

	public void setKey(String key) {
		spec = new SecretKeySpec(convertHextoByte(key), keygenAlgorithm);
	}
	
	public String generateKeyByPassword(String password) {
		try {
			SecureRandom rand = SecureRandom.getInstance("SHA1PRNG");  
			byte[] salt = new byte[16];  
			rand.nextBytes(salt);  
			PBEKeySpec pass = new PBEKeySpec(password.toCharArray(), salt, 1000, defaultKeysize);  
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");  
			PBEKey key = (PBEKey) factory.generateSecret(pass);  
			skey = new SecretKeySpec(key.getEncoded(), keygenAlgorithm);
			
			return convertByteToHex(skey.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} 
		return "error";
	}

	public String getKey() {
		return convertByteToHex(skey.getEncoded());
	}

	@Override
	public String toString() {
		return name;
	}

	public int getDefaultKeysize() {
		return defaultKeysize;
	}
}
