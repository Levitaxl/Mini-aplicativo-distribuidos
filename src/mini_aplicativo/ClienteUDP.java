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
    
    public void invoke() {

        //puerto del servidor
        final int PUERTO_SERVIDOR = 5000;
        try {
            InetAddress direccionServidor = InetAddress.getByName("localhost");
            DatagramSocket socketUDP = new DatagramSocket();
            
            Scanner entrada = new Scanner(System.in,"ISO-8859-1");
            String nombre= "helloiam "+ entrada.nextLine().trim();
            
            SendData(nombre,socketUDP,direccionServidor,PUERTO_SERVIDOR);
            String mensaje_peticion = ReceiveData(socketUDP);
            
            if(mensaje_peticion.equals("Usuario inexistente")) System.out.println("Usuario inexistente");
            else if(mensaje_peticion.equals("ok")){
                String mensaje= entrada.nextLine().trim();
                System.out.println("Se envia el mensaje "+mensaje);
                SendData(mensaje,socketUDP,direccionServidor,PUERTO_SERVIDOR);
            }
            System.out.println("Socket Cerrado");
            socketUDP.close();

        } catch (SocketException ex) {
            Logger.getLogger(ClienteUDP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteUDP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteUDP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private static void SendData(String data, DatagramSocket socketUDP, InetAddress direccionServidor,int PUERTO_SERVIDOR) throws IOException {
        byte[]  buffer = data.getBytes();
        DatagramPacket paquete_nombre = new DatagramPacket(buffer, buffer.length, direccionServidor, PUERTO_SERVIDOR);
        socketUDP.send(paquete_nombre);
    }
    
    
    private static String ReceiveData(DatagramSocket socketUDP) throws IOException{
        byte[] buffer = new byte[1024];
        DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
        socketUDP.receive(peticion);
        return new String(peticion.getData(), peticion.getOffset(), peticion.getLength());
    }
}
