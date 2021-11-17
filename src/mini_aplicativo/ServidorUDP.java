/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mini_aplicativo;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author herme
 */
public class ServidorUDP {

     public static void main(String[] args) {

        final int PUERTO = 5000;
        byte[] buffer = new byte[1024];

        try {
            System.out.println("Iniciado el servidor UDP");
            DatagramSocket socketUDP = new DatagramSocket(PUERTO);
            
            while (true) {
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
                socketUDP.receive(peticion);
                System.out.println("Recibo la informacion del cliente");
                
                
                String mensaje = new String(peticion.getData());
                System.out.println("El mensaje es "+ mensaje);
                
                
                String respuesta2= leetTxt(mensaje.trim());
                int puertoCliente = peticion.getPort();
                InetAddress direccion = peticion.getAddress();
                buffer = new byte[1024];
                buffer = respuesta2.getBytes();
                DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length, direccion, puertoCliente);

                System.out.println("Envio la informacion al cliente");
                
                socketUDP.send(respuesta);
                
  
                if(respuesta2.equals("Usuario inexistente")){
                  System.out.println("El usuario "+respuesta2+" Se encuentra inexistente");
                }
                
                else{    
                    System.out.println("El usuario "+respuesta2+" Si se encuentra");
                    buffer = new byte[1024];
                    DatagramPacket peticion2 = new DatagramPacket(buffer, buffer.length);
                    socketUDP.receive(peticion2);
                     
                    mensaje = new String(peticion2.getData());
                      
                    String ip = InetAddress.getLocalHost().getHostAddress();   
                    saveLog(mensaje,ip,"UDP");
                    //socketUDP.close(); 
                
                    System.out.println("Segundo mensaje"+ mensaje);
                }
                
                //socketUDP.close();
                
            }

        } catch (SocketException ex) {
            Logger.getLogger(ServidorUDP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServidorUDP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

     private static String leetTxt(String nombre) throws IOException{
        Log myLog = new Log("./log.txt");
        System.out.println(nombre);
        try {
            BufferedReader bf = new BufferedReader(new FileReader("C:\\Users\\herme\\Documents\\NetBeansProjects\\Cliente\\usuarios.txt"));
            String bfRead;
            while(((bfRead= bf.readLine()) !=null)){
               System.out.println("helloiam "+bfRead);
                if(nombre.equalsIgnoreCase("helloiam "+bfRead)) 
                    return "ok";
            }
        } catch (Exception e) { System.out.println("No se encontro el archivo");
        }
         myLog.addLine("Usuario inexistente");
        return "Usuario inexistente";
     
    }
    

     
     private static void saveLog(String nombre, String ip, String protocolo) throws IOException{
        Log myLog = new Log("./log.txt");
        myLog.addLine(nombre.trim()+" "+ip.trim()+" "+protocolo.trim());
    }
}
