package org.example;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiInterface extends Remote{
    public String listFiles();
    public boolean uploadFile(byte[] bytes, String remoteLocation) throws RemoteException;
    public byte[] downloadFile(String remoteLocation) throws RemoteException;
}