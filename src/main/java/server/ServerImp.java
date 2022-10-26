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

    private String hostAddress = "localhost:1099";
    private ArrayList<User> userList;
    private ArrayList<String> nameList;
    private byte[] data;


    public ServerImp() throws RemoteException{
        super();
        this.userList = new ArrayList<User>();
        this.nameList = new ArrayList<String>();

    }

    @Override
    public synchronized boolean addUser(String service, String username) throws RemoteException {
        try{
            ArrayList<String> nameList = this.getNameList();
            System.out.println("The username list:");
            if (nameList != null){
                for (String value : nameList) {
                    System.out.print(value + " ");
                    //System.out.println("The username already exists!");
                }
                for (String s : nameList) {
                    System.out.println("CHECK FOR DUPLICATES");
                    if (username.equals(s)) {
                        System.out.println("The username already exists!");
                        //System.exit(0);
                        return false;
                    }
                }
            }

            // instantiate a client object
            ClientRemote newUserRemote = (ClientRemote) Naming.lookup("rmi://" + this.hostAddress + "/" + service);
            User newUser = new User(username, newUserRemote);
            userList.add(newUser);
            updateNameList();
            System.out.println(username+", welcome! you are added to the list.");
            if(userList.size() == 1){
                newUser.setManager(true);
                System.out.println(username+", you are the manager now.");
            }
            if(this.data!=null){
                newUserRemote.download(this.data);
                newUser.setClientRemote(newUserRemote);
            }

            return true;


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
        System.out.println("BROADCASTING START");
        this.data = data;
        for(User user : this.userList){
            ClientRemote thisClientRemote = user.getClientRemote();
            System.out.println("");
            thisClientRemote.download(data);
            user.setClientRemote(thisClientRemote);
        }

    }

    @Override
    public ArrayList<String> displayUsers() throws RemoteException {
        System.out.println("INTO METHOD displayUsers");
        //String[] userList = new String[this.userList.size()];
        //for(int i = 0;i<this.getUserList().size();i++){
        //    userList[i] = this.getUserList().get(i).getUsername();
        //}
        return this.nameList;
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

    public ArrayList<String> getNameList() {
        return nameList;
    }

    public void updateNameList() {
        ArrayList<String> nameList = new ArrayList<String>();
        for(int i = 0; i < this.getUserList().size();i++){
            nameList.add(this.getUserList().get(i).getUsername());
        }
        setNameList(nameList);
    }

    public void setNameList(ArrayList<String> nameList) {
        this.nameList = nameList;
    }
}
