/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mini_aplicativo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author herme
 */
public class ServidorTCP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {       
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
                
                if(respuesta.equals("Usuario inexistente"))socket.close();
                        
                else{
                    nombre = in.readUTF();
                    String ip = InetAddress.getLocalHost().getHostAddress();   
                    saveLog(nombre,ip,"TCP");
                    socket.close(); 
               }
           }
        
        
    }
    
    
    private static void saveLog(String nombre, String ip, String protocolo) throws IOException{
        Log myLog = new Log("./log.txt");
        myLog.addLine(nombre+" "+ip+" "+protocolo);
    }
    
    private static String leetTxt(String nombre) throws IOException{
        Log myLog = new Log("./log.txt");
        try {
            BufferedReader bf = new BufferedReader(new FileReader("C:\\Users\\herme\\Documents\\NetBeansProjects\\Cliente\\usuarios.txt"));
            String bfRead;
            while(((bfRead= bf.readLine()) !=null)){
                if(nombre.equals("helloiam "+bfRead)) 
                    return "ok";
            }
        } catch (Exception e) { System.out.println("No se encontro el archivo");
        }
         myLog.addLine("Usuario inexistente");
        return "Usuario inexistente";
     
    }
    
}
