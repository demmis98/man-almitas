/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package midi;

public class Note {

    private static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    private String name;
    private int key;
    private int octave;

    public Note(int key) {
        this.key = key;
        this.octave = (key / 12)-1;
        int note = key % 12;
        this.name = NOTE_NAMES[note];
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Note && this.key == ((Note) obj).key;
    }

    @Override
    public String toString() {
        return "Note -> " + this.name + this.octave + " key=" + this.key;
    }
}