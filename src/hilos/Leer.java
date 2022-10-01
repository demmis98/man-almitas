/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hilos;

/**
 *
 * @author KIKA
 */
import java.net.*;
import javax.sound.midi.*;
import java.io.*;
import midi.MidiPlayer;

class Leer extends Thread      // Creamos Hilo heredando de la clase Thread
	{
	Socket socket;
        String nombre;
        MidiPlayer midi;
	Leer( Socket socket, String nombre,MidiPlayer midi)           // Recibe un objeto Socket para indicar qué Socket esta ligado a este proceso
		{
		this.socket=socket;
                this.nombre=nombre;
                this.midi=midi;
		start();   //Iniciar el proceso
		}
	public void run(){
		try{
			while(true){     //bucle infinito para lectura
				InputStream aux = socket.getInputStream();
				DataInputStream flujo = new DataInputStream( aux );
                                String msg=flujo.readUTF();
				System.out.println(msg);
                                if(msg.charAt(0)=='$'||msg.length()>20){
                                    String[] split=msg.split(" ");
                                    if(split[1].equals(nombre)){
                                        ShortMessage message = new ShortMessage();
                                        message.setMessage(Integer.parseInt(split[1]),
                                                Integer.parseInt(split[2]),
                                                Integer.parseInt(split[3]),
                                                Integer.parseInt(split[4]));
                                        System.out.println("uwu");
                                        midi.playMsg(message);
                                        System.out.println("owo");
                                    }
                                }
				}
			}
		catch(Exception e){
			System.out.println("Error");
			}
		}
}