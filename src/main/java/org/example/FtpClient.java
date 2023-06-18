package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

class FtpCient {
    public static void main(String[] args) throws IOException{
        final Scanner scanner = new Scanner(System.in);
        String localStorage = "./LocalStorage/";
        String dir;
        RmiInterface rmi = null;
        try {
            Object o = Naming.lookup("rmi://127.0.0.1:9000/FTP");
            rmi = (RmiInterface) o;
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            System.out.println("Błąd połączenia!");
            System.out.println("Zamykanie klienta!");
            System.exit(1);
        }
        System.out.println("Witaj w kliencie FTP!");
        System.out.println("Pliki na sewerze: ");
        //poka pliki
        while(true){
            System.out.println("1. Pobieranie z serwera");
            System.out.println("2. Wysyłanie na serwer");
            System.out.println("0. Wyjście");
            switch(scanner.nextInt()){
                case 1:
                    System.out.println("Podaj nazwę pliku do pobrania: ");
                    dir = scanner.nextLine();
                    byte[] fileDownloadData = rmi.downloadFile(dir);
                    File clientDownloadPathFile = new File(localStorage+dir);
				    FileOutputStream out = new FileOutputStream(clientDownloadPathFile);				
	    		    out.write(fileDownloadData);
				    out.flush();
		    	    out.close();
                break;
                case 2:
                    System.out.println("Podaj ścierzkę pliku do wysłania: ");
                    dir = scanner.nextLine(); 
                    File clientUploadPathFile = new File(dir);
				    byte [] fileUploadData = new byte[(int) clientUploadPathFile.length()];
				    FileInputStream in = new FileInputStream(clientUploadPathFile);	
				    in.read(fileUploadData, 0, fileUploadData.length);					 
				    boolean upload = rmi.uploadFile(fileUploadData, clientUploadPathFile.getName());
				    in.close();
                    if(upload){
                        System.out.println("Przesłano plik");	
                    }else{
                        System.out.println("Błąd przesyłania plik");	
                    }
                   
                break;
                case 0:
                    System.out.println("Zamykanie połączenia");
                break;
                default:
                    continue;
            }
        }
    }
}