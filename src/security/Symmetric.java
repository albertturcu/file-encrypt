package security;

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
    private KeyStoreUtil ksUtil = new KeyStoreUtil();
    private KeyStore store;

    public final static byte ENCRYPT_MODE = 0;
    public final static byte DECRYPT_MODE = 1;

    public Symmetric() {
        store = ksUtil.openKeyStore();
    }

    public void encrypt(String plaintextFileName, char[] secretKeyPW) {
        ksUtil.generateAndAddKey(this.store, secretKeyPW);
        var key = KeyStoreUtil.retrieveFromKeyStore(this.store, secretKeyPW);

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
        var key = KeyStoreUtil.retrieveFromKeyStore(this.store, secretKeyPW);

        try {
            // Reading
            String ivString = FileUtil.getIV("AES/CBC/PKCS5Padding", plaintextFileName);
            System.out.println("decrypt ivString: " + ivString);
            IvParameterSpec iv = new IvParameterSpec(Hex.decode(ivString));

            byte[] input = FileUtil.readAllBytes("AES/CBC/PKCS5Padding", plaintextFileName);

            // Decrypting
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] output = cipher.doFinal(input);

            String outputFileName = FileUtil.removeIvFromPathFilename(plaintextFileName);
            // Writing
            FileUtil.write(outputFileName, output);

            // Delete old file
            File oldFile = new File(plaintextFileName);
            oldFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
