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
import java.io.*;

class Leer extends Thread      // Creamos Hilo heredando de la clase Thread
	{
	Socket socket;
	Leer( Socket socket)           // Recibe un objeto Socket para indicar qué Socket esta ligado a este proceso
		{
		this.socket=socket;
		start();   //Iniciar el proceso
		}
	public void run(){
		try{
			while(true){     //bucle infinito para lectura
				InputStream aux = socket.getInputStream();
				DataInputStream flujo = new DataInputStream( aux );
				System.out.println(flujo.readUTF() );
				}
			}
		catch(Exception e){
			System.out.println("Error");
			}
		}
}