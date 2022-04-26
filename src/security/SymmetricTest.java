package security;

import util.FileUtil;

import java.io.File;
import java.io.FilenameFilter;

public class SymmetricTest {
    private Symmetric securityControl;
    private String fileName = "testFile.txt";
    private char[] testKey = "testKey".toCharArray();
    private File testFile;
    public Integer countPassed;
    public SymmetricTest(Symmetric securityControl) {
        System.out.println("START TESTING");
        this.securityControl = securityControl;
        try {
            testFile = new File(fileName);
            testFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.countPassed = 0;
    }

    // test case for encrypting empty file. Empty files should pass the encryption process.
    public void encryptEmptyFile() {
        // proceed with file encryption
        try {
            this.securityControl.encrypt(fileName, testKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //delete initial file
        testFile.delete();
        countPassed++;
    }

    // test case for decrypting empty file. Empty files should pass the decryption process.
    public void decryptEmptyFile() {
        // read encrypted test file with IV in filename
        File dir = new File("./");
        File[] foundFiles = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(fileName);
            }
        });

        for (File file : foundFiles) {
            // proceed with file decrypting
            try {
                this.securityControl.decrypt(file.getName(), testKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        countPassed++;

        // delete decrypted file for testing environment
        File[] foundDecryptFiles = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(fileName.split("\\.")[0]);
            }
        });
        for (File file : foundDecryptFiles) {
            file.delete();
        }
    }
}
