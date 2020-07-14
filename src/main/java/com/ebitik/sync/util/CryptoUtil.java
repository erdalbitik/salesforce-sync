package com.ebitik.sync.util;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.ebitik.sync.exception.SyncException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CryptoUtil {

	/**
	 * Algorithm
	 */
	public static final String ALGORITHM = "AES/GCM/NoPadding";

	/**
	 * Secret key length in bits
	 */
	private static final int SECRET_KEY_LEN = 128;

	/**
	 * Default pseudo random function
	 */
	private static final String PRF = "PBKDF2WithHmacSHA256";

	/**
	 * PRF iteration count
	 */
	private static final int PBKDF_ITER_COUNT = 16;

	private static final int GCM_IV_LENGTH = 12;
	private static final int GCM_TAG_LENGTH = 16;

	public static String encrypt(String plainText, String masterPassword, byte[] salt) {
		try {
			SecretKey skey = generateSecretKey(masterPassword, salt);

			byte[] iv = new byte[GCM_IV_LENGTH];
			(new SecureRandom()).nextBytes(iv);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			GCMParameterSpec ivSpec = new GCMParameterSpec(GCM_TAG_LENGTH * Byte.SIZE, iv);
			cipher.init(Cipher.ENCRYPT_MODE, skey, ivSpec);
			byte[] ciphertext = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
			byte[] encrypted = new byte[iv.length + ciphertext.length];
			System.arraycopy(iv, 0, encrypted, 0, iv.length);
			System.arraycopy(ciphertext, 0, encrypted, iv.length, ciphertext.length);

			return Base64.getEncoder().encodeToString(encrypted);
		} catch (GeneralSecurityException e) {
			log.error("Error while encrypting text", e);
			throw new SyncException("Error while encrypting text", e);
		}
	}

	public static String decrypt(String encoded, String masterPassword, byte[] salt) {
		try {
			SecretKey skey = generateSecretKey(masterPassword, salt);
			byte[] decoded = Base64.getDecoder().decode(encoded);

			byte[] iv = Arrays.copyOfRange(decoded, 0, GCM_IV_LENGTH);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			GCMParameterSpec ivSpec = new GCMParameterSpec(GCM_TAG_LENGTH * Byte.SIZE, iv);
			cipher.init(Cipher.DECRYPT_MODE, skey, ivSpec);
			byte[] ciphertext = cipher.doFinal(decoded, GCM_IV_LENGTH, decoded.length - GCM_IV_LENGTH);

			return new String(ciphertext, StandardCharsets.UTF_8);
		} catch (GeneralSecurityException e) {
			log.error("Error while decrypting text", e);
			throw new SyncException("Error while decrypting text", e);
		}
	}

	private static SecretKey generateSecretKey(String masterPassword, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		PBEKeySpec spec = new PBEKeySpec(masterPassword.toCharArray(), salt, PBKDF_ITER_COUNT, SECRET_KEY_LEN);
		SecretKeyFactory factory = SecretKeyFactory.getInstance(PRF);
		SecretKey secret = factory.generateSecret(spec);
		return new SecretKeySpec(secret.getEncoded(), "AES");
	}

}
