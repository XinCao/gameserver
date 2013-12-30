package util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author caoxin
 */
public class FileRead {

    private static final Logger logger = LoggerFactory.getLogger(FileRead.class);

    public static String readFileAsString(String fileName) {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(fileName));
            String tempStr = bufferedReader.readLine();
            while (tempStr.length() > 0) {
                sb.append(tempStr);
                tempStr = bufferedReader.readLine();
            }
        } catch (FileNotFoundException fileNotFoundException) {
            logger.error(fileNotFoundException.getMessage());
        } catch(IOException iOException) {
            logger.error(iOException.getMessage());
        }
        return sb.toString();
    }

    public static byte[] readFileAsByteArray(String fileName) {
        byte[] byteBuffer = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
        try {
            FileInputStream fis = new FileInputStream(fileName);
            while(fis.read(byteBuffer) != -1) {
                baos.write(byteBuffer);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            logger.error(fileNotFoundException.getMessage());
        } catch( IOException iOException) {
            logger.error(iOException.getMessage());
        }
        return baos.toByteArray();
    }

    public static String fileNameCurrentPlatform(String fileName) {
        if (fileName.contains("\\") || fileName.contains("/")) {
            fileName = fileName.replace('\\', '.');
            fileName = fileName.replace('/', '.');
        }
        return fileName.replace('.', CurrentPlatform.fileSeparator);
    }

    public static void main(String... args) {
    }
}