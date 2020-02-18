package main.buffs;

import processing.core.PApplet;

import static main.Main.buffs;

public class Wet extends Buff{
    public Wet(PApplet p, int enId){
        super(p,enId);
        effectDelay = 6; //frames
        lifeDuration = 600;
        particle = "water";
        name = "wet";
        this.enId = enId;
    }

    public void effect(){ //removes fire
        for (int i = buffs.size()-1; i >= 0; i--){
            Buff buff = buffs.get(i);
            if (buff.particle.equals("fire") && buff.enId == enId){
                buffs.remove(i);
            }
        }
    }
}