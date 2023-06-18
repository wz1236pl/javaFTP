import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class FtpSerwer {
    private static File dir = new File("./FtpStorage");
    
    public static void main(String[] args){
        if(Files.notExists(dir.toPath())){
            dir.mkdir();
        }
        try {
            RmiImplementation rmi = new RmiImplementation();
            LocateRegistry.createRegistry(40000);
            Naming.rebind("SerwerFtp", rmi);
            System.out.println("Serwer wystartował!");
        } catch(RemoteException |MalformedURLException e) {
            throw new RuntimeException("Błąd po stronie serwera! "+e.getMessage());
        } 
    }
}
