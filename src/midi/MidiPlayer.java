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
    static Instrument[] instr;
    static MidiChannel[] mChannels;
    
    static String mens;
    static int note=0;
    
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
            synth   = MidiSystem.getSynthesizer();
            
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
                    mens="";
                    if(event.getCommand()==176){
                        mens+="n";
                    }else if(event.getCommand()==ShortMessage.NOTE_OFF){
                        mens+="f";
                    }else{
                        mens+="w";
                    }
                    mens+=" "+event.getChannel()+" "+event.getCommand()
                    +" "+event.getData1()+" "+event.getData2();
                    System.out.println(mens);
                    
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

    public static String getMens() {
        return mens;
    }

    public static int getNote() {
        return note;
    }
    public void playMsg(MidiMessage msg){
        try {
            sequencer.getTransmitter().getReceiver().send(msg, -1);
        } catch (MidiUnavailableException ex) {
            Logger.getLogger(MidiPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void initSynth() { 
      try{
        /* Create a new Sythesizer and open it. Most of 
         * the methods you will want to use to expand on this 
         * example can be found in the Java documentation here: 
         * https://docs.oracle.com/javase/7/docs/api/javax/sound/midi/Synthesizer.html
         */
        Synthesizer midiSynth = MidiSystem.getSynthesizer(); 
        midiSynth.open();
    
        //get and load default instrument and channel lists
        instr = midiSynth.getDefaultSoundbank().getInstruments();
        mChannels = midiSynth.getChannels();
        
        midiSynth.loadInstrument(instr[0]);//load an instrument
    
   
    
    
      } catch (MidiUnavailableException e) {
         e.printStackTrace();
      }
    }
    public void playNote(int note,int vel){
        mChannels[0].noteOn(note, vel);//On channel 0, play note number 60 with velocity 100 
    }
    public void stopNote(int note){
        mChannels[0].noteOff(note, 100);//On channel 0, play note number 60 with velocity 100 
    }
}