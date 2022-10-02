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
                
                    midi.initSynth();
		start();   //Iniciar el proceso
		}
	public void run(){
		try{
			while(true){     //bucle infinito para lectura
				InputStream aux = socket.getInputStream();
				DataInputStream flujo = new DataInputStream( aux );
                                String msg=flujo.readUTF();
				System.out.println(msg);
                                if(msg.charAt(0)=='$'||msg.length()>3){
                                    String[] split=msg.split(" ");
                                    if(split[2].equals(nombre)){
                                        if(split[1].equals("n")){
                                            midi.playNote(Integer.parseInt(split[4])
                                            ,Integer.parseInt(split[5]));
                                        }else if(split[1].equals("f")){
                                            midi.stopNote(Integer.parseInt(split[4]));
                                        }
                                    }
                                }
				}
			}
		catch(Exception e){
			System.out.println("Error");
			}
		}
}