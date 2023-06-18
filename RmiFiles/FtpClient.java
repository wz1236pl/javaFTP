import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

class FtpClient {
    public static void main(String[] args) throws IOException{
        String localStorage = "./LocalStorage/";
        String dir;
        RmiInterface rmi = null;
        try {
            Object o = Naming.lookup("SerwerFtp");
            rmi = (RmiInterface) o;
        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            System.out.println("Błąd połączenia!");
            System.out.println("Zamykanie klienta!");
            System.exit(1);
        }
        System.out.println("Witaj w kliencie FTP!");
        System.out.println("Pliki na sewerze: ");
        System.out.println(rmi.listFiles());
        while(true){
            Scanner scanner = new Scanner(System.in);
            Scanner scanner2 = new Scanner(System.in);
            System.out.println("1. Pobieranie z serwera");
            System.out.println("2. Wysyłanie na serwer");
            System.out.println("0. Wyjście");
            switch(scanner.nextInt()){
                case 1:
                    File localDir = new File("./LocalStorage");
                    if(Files.notExists(localDir.toPath())){
                        localDir.mkdir();
                    }
                    System.out.println("Podaj nazwę pliku do pobrania: ");
                    dir = scanner2.nextLine();
                    byte[] fileDownloadData = rmi.downloadFile(dir);
                    File clientDownloadPathFile = new File(localStorage+dir);
				    FileOutputStream out = new FileOutputStream(clientDownloadPathFile);				
	    		    out.write(fileDownloadData);
				    out.flush();
		    	    out.close();
                break;
                case 2:
                    System.out.println("Podaj ścierzkę pliku do wysłania: ");
                    dir = scanner2.nextLine(); 
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
                    System.exit(1);
                break;
                default:
                    continue;
            }
            // scanner.close();
            // scanner2.close();
        }
    }
}