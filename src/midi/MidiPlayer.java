/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package midi;
/* http://blog.taragana.com/index.php/archive/how-to-play-a-midi-file-from-a-java-application/ */

import javax.sound.midi.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Plays a midi file provided on command line */
public class MidiPlayer{
    
    static Sequencer sequencer;
    static Transmitter trans;
    static Synthesizer synth;
    static Receiver receiver;
    
    public MidiPlayer(){
        
    }
    
    public static void play(String path) {
        try {
            sequencer = MidiSystem.getSequencer(); // Get the default Sequencer
            if (sequencer==null) {
                System.err.println("Sequencer device not supported");
                return;
            } 
            trans = sequencer.getTransmitter();
            // synth   = MidiSystem.getSynthesizer();
            
            receiver = sequencer.getReceiver(); 
            trans.setReceiver(receiver);  

            sequencer.open(); // Open device
            // Create sequence, the File must contain MIDI file data.
            Sequence sequence = MidiSystem.getSequence(new File(path+".mid"));
            
            MetaEventListener mel = new MetaEventListener() {
                @Override
                public void meta(MetaMessage meta) {
                    final int type = meta.getType();
                    System.out.println("MEL - type: " + type);
                }
            };
            sequencer.addMetaEventListener(mel);

            int[] types = new int[128];
            for (int ii = 0; ii < 128; ii++) {
                types[ii] = ii;
            }
            ControllerEventListener cel = new ControllerEventListener() {
                @Override
                public void controlChange(ShortMessage event) {
                    System.out.println(event.getChannel()+" "+event.getCommand()
                    +" "+event.getData1()+" "+event.getData2());
                    
                }
            };
            int[] listeningTo = sequencer.addControllerEventListener(cel, types);
            for (int ii : listeningTo) {
                System.out.println("Listening To: " + ii);
            }
            
            sequencer.setSequence(sequence); // load it into sequencer
            sequencer.start();  // start the playback
        } catch (MidiUnavailableException | InvalidMidiDataException | IOException ex) {
                ex.printStackTrace();
        }
    }  

}