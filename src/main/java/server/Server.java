package server;

import remote.ServerRemote;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Server {
    public static void main(String[] args)  {

        try{
            System.out.println("create whiteboard remote service");
            // instantiate a ServerRemote
            ServerRemote serverRemote = new ServerImp();
            //int port = Integer.parseInt(args[0]);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("server", serverRemote);
            System.out.println("Server started");

        } catch (AccessException e) {
            throw new RuntimeException(e);
        } catch (AlreadyBoundException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }
}
