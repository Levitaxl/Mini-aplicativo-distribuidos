/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mini_aplicativo;

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
            //Creacion del socket
            DatagramSocket socketUDP = new DatagramSocket(PUERTO);

            //Siempre atendera peticiones
            while (true) {
                
                //Preparo la respuesta
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
                
                //Recibo el datagrama
                socketUDP.receive(peticion);
                System.out.println("Recibo la informacion del cliente");
                
                //Convierto lo recibido y mostrar el mensaje
                String mensaje = new String(peticion.getData());
                String respuesta2= leetTxt(mensaje.trim());

           
                //Obtengo el puerto y la direccion de origen
                //Sino se quiere responder, no es necesario
                int puertoCliente = peticion.getPort();
                InetAddress direccion = peticion.getAddress();
                
                buffer = respuesta2.getBytes();

                //creo el datagrama
                DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length, direccion, puertoCliente);

                //Envio la información
                System.out.println("Envio la informacion del cliente");
                socketUDP.send(respuesta);
                
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
                System.out.println("helloiam "+bfRead+nombre);
                if(nombre.equalsIgnoreCase("helloiam "+bfRead) || (bfRead == nombre)) 
                    return "ok";
            }
        } catch (Exception e) { System.out.println("No se encontro el archivo");
        }
         myLog.addLine("Usuario inexistente");
        return "Usuario inexistente";
     
    }
    

}
