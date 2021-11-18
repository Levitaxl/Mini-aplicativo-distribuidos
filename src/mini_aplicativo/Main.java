/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mini_aplicativo;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author herme
 */
public class Main {
    
    private static void tcp() throws IOException{
        ClienteTCP clienteTCP= new ClienteTCP();
        clienteTCP.invoke();
    }
    
    private static void udp() throws IOException{
        ClienteUDP clienteUDP= new ClienteUDP();
        clienteUDP.invoke();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Seleccione el protocolo a usar (TCP o UDP)");
        Scanner entrada = new Scanner(System.in);
        String protocolo= entrada.nextLine().trim();

        if(protocolo.equals("TCP")){
            try {tcp();} 
            catch (Exception e) {System.out.println("Error al crear la conexion TCP");}
        }
        else if(protocolo.equals("UDP")){
            try {udp();} 
            catch (Exception e) {System.out.println("UDP");}
        }
        else System.out.println("Ha ocurrido un error");
    }
    
}
