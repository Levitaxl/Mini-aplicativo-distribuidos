/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author herme
 */
public class Cliente {
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        final String HOST="127.0.0.1";
        final int PUERTO=19876;
        
        DataInputStream in;
        DataOutputStream out;
        
        Socket sc= new Socket(HOST, PUERTO);
        in  = new DataInputStream(sc.getInputStream());
        out = new DataOutputStream(sc.getOutputStream());
          
        //Este es el cliente envia
        Scanner entrada = new Scanner(System.in);
        String nombre= entrada.nextLine();
        out.writeUTF(nombre);
        String mensaje= in.readUTF();
        
        System.out.println(mensaje);
            
        sc.close();
    }
}
