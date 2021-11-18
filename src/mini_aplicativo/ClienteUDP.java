/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mini_aplicativo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author herme
 */
public class ClienteUDP {
    
    public static void main(String[] args) {

        //puerto del servidor
        final int PUERTO_SERVIDOR = 5000;
        //buffer donde se almacenara los mensajes
        byte[] buffer = new byte[1024];

        try {
            //Obtengo la localizacion de localhost
            InetAddress direccionServidor = InetAddress.getByName("localhost");

            //Creo el socket de UDP
            DatagramSocket socketUDP = new DatagramSocket();
            
            Scanner entrada = new Scanner(System.in);
            String nombre= "";
            nombre= entrada.nextLine().trim();
            String mensaje = "helloiam "+nombre;
            byte[]  buffer4 = mensaje.getBytes();

            
            DatagramPacket pregunta = new DatagramPacket(buffer4, buffer4.length, direccionServidor, PUERTO_SERVIDOR);

           
           // System.out.println("Envio el datagrama  "+mensaje);
            socketUDP.send(pregunta);

     
           byte[] buffer_2 = new byte[1024];
            DatagramPacket peticion = new DatagramPacket(buffer_2, buffer_2.length);


            socketUDP.receive(peticion);
            System.out.println("Recibo la peticion");


            mensaje = new String(peticion.getData(), peticion.getOffset(), peticion.getLength());
            System.out.println("La respuesta del server es"+ mensaje);
            
             if(mensaje.equals("Usuario inexistente")) {
                    System.out.println("Socket Cerrado");
                    //socketUDP.close();
             }
             else if(mensaje.equals("ok")){
                
                 mensaje=nombre;
             
                byte[] buffer3 = mensaje.getBytes();
                pregunta = new DatagramPacket(buffer3, buffer3.length, direccionServidor, PUERTO_SERVIDOR);

            //    System.out.println("Envio el datagrama de nuevo");
                
                System.out.println("Se envia el mensaje "+mensaje);
            
                socketUDP.send(pregunta);
                //socketUDP.close();
             
      
             }
             
             socketUDP.close();

        } catch (SocketException ex) {
            Logger.getLogger(ClienteUDP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteUDP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteUDP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
