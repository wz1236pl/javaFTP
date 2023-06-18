package org.example;

import java.io.File;
import java.nio.file.Files;

public class FtpSerwer {
    private static File dir = new File("./FtpStorage");
    
    public static void main(String[] args){
        if(Files.notExists(dir.toPath())){
            dir.mkdir();
        }
        try {
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");
            
        } catch(Exception e) {
            throw new RuntimeException("Błąd po stronie serwera!");
        }
    }
}
