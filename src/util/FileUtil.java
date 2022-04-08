package util;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {
    // basic read of file
    public static byte[] readAllBytes(String plaintextFileName) {
        byte[] bytesRead = {};

        try {
            bytesRead = Files.readAllBytes(Paths.get(plaintextFileName));
        } catch (Exception e) {
        }

        return bytesRead;
    }

    // read file that is aes encrypted
    public static byte[] readAllBytes(String transformation, String plaintextFileName) {
        byte[] bytesRead = {};
        String outFile = "";
        String[] parts = transformation.split("/");

        if (parts.length == 3 && parts[0].equals("AES")) {
            outFile = plaintextFileName;
        }

        try {
            bytesRead = Files.readAllBytes(Paths.get(outFile));
        } catch (Exception e) {
        }

        return bytesRead;
    }

    public static void write(String filename, byte[] output) {
        try {
            Files.write(Paths.get(filename), output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void write(String transformation, String plaintextFileName, byte[] output) {
        String outFile = "";
        String[] parts = transformation.split("/");

        if (parts.length == 3 && parts[0].equals("AES")) {
            outFile = plaintextFileName + ".aes";
        }

        try {
            Files.write(Paths.get(outFile), output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void write(String transformation, String plaintextFileName, byte[] output, String iv) {
        String outFile = "";
        String[] parts = transformation.split("/");

        if (parts.length == 3 && parts[0].equals("AES")) {
            outFile = plaintextFileName + "." + iv + ".aes";
        }

        try {
            Files.write(Paths.get(outFile), output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getIV(String transformation, String plaintextFileName) {
        String iv = "";
        String[] parts = transformation.split("/");

        if (parts.length == 3 && parts[0].equals("AES") && parts[1].equals("CBC")) {
            String[] partsFileName = plaintextFileName.split("/");
            String filename = partsFileName[partsFileName.length - 1];
            String[] p = filename.split("\\.");
            iv = p[p.length - 2];
        }

        return iv;
    }

    public static String removeIvFromPathFilename(String plaintextFileName) {
        String[] partsFileName = plaintextFileName.split("/");
        String filename = partsFileName[partsFileName.length - 1];
        String[] p = filename.split("\\.");

        if (p.length <= 3) {
            return p[0] + "-decrypted";
        } else if (p.length > 3) {
            return p[0] + "-decrypted" + "." + p[1];
        }

        return "";
    }
}