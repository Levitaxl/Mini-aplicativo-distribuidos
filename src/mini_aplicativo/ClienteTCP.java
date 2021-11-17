/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mini_aplicativo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author herme
 */
public class ClienteTCP {
    public static void main(String[] args) throws IOException {
        final String HOST="127.0.0.1";
        final int PUERTO=19876;
        
        DataInputStream in;
        DataOutputStream out;
        
        Socket sc= new Socket(HOST, PUERTO);
        in  = new DataInputStream(sc.getInputStream());
        out = new DataOutputStream(sc.getOutputStream());
         
        
        Scanner entrada = new Scanner(System.in);
        String nombre= entrada.nextLine();
        
        out.writeUTF("helloiam "+nombre);
        
        String mensaje= in.readUTF();
        
        if(mensaje.equals("Usuario inexistente")) {
            System.out.println("Socket Cerrado");
            sc.close();}
        
        else if(mensaje.equals("ok")){
            out.writeUTF(nombre);
        }
    }
}
