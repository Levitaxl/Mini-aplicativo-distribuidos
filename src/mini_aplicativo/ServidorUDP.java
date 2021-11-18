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
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author herme
 */
public class ServidorUDP {

     public static void main(String[] args) throws UnknownHostException {

        final int PUERTO = 5000;
        String ip = InetAddress.getLocalHost().getHostAddress();  
        int puertoCliente;
        InetAddress direccion;
        String respuesta_txt;
        DatagramSocket socketUDP;

        try {
            System.out.println("Iniciado el servidor UDP");
            socketUDP = new DatagramSocket(PUERTO);
            
            while (true) {
                DatagramPacket peticion = createDatagramPacket();
                
                String mensaje = ReceiveData(socketUDP, peticion);
        
                puertoCliente = peticion.getPort();
                direccion = peticion.getAddress();
                
                respuesta_txt= leetTxt(mensaje.trim());
                SendData(respuesta_txt, socketUDP, direccion, puertoCliente);
                
                if(respuesta_txt.equals("Usuario inexistente")) System.out.println("El usuario "+respuesta_txt+" Se encuentra inexistente");
                
                else if(respuesta_txt.equals("ok")){    
                    DatagramPacket peticion2 = createDatagramPacket();
                    mensaje = ReceiveData(socketUDP, peticion2);
                    
                    saveLog(mensaje,ip,"UDP");                
                    System.out.println("Segundo mensaje"+ mensaje);
                }
            }

        } catch (SocketException ex) {
            Logger.getLogger(ServidorUDP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServidorUDP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

     private static String leetTxt(String nombre) throws IOException{
        Log myLog = new Log("./log.txt");
        try {
            BufferedReader bf = new BufferedReader(new FileReader("C:\\Users\\herme\\Documents\\NetBeansProjects\\Cliente\\usuarios.txt"));
            String bfRead;
            while(((bfRead= bf.readLine()) !=null)){
                if(nombre.equalsIgnoreCase("helloiam "+bfRead)) 
                    return "ok";
            }
        } 
        catch (Exception e) { System.out.println("No se encontro el archivo");}
        myLog.addLine("Usuario inexistente");
        return "Usuario inexistente";
    }
     
    private static void saveLog(String nombre, String ip, String protocolo) throws IOException{
        Log myLog = new Log("./log.txt");
        myLog.addLine(nombre.trim()+" "+ip.trim()+" "+protocolo.trim());
    }
     
    private static String ReceiveData(DatagramSocket socketUDP,DatagramPacket peticion) throws IOException{
        socketUDP.receive(peticion);
        return new String(peticion.getData(), peticion.getOffset(), peticion.getLength());
    }
    
     private static void SendData(String data, DatagramSocket socketUDP, InetAddress direccionServidor,int PUERTO_SERVIDOR) throws IOException {
        byte[]  buffer = data.getBytes();
        DatagramPacket paquete_nombre = new DatagramPacket(buffer, buffer.length, direccionServidor, PUERTO_SERVIDOR);
        socketUDP.send(paquete_nombre);
    }
     
    private static  DatagramPacket createDatagramPacket(){
        byte[]  buffer = new byte[1024];
        return new DatagramPacket(buffer, buffer.length);
    }
}
