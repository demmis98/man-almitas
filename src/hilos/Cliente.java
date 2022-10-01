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
import java.util.*;
import midi.MidiPlayer;
public class Cliente 
    { 
        MidiPlayer midi;
    Scanner entrada;          
     Cliente()     {	 
       entrada = new Scanner(System.in);         
       try         {             
                      Socket skCliente = new Socket ("localhost", 5000);             			
                      System.out.println("Introduce tu Nombre:");			 
                      String nombre=entrada.next();	
                      if(nombre.equals("master")){
                        midi=new MidiPlayer();
                        System.out.println("cual?");
                        String path=entrada.next(); 
                        midi.play("res/"+path);
                      }
                      Escribir hilo1 =new Escribir(skCliente,nombre, midi);  //hilo que escribe se envía el nombre ingresado por el cliente y el socket 
                      Leer hilo2= new Leer(skCliente);    //hilo que lee, se envía como parámetro el Socket				   skCliente.close();         
                     }         
      catch (Exception e)    {  
          e.printStackTrace();      
      }     
      }	 
public static void main (String [] args)    
          {         new Cliente();     }     
}

