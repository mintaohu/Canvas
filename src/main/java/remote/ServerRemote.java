package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerRemote extends Remote {

    public boolean addUser(String service, String username) throws RemoteException;
    public void joinRequest(String username) throws RemoteException;
    public void broadcastImg(byte[] data) throws RemoteException;
    public ArrayList<String> displayUsers() throws RemoteException;
    public void exitBoard(String username) throws RemoteException;
    public void closeBoard() throws RemoteException;

}
