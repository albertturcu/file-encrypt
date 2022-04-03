package security;

import javax.crypto.spec.SecretKeySpec;

import java.io.File;

import org.bouncycastle.util.encoders.Hex;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.Cipher;

import java.security.SecureRandom;

// util
import util.FileUtil;

public class Symmetric {
    String pdfFile = "RA059U_Albert";
    String dir = new File("").getAbsolutePath();
    String plaintextFileName = dir + "/" + pdfFile + "." + "pdf",
            testFile = dir + "/" + pdfFile + "_" + "test" + "." + "pdf";
    // pass for key value in store ( needs to be provided by user )
    public char[] storePW;

    public Symmetric() {
        storePW = "pizza".toCharArray();
    }

    public void encrypt(SecretKeySpec key) {
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

    public void decrypt(SecretKeySpec key) {
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
