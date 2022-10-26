package client;

import remote.ClientRemote;
import remote.ServerRemote;
import server.User;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ClientImp extends UnicastRemoteObject implements ClientRemote, Serializable {

    private static final long serialVersionUID = 1L;
    private String username;
    private MainFrame clientGUI = new MainFrame("Canvas");
    private ServerRemote serverRemote;
    private String service="server";
    private String clientService="canvas";
    private byte[] data;


    protected ClientImp(String username) throws RemoteException {
        super();
        this.username = username;
    }

    @Override
    public void userPool(String[] userList) throws RemoteException {
        //clientGUI.getJlist().setListData(userList)
    }

    @Override
    public void download(byte[] data) throws RemoteException {
        try{
            ByteArrayInputStream input = new ByteArrayInputStream(data);
            BufferedImage canvas = ImageIO.read(input);
            this.clientGUI.getCanvas().setImg(canvas);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean qualify(String username) throws RemoteException {
        ArrayList<String> userList = this.serverRemote.displayUsers();

        for(int i = 0; i < userList.size(); i++){
            if (username==userList.get(i)){
                return false;
            }
        }
        return true;
    }


    public void register(String username){
        try {
            System.out.println("REGISTER CLIENT");
            Naming.rebind("rmi://localhost:1099/"+ clientService, this);
            this.serverRemote = (ServerRemote) Naming.lookup("rmi://localhost:1099/" + service);
            //System.out.println(this.serverRemote.displayUsers());
            this.clientGUI.startGUI();
            boolean success = this.serverRemote.addUser(clientService,username);
            if(success){
                this.clientGUI.setServerRemote(this.serverRemote);
                System.out.println(this.clientGUI.getServerRemote());
                this.clientGUI.getCanvas().setBoard(this.serverRemote);
                System.out.println(this.clientGUI.getCanvas().getBoard());
                System.out.println("REGISTER CLIENT SUCCESS");
            }else{
                System.out.println("Username already exists, please restart!");
                System.exit(0);
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e){
            throw new RuntimeException(e);
        } catch (NotBoundException e){
            throw new RuntimeException(e);
        }
    }





}
