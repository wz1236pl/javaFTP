package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiImplementation extends UnicastRemoteObject implements RmiInterface {
    private File dir = new File("./FtpStorage");
    protected RmiImplementation() throws RemoteException {
        super();
    }

    @Override
    public String listFiles(){
        String[] fileList = dir.list();
        String out = "";
        for(int i=0; i<fileList.length; i++){
            out += fileList[i] + "\t";
        }
        return out;
    }
    
    @Override
    public boolean uploadFile(byte[] bytes, String remoteLocation){
       try {
    		File serverPathFile = new File(dir.getPath()+remoteLocation);
    		FileOutputStream out=new FileOutputStream(serverPathFile);
    		byte[] data = bytes;
    		out.write(data);
			out.flush();
	    	out.close();
	        System.out.println("Przesłąno plik");
            return true;
		} catch (IOException e) {
			e.printStackTrace();
            System.out.println("Przesłąno plik");
            return false;
		}
    }

    @Override
    public byte[] downloadFile(String remoteLocation){
        byte[] fileData;
        File serverPathFile = new File(dir.getPath()+remoteLocation);			
		fileData=new byte[(int) serverPathFile.length()];
		FileInputStream in;
		try {
			in = new FileInputStream(serverPathFile);
			try {
				in.read(fileData, 0, fileData.length);
			} catch (IOException e) {
				e.printStackTrace();
			}						
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
		return fileData;
    }

}
