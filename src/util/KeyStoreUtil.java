package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Scanner;

import javax.crypto.spec.SecretKeySpec;

public class KeyStoreUtil {
    private KeyStore ks;
    private char[] storePW = "pizza".toCharArray();
    private String storeFileName = "appKeyStore.bks";

    public KeyStoreUtil() {
    }

    public KeyStore openKeyStore() {
        try {
            this.ks = KeyStore.getInstance("BKS", "BC");
            FileInputStream fis = new FileInputStream(this.storeFileName);
            this.ks.load(fis, this.storePW);
            fis.close();
        } catch (Exception e) {
            try {
                this.ks = KeyStore.getInstance("BKS", "BC");
                this.ks.load(null, this.storePW);

                try (FileOutputStream fos = new FileOutputStream(this.storeFileName)) {
                    this.ks.store(fos, this.storePW);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return ks;
    }

    public void generateAndAddKey(KeyStore store, char[] secretKeyPW) {
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("DEFAULT", "BC");
            byte[] keyBytes = new byte[32];
            secureRandom.nextBytes(keyBytes);

            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

            KeyStore.SecretKeyEntry entry = new KeyStore.SecretKeyEntry(key);
            KeyStore.ProtectionParameter protection = new KeyStore.PasswordProtection(secretKeyPW);
            store.setEntry("key", entry, protection);

            try (FileOutputStream fos = new FileOutputStream(this.storeFileName)) {
                this.ks.store(fos, this.storePW);
            }
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
