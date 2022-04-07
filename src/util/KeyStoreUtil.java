package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Scanner;

import javax.crypto.spec.SecretKeySpec;

public class KeyStoreUtil {
    public static KeyStore createKeyStore(char[] storePW) {
        System.out.println("OPEN STORE... ");

        String storeFileName = "appKeyStore.bks";

        KeyStore ks = null;
        try {
            ks = KeyStore.getInstance("BKS", "BC");
            FileInputStream fis = new FileInputStream(storeFileName);
            ks.load(fis, storePW);
            fis.close();
        } catch (Exception e) {
            System.out.println("STORE NOT EXISTING... ");
            try {
                System.out.println("GENERATE STORE... ");
                ks = KeyStore.getInstance("BKS", "BC");
                ks.load(null, storePW);

                try (FileOutputStream fos = new FileOutputStream(storeFileName)) {
                    ks.store(fos, storePW);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return ks;
    }

    public static void generateAndAddKey(KeyStore store, char[] secretKeyPW) {
        System.out.println("Generate and add key...");
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("DEFAULT", "BC");
            byte[] keyBytes = new byte[32];
            secureRandom.nextBytes(keyBytes);

            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

            KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(key);
            KeyStore.ProtectionParameter protection = new KeyStore.PasswordProtection(secretKeyPW);
            store.setEntry("key", entry, protection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SecretKeySpec retrieveFromKeyStore(KeyStore store, char[] pw) {
        SecretKeySpec key = null;

        try {
            key = (SecretKeySpec) store.getKey("key", pw);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return key;
    }
}
