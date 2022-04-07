package security;

import javax.crypto.spec.SecretKeySpec;

import java.io.File;

import org.bouncycastle.util.encoders.Hex;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.Cipher;

import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Base64;

// util
import util.FileUtil;
import util.KeyStoreUtil;

public class Symmetric {
    String pdfFile = "RA059U_Albert";
    String dir = new File("").getAbsolutePath();
    String plaintextFileName = dir + "/" + pdfFile + "." + "pdf",
            testFile = dir + "/" + pdfFile + "_" + "test" + "." + "pdf";

    private char[] storePW;
    private KeyStore store;

    public final static byte ENCRYPT_MODE = 0;
    public final static byte DECRYPT_MODE = 1;

    public Symmetric() {
        storePW = "pizza".toCharArray();
        store = KeyStoreUtil.createKeyStore(storePW);
    }

    private SecretKeySpec getEncryptionKey(char[] secretKeyPW) {
        KeyStoreUtil.generateAndAddKey(store, secretKeyPW);

        return KeyStoreUtil.retrieveFromKeyStore(store, secretKeyPW);
    }

    public void encrypt(String plaintextFileName, char[] secretKeyPW) {
        System.out.println("encrypt plaintextFileName: " + plaintextFileName);
        System.out.println("encrypt secretKeyPW: " + new String(secretKeyPW));
        // SecretKeySpec key = this.getEncryptionKey(secretKeyPW);
        KeyStoreUtil.generateAndAddKey(store, secretKeyPW);
        var key = KeyStoreUtil.retrieveFromKeyStore(store, secretKeyPW);

        System.out.println("encrypt storedKey: " + Base64.getEncoder().encodeToString(key.getEncoded()));
        byte[] iv = new byte[16];
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("DEFAULT", "BC");
            secureRandom.nextBytes(iv);

            // reading
            byte[] input = FileUtil.readAllBytes(plaintextFileName);
            // encrypting
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
            byte[] output = cipher.doFinal(input);
            // writing
            FileUtil.write("AES/CBC/PKCS5Padding", plaintextFileName, output, Hex.toHexString(iv));

            plaintextFileName = plaintextFileName + "." + Hex.toHexString(iv) + ".aes";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decrypt(String plaintextFileName, char[] secretKeyPW) {
        System.out.println("decrypt plaintextFileName: " + plaintextFileName);
        System.out.println("decrypt secretKeyPW: " + new String(secretKeyPW));
        // SecretKeySpec key = this.getEncryptionKey(secretKeyPW);
        var key = KeyStoreUtil.retrieveFromKeyStore(store, secretKeyPW);

        System.out.println("decrypt storedKey: " + Base64.getEncoder().encodeToString(key.getEncoded()));
        try {
            // Reading
            String ivString = FileUtil.getIV("AES/CBC/PKCS5Padding", plaintextFileName);
            IvParameterSpec iv = new IvParameterSpec(Hex.decode(ivString));

            byte[] input = FileUtil.readAllBytes("AES/CBC/PKCS5Padding", plaintextFileName);

            // Decrypting
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] output = cipher.doFinal(input);

            // Writing
            FileUtil.write(testFile, output);

            // Delete old file
            File oldFile = new File(plaintextFileName);
            oldFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
