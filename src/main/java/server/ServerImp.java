package server;

import remote.ClientRemote;
import remote.ServerRemote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerImp extends UnicastRemoteObject implements ServerRemote {

    private String hostAddress = "localhost";
    private ArrayList<User> userList;
    private byte[] data;


    public ServerImp() throws RemoteException{
        super();
        this.userList = new ArrayList<User>();
    }

    @Override
    public synchronized void addUser(String service, String username) throws RemoteException {
        try{
            // instantiate a client remote object
            ClientRemote newUserRemote = (ClientRemote) Naming.lookup("rmi://" + this.hostAddress + "/" + service);
            User newUser = new User(username, newUserRemote);
            userList.add(newUser);
            System.out.println(username+", welcome! you are added to the list.");
            if(userList.size() == 1){
                newUser.setManager(true);
                System.out.println(username+", you are the manager now.");
            }
            newUserRemote.download(this.data);
            newUser.setClientRemote(newUserRemote);

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void joinRequest(String username) throws RemoteException {

    }


    @Override
    public void broadcastImg(byte[] data) throws RemoteException {
        this.data = data;
        for(User user : this.userList){
            ClientRemote thisClientRemote = user.getClientRemote();
            thisClientRemote.download(data);
            user.setClientRemote(thisClientRemote);
        }

    }

    @Override
    public String[] displayUsers() throws RemoteException {
        System.out.println("INTO METHOD displayUsers");
        String[] userList = new String[this.userList.size()];
        for(int i = 0;i<this.getUserList().size();i++){
            userList[i] = this.getUserList().get(i).getUsername();
        }
        return userList;
    }

    @Override
    public void exitBoard(String username) throws RemoteException {

    }

    @Override
    public void closeBoard() throws RemoteException {

    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public byte[] getData() {
        return data;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }
}
