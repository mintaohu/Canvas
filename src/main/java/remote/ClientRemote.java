package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientRemote extends Remote {

    public void userPool(String[] userList) throws RemoteException;
    public void download(byte[] data) throws RemoteException;
    public boolean qualify(String username) throws RemoteException;
}
