/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author herme
 */
public class Servidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        // TODO code application logic here
        ServerSocket servidor=null;
        Socket socket=null;
        DataInputStream in;
        DataOutputStream out;
        final int PUERTO=19876;
            
        servidor= new ServerSocket(PUERTO);
        System.out.println("Servidor iniciado");
           while(true){
               socket = servidor.accept();
               in = new DataInputStream(socket.getInputStream());
               out = new DataOutputStream(socket.getOutputStream());
               
               String nombre = in.readUTF();
               String respuesta= leetTxt(nombre);
               
               out.writeUTF(respuesta);
               socket.close();
               
               System.out.println("Cliente desconectado");
           }
        
        
    }
    
    
    private static String leetTxt(String nombre){
        try {
            BufferedReader bf = new BufferedReader(new FileReader("C:\\Users\\herme\\Documents\\NetBeansProjects\\Cliente\\usuarios.txt"));
            String bfRead;
            while(((bfRead= bf.readLine()) !=null)){
                if(bfRead.equals(nombre)) 
                    return "ok";
            }
        } catch (Exception e) { System.out.println("No se encontro el archivo");
        }
        return "error";
     
    }
    
}
