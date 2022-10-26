package client;

import remote.ServerRemote;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    private int id;
    private String username;


    public static void main(String[] args) {

        // look for the server's remote object

        ClientImp newWhiteBoard;
        try{
            String username = args[0];
            newWhiteBoard = new ClientImp(username);
            newWhiteBoard.register(username);


        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }


}
