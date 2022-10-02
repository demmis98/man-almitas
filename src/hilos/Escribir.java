/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hilos;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import midi.MidiPlayer;

class Escribir extends Thread 
	{
	Socket socket;
	String name;
	Scanner entrada;
        MidiPlayer midi;
 	Escribir(Socket socket, String name, MidiPlayer midi){      //Recibe objeto de tipo Socket para identificar el Socket que está ejecutando el proceso y
                                                                                     // en el parámetro name recibirá como el cliente desea ser nombrado
		entrada = new Scanner(System.in);  //Objeto para recibir datos desde teclado
		this.socket=socket;
		this.name=name;
                this.midi=midi;
		start();  //Inicia el Hilo, se llama automáticamente al método run()
		}

    public void run(){
        
		try{
                    
			boolean terminar=false;
			String mensaje;
			while(!terminar){      // Creamos bucle infinito para escritura
				OutputStream os= socket.getOutputStream();
				DataOutputStream flujoDOS = new DataOutputStream(os);
				
                                if(midi!=null){
                                    try {
                                        flujoDOS.writeUTF("$ "+midi.getMens());
                                    } catch (IOException ex) {
                                        Logger.getLogger(Escribir.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
			socket.close();
			}
		catch(Exception e){
			System.out.println("Error");
			}
		}
	}

    

