package main.enemies;

import main.Main;
import main.buffs.Buff;
import main.buffs.Burning;
import main.buffs.Poisoned;
import main.buffs.Wet;
import main.particles.Ouch;
import main.pathfinding.PathRequest;
import main.towers.Tower;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

import static main.Main.*;
import static main.util.MiscMethods.findAngleBetween;
import static main.util.MiscMethods.roundTo;

public abstract class Enemy {

    private PApplet p;

    public ArrayList<TurnPoint> points;
    public int pfSize;
    public PVector position;
    public PVector size;
    private float angle;
    public float radius;
    public float maxSpeed;
    public float speed;
    public int dangerLevel;
    int twDamage;
    public int maxHp;
    public int hp;
    private PImage sprite;
    private PImage[] attackFrames;
    private PImage[] moveFrames;
    private float moveFrame;
    int attackFrame;
    private boolean attacking;
    int numAttackFrames;
    int numMoveFrames;
    int startFrame;
    public int barTrans;
    public int tintColor;
    String hitParticle;
    public String name;

    public Enemy(PApplet p, float x, float y) {
        this.p = p;

        points = new ArrayList<>();
        position = new PVector(roundTo(x,25)+12.5f, roundTo(y,25)+12.5f);
        size = new PVector(20,20);
        angle = 0;
        radius = 10;
        maxSpeed = 1;
        speed = maxSpeed;
        dangerLevel = 1;
        twDamage = 1;
        maxHp = 20; //Hp <---------------------------
        hp = maxHp;
        barTrans = 0;
        tintColor = 255;
        hitParticle = "redOuch";
        name = "null";
        numAttackFrames = 1;
        numMoveFrames = 1;
        startFrame = 0;
        loadSprites();
        pfSize = 1; //enemies pathfinding size, multiplied by twenty-five
    }

    public void main(int i) {
        boolean dead = false; //if its gotten this far, it must be alive?
        swapPoints(false);
        collideTW();
        if (!attacking) move();
        display();
        //prevent from going offscreen
        if (position.x >= GRID_WIDTH || position.x <= 0 || position.y >= GRID_HEIGHT || position.y <= 0) dead = true;
        //if health is 0, die
        if (hp <= 0) dead = true;
        if (dead) die(i);
    }

    private void die(int i) {
        Main.money += dangerLevel;
        int num = (int)(p.random(2,5));
        for (int j = num; j >= 0; j--) { //creates death particles
            particles.add(new Ouch(p,position.x+p.random((size.x/2)*-1,size.x/2), position.y+p.random((size.y/2)*-1,size.y/2), p.random(0,360), "greyPuff"));
        }
        for (int j = buffs.size()-1; j >= 0; j--) { //deals with buffs
            Buff buff = buffs.get(j);
            //if attached, remove
            if (buff.enId == i) buffs.remove(j);
            //shift ids to compensate for enemy removal
            else if (buff.enId > i) buff.enId -= 1;
        }
        enemies.remove(i);
    }

    private void move(){
        PVector m = PVector.fromAngle(angle);
        m.setMag(speed);
        position.add(m);
        if (points.size() != 0) {
            PVector p = points.get(points.size()-1).position;
            boolean intersecting;
            intersecting = (position.x > p.x && position.x < p.x+nSize+size.x) && (position.y > p.y && position.y < p.y+nSize+size.y);
            if (intersecting) swapPoints(true);
        }
        speed = maxSpeed;
    }

    private void preDisplay(){
        if (attacking){
            sprite = attackFrames[attackFrame];
            if (attackFrame < numAttackFrames-1){
                attackFrame += 1;
            }
            else {
                attackFrame = 0;
            }
        }
        else{
            sprite = moveFrames[(int)(moveFrame)];
            if (moveFrame < numMoveFrames-1){
                moveFrame += speed;
            }
            else{
                moveFrame = 0;
            }
        }
        if (tintColor < 255){ //shift back to normal
            tintColor += 20;
        }
    }

    private void display(){
        preDisplay();
        if (debugPathfinding){
            for (int i = points.size()-1; i > 0; i--){
                points.get(i).display();
            }
        }
        p.pushMatrix();
        p.translate(position.x,position.y);
        p.rotate(angle);
        p.image(sprite,-size.x/2,-size.y/2);
        p.popMatrix();
        if (hp > 0){
            HpBar();
        }
    }

    private void collideTW(){ //when the enemy hits a tower
        boolean ak = false;
        for (int i = 0; i > tiles.size(); i++){
            if (tiles.get(i).tower != null) {
                Tower tower = tiles.get(i).tower;
                float dx = (tower.tile.position.x - tower.size.x / 2) - (position.x);
                float dy = (tower.tile.position.y - tower.size.y / 2) - (position.y);
                if (dy <= size.y / 2 + tower.size.y / 2 && dy >= -(tower.size.y / 2) - size.y / 2 && dx <= size.x / 2 + tower.size.x / 2 && dx >= -(tower.size.x / 2) - size.x / 2) { //if touching tower
                    attacking = true;
                    ak = true;
                    moveFrame = 0;
                    if (attackFrame == numAttackFrames - 1) { //enemy only attacks when punch
                        tower.collideEN(twDamage);
                    }
                }
            }
        }
        if (!ak && attackFrame == startFrame){
            attacking = false;
            attackFrame = startFrame;
        }
    }

    public void collidePJ(int damage, String pjBuff, int i){ //when the enemy hits a projectile
        hp -= damage;
        if (pjBuff.equals("poison")){ //applies buffs
            if (buffs.size() > 0){
                for (int j = buffs.size()-1; j >= 0; j--){
                    Buff buff = buffs.get(j);
                    if (buff.particle.equals("poison") && buff.enId == i){
                        buffs.remove(j);
                    }
                }
            }
            buffs.add(new Poisoned(p,i));
        }
        if (pjBuff.equals("wet")){
            if (buffs.size() > 0){
                for (int j = buffs.size()-1; j >= 0; j--){
                    Buff buff = buffs.get(j);
                    if (buff.particle.equals("water") && buff.enId == i){
                        buffs.remove(j);
                    }
                }
            }
            buffs.add(new Wet(p,i));
        }
        if (pjBuff.equals("burning")){
            if (buffs.size() > 0){
                for (int j = buffs.size()-1; j >= 0; j--){
                    Buff buff = buffs.get(j);
                    if (buff.particle.equals("fire") && buff.enId == i){
                        buffs.remove(j);

                    }
                }
            }
            buffs.add(new Burning(p,i));
        }
        barTrans = 255;
        tintColor = 0;
        int num = (int)(p.random(1,3));
        for (int j = num; j >= 0; j--){ //sprays ouch
            particles.add(new Ouch(p,position.x+p.random((size.x/2)*-1,size.x/2), position.y+p.random((size.y/2)*-1,size.y/2), p.random(0,360), hitParticle));
        }
    }

    private void HpText(){ //displays the enemies health
        p.text(hp, position.x, position.y + size.y/2 + 12);
    }

    private void HpBar() {
        p.fill(255,0,0,barTrans);
        p.noStroke();
        p.rect(position.x-size.x, position.y+size.y/2 + 12, (2*size.x)*(((float) hp)/((float) maxHp)), 6);
    }

    public void loadSprites() {
        attackFrames = spritesAnimH.get(name+"AttackEN");
        moveFrames = spritesAnimH.get(name+"MoveEN");
    }

    //pathfinding

    public void requestPath(int i) {
        path.reqQ.add(new PathRequest(i,enemies.get(i)));
    }

    public void swapPoints(boolean remove) {
        if (remove) points.remove(points.size() - 1);
        if (points.size() != 0) {
            PVector pointPosition = points.get(points.size()-1).position;
            pointPosition = new PVector(pointPosition.x+12.5f,pointPosition.y+12.5f);
            angle = findAngleBetween(pointPosition,position);
        }
    }

    public void cleanTurnPoints() { //todo: dont remove points on towers
        ArrayList<TurnPoint> pointsD = new ArrayList<>(points);
        for (int i = 0; i < pointsD.size()-2; i++) {
            TurnPoint pointA = pointsD.get(i);
            TurnPoint pointB = pointsD.get(i+1);
            TurnPoint pointC = pointsD.get(i+2);
            float angleAB = findAngleBetween(pointA.position, pointB.position);
            float angleBC = findAngleBetween(pointB.position, pointC.position);
            if (angleAB == angleBC) {
                pointsD.remove(pointB);
                i--;
            }
            if (i+1 == pointsD.size()+2) break;
        }
        points = new ArrayList<>();
        points.addAll(pointsD);
    }

    public static class TurnPoint {

        public PVector position;
        private PApplet p;

        public TurnPoint(PApplet p, PVector position) {
            this.p = p;
            this.position = new PVector(position.x,position.y);
        }

        public void display() {
            p.fill(255);
            p.ellipse(position.x+nSize/2f,position.y+nSize/2f,nSize,nSize);
        }
    }
}
