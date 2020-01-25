package main.util;

import main.enemies.Enemy;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.BOARD_HEIGHT;
import static main.Main.enemies;

public class EnemyTracker { //keeps track of first last and strongest enemies, I cannot believe this works todo: replace

    private PApplet p;

    public int firstId;
    public PVector firstPos;
    public int lastId;
    public PVector lastPos;
    public int strongId;
    private int strength;
    public PVector strongPos;

    public EnemyTracker(PApplet p) {
        this.p = p;

        firstId = -1; //set to -1 so not associated with any enemy
        firstPos = new PVector(0,-BOARD_HEIGHT);
        lastId = -1;
        lastPos = new PVector(0,2*BOARD_HEIGHT);
        strongId = -1;
        strength = -1;
    }

    public void main(ArrayList<Enemy> enemies) {
        if (enemies.size() > 0){
            trackFirst(enemies);
            trackLast(enemies);
            trackStrong(enemies);
            //debugDisplay(); //points out positions
        }
    }

    private void trackFirst(ArrayList<Enemy> enemies) {
        firstPos = new PVector(0,-BOARD_HEIGHT);
        for (int i = enemies.size()-1; i >= 0; i--) { //chooses an enemy to attack
            Enemy enemy = enemies.get(i);
            if (enemy.position.y > firstPos.y) { //if the enemy is closer to the exit than the current target
                firstPos = new PVector(enemy.position.x,enemy.position.y);
                firstId = i;
            }
        }
    }

    private void trackLast(ArrayList <Enemy> enemies) {
        lastPos = new PVector(0,2*BOARD_HEIGHT);
        for (int i = enemies.size()-1; i >= 0; i--) { //chooses an enemy to attack
            Enemy enemy = enemies.get(i);
            if (enemy.position.y < lastPos.y) { //if the enemy is further from the exit than the current target
                lastPos = new PVector(enemy.position.x,enemy.position.y);
                lastId = i;
            }
        }
    }

    private void trackStrong(ArrayList <Enemy> enemies) {
        strength = -1;
        for (int i = enemies.size()-1; i >= 0; i--) { //chooses an enemy to attack
            Enemy enemy = enemies.get(i);
            if (enemy.dangerLevel > strength || (enemy.dangerLevel == strength && enemy.position.y > strongPos.y)) { //if the enemy is more dangerous than the current target, or same strenght but closer to exit
                strongPos = new PVector(enemy.position.x,enemy.position.y);
                strength = enemy.dangerLevel;
                strongId = i;
            }
        }
    }

    private void debugDisplay() { //debug boxes
        //first
        Enemy first = enemies.get(firstId);
        p.stroke(0,255,0);
        p.fill(0,255,0,100);
        p.rect(firstPos.x-first.size.x/2,firstPos.y-first.size.y/2,first.size.x,first.size.y);
        //last
        Enemy last = enemies.get(lastId);
        p.stroke(0,0,255);
        p.fill(0,0,255,100);
        p.rect(lastPos.x-last.size.x/2,lastPos.y-last.size.y/2,last.size.x,last.size.y);
        //strong
        Enemy strong = enemies.get(strongId);
        p.stroke(255,0,0);
        p.fill(255,0,0,100);
        p.rect(strongPos.x-strong.size.x/2,strongPos.y-strong.size.y/2,strong.size.x,strong.size.y);
    }
}
