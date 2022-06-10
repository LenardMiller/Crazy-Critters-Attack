package main.enemies;

import com.sun.istack.internal.Nullable;
import main.Main;
import main.buffs.*;
import main.buffs.glued.Glued;
import main.buffs.glued.SpikeyGlued;
import main.buffs.stunned.Frozen;
import main.buffs.stunned.Stunned;
import main.enemies.burrowingEnemies.*;
import main.enemies.flyingEnemies.*;
import main.enemies.shootingEnemies.*;
import main.gui.guiObjects.PopupText;
import main.misc.Corpse;
import main.misc.Tile;
import main.particles.Floaty;
import main.particles.MiscParticle;
import main.particles.Ouch;
import main.particles.Pile;
import main.pathfinding.Node;
import main.pathfinding.PathRequest;
import main.sound.MoveSoundLoop;
import main.towers.Tower;
import main.towers.turrets.Turret;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.sound.SoundFile;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static main.Main.*;
import static main.misc.Utilities.*;
import static main.sound.SoundUtilities.playSoundRandomSpeed;

public abstract class Enemy {

    public enum State {
        Moving,
        Attacking,
        Special
    }

    /** measured in pixels per second */
    public float speed;
    public float speedModifier;
    public float rotation;
    public float radius;
    public boolean showBar;
    public int hp;
    public int maxHp;
    public int attackFrame;
    public int pfSize;
    public int intersectingIceCount;
    public int[] attackDmgFrames;
    /** something to do with glue */
    public int[] tempAttackDmgFrames;
    public boolean immobilized;
    public ArrayList<TurnPoint> trail;
    public PImage[] attackFrames;
    public String hitParticle;
    public String lastDamageType;
    public String name;
    public PVector position;
    public PVector size;
    public State state = State.Moving;

    protected int moneyDrop;
    protected int damage;
    protected int betweenWalkFrames;
    protected int betweenAttackFrames;
    protected int betweenCorpseFrames;
    protected int corpseLifespan;
    protected int pathRequestWaitTimer;
    protected int moveFrame;
    protected int idleTime;
    protected float targetAngle;
    protected boolean overkill;
    protected PApplet p;
    protected PVector partsDirection;
    protected PVector corpseSize;
    protected PVector partSize;
    protected PImage[] moveFrames;
    protected PImage sprite;
    protected Color maxTintColor;
    protected Color currentTintColor;
    protected SoundFile overkillSound;
    protected SoundFile dieSound;
    protected SoundFile attackSound;
    protected MoveSoundLoop moveSoundLoop;

    protected int attackCount;
    protected boolean attackCue;
    protected boolean targetMachine;
    protected Tower targetTower;

    protected Enemy(PApplet p, float x, float y) {
        this.p = p;

        trail = new ArrayList<>();
        position = new PVector(roundTo(x, 25) + 12.5f, roundTo(y, 25) + 12.5f);
        size = new PVector(20, 20);
        rotation = radians(270);
        radius = 10;
        speed = 60;
        speedModifier = 1;
        moneyDrop = 1;
        damage = 1;
        maxHp = 20; //Hp <---------------------------
        hp = maxHp;
        hitParticle = "redOuch";
        name = "";
        betweenWalkFrames = 0;
        attackDmgFrames = new int[]{0};
        tempAttackDmgFrames = new int[attackDmgFrames.length];
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        pfSize = 1;
        attackCount = 0;
        corpseSize = size;
        partSize = size;
        betweenCorpseFrames = down60ToFramerate(7);
        corpseLifespan = 8;
        lastDamageType = "normal";
    }

    public void main(int i) {
        boolean dead = false; //if its gotten this far, it must be alive?
        swapPoints(false);

        if (!paused && !immobilized) {
            rotation = normalizeAngle(rotation);
            targetAngle = normalizeAngle(targetAngle);
            rotation += getAngleDifference(targetAngle, rotation) / 10;

            switch (state) {
                case Moving:
                    move();
                    break;
                case Attacking:
                    attack();
                    break;
            }

            //prevent wandering
            if (trail.size() == 0 && state != State.Attacking) pathRequestWaitTimer++;
            if (pathRequestWaitTimer > FRAMERATE) {
                requestPath(i);
                pathRequestWaitTimer = 0;
            }
        }
        if (trail.size() != 0 && intersectTurnPoint()) swapPoints(true);
        displayMain();
        //if health is 0, die
        if (hp <= 0) dead = true;
        if (dead) die(i);
    }

    /**
     * Adds money with a popup.
     * Plays death sound.
     * If overkill, fling bits everywhere, else create a corpse.
     * Clear buffs.
     * Remove from array.
     * @param i id for buff stuff
     */
    protected void die(int i) {
        Main.money += moneyDrop;
        popupTexts.add(new PopupText(p, new PVector(position.x, position.y), moneyDrop));

        String type = lastDamageType;
        for (Buff buff : buffs) {
            if (buff.enId == i) type = buff.name;
        }
        if (overkill) playSoundRandomSpeed(p, overkillSound, 1);
        else playSoundRandomSpeed(p, dieSound, 1);

        if (gore) goreyDeathEffect(type);
        else cleanDeathEffect();

        for (int j = buffs.size() - 1; j >= 0; j--) { //deals with buffs
            Buff buff = buffs.get(j);
            //if attached, remove
            if (buff.enId == i) {
                buffs.get(j).dieEffect();
                buffs.remove(j);
            } //shift ids to compensate for enemy removal
            else if (buff.enId > i) buff.enId -= 1;
        }

        enemies.remove(i);
    }

    protected void goreyDeathEffect(String type) {
        if (overkill) {
            for (int j = 0; j < animatedSprites.get(name + "PartsEN").length; j++) {
                float maxRotationSpeed = up60ToFramerate(200f / partSize.x);
                corpses.add(new Corpse(p, position, partSize, rotation, adjustPartVelocityToFramerate(partsDirection),
                  currentTintColor ,p.random(radians(-maxRotationSpeed), radians(maxRotationSpeed)),
                  0, corpseLifespan, type, name + "Parts", hitParticle, j, false));
            }
            for (int k = 0; k < sq(pfSize); k++) {
                bottomParticles.add(new Pile(p, (float) (position.x + 2.5 + p.random((size.x / 2) * -1,
                  (size.x / 2))), (float) (position.y + 2.5 + p.random((size.x / 2) * -1, (size.x / 2))),
                  0, hitParticle));
            }
        } else
            corpses.add(new Corpse(p, position, corpseSize,
              rotation + p.random(radians(-5), radians(5)), new PVector(0, 0),
              currentTintColor, 0, betweenCorpseFrames, corpseLifespan, type, name + "Die",
              "none", 0, true));
    }

    protected void cleanDeathEffect() {
        int num = (int) p.random(pfSize, pfSize * pfSize);
        if (!overkill) {
            for (int i = 0; i < num * 5; i++) {
                PVector partPos = getParticlePosition();
                topParticles.add(new Floaty(p, partPos.x, partPos.y, p.random(20, 30), "smokeCloud"));
            }
        } else {
            for (int i = 0; i < num * 10; i++) {
                PVector partPos = getParticlePosition();
                topParticles.add(new Floaty(p, partPos.x, partPos.y, p.random(50 * pfSize, 100 * pfSize), "smokeCloud"));
            }
        }
        for (int j = num; j >= 0; j--) { //sprays puff
            PVector partPos = getParticlePosition();
            topParticles.add(new Ouch(p, partPos.x, partPos.y, p.random(0, 360), "greyPuff"));
        }
    }

    protected PVector getParticlePosition() {
        return getRandomPointInRange(p, position, size.mag() * 0.4f);
    }

    protected PVector adjustPartVelocityToFramerate(PVector partVelocity) {
        return partVelocity.setMag(partVelocity.mag() * up60ToFramerate(1));
    }

    protected void move() {
        if (moveSoundLoop != null) moveSoundLoop.increment();
        PVector m = PVector.fromAngle(rotation);
        float pixelsMoved = getActualSpeed() / FRAMERATE;
        m.setMag(pixelsMoved);
        //don't move if no path
        if (trail.size() > 0) position.add(m);
    }

    public float getActualSpeed() {
        float actualSpeed = speed * speedModifier;
        if (actualSpeed > 20 && intersectCombatPoint()) actualSpeed = 20;
        return actualSpeed;
    }

    /** handle animation states */
    protected void animate() {
        if (!immobilized) {
            switch (state) {
                case Attacking:
                    if (attackFrame >= attackFrames.length) attackFrame = 0;
                    sprite = attackFrames[attackFrame];
                    idleTime++;
                    if (attackFrame < attackFrames.length - 1) {
                        if (idleTime >= betweenAttackFrames) {
                            attackFrame += 1;
                            idleTime = 0;
                        }
                    } else attackFrame = 0;
                    break;
                case Moving:
                    idleTime++;
                    if (moveFrame < moveFrames.length - 1) {
                        if (idleTime >= betweenWalkFrames) {
                            moveFrame++;
                            idleTime = 0;
                        }
                    } else moveFrame = 0;
                    sprite = moveFrames[moveFrame];
            }
        }
        //shift back to normal
        currentTintColor = incrementColorTo(currentTintColor, up60ToFramerate(20), new Color(255, 255, 255));
    }

    /**
     * Displays but tinted black and semi-transparent.
     * Calls to animate sprite.
     */
    public void displayShadow() {
        if (!paused) animate();
        p.pushMatrix();
        p.tint(0, 60);
        int x = 1;
        if (pfSize > 1) x++;
        p.translate(position.x + x, position.y + x);
        p.rotate(rotation);
        if (sprite != null) p.image(sprite, -size.x / 2, -size.y / 2);
        p.tint(255);
        p.popMatrix();
    }

    /** Display main sprite */
    public void displayMain() {
        if (debug) for (int i = trail.size() - 1; i > 0; i--) {
            trail.get(i).display();
        }
        p.pushMatrix();
        p.translate(position.x, position.y);
        p.rotate(rotation);
        p.tint(currentTintColor.getRGB());
        if (sprite != null) p.image(sprite, -size.x / 2, -size.y / 2);
        p.popMatrix();
        if (debug) {
            PVector pfPosition = new PVector(position.x - ((pfSize - 1) * 12.5f), position.y - ((pfSize - 1) * 12.5f));
            p.stroke(0, 0, 255);
            p.line(pfPosition.x - 10, pfPosition.y, pfPosition.x + 10, pfPosition.y);
            p.stroke(255, 0, 0);
            p.line(pfPosition.x, pfPosition.y - 10, pfPosition.x, pfPosition.y + 10);
            p.noFill();
            p.stroke(255, 0, 255);
            p.rect(pfPosition.x - 12.5f, pfPosition.y - 12.5f, pfSize * 25, pfSize * 25);
        }
    }

    /**
     * Applies damage to the enemy, can also apply a buff
     * @param damage the amount of damage to be taken
     * @param buffName the name of the buff to be applied, nullable
     * @param effectLevel level of the buff to be applied
     * @param effectDuration duration of the buff to be applied
     * @param turret turret that caused the damage, nullable
     * @param displayParticles if should create particles
     * @param damageType determines what effect to apply to corpse
     * @param direction determines where parts will be flung, (0, 0) for everywhere
     * @param id id of this enemy, set to -1 if unknown
     */
    public void damageWithBuff(int damage, String buffName, float effectLevel, float effectDuration, Turret turret,
                               boolean displayParticles, String damageType, PVector direction, int id) {
        if (id == -1 && buffName != null) id = getId();
        lastDamageType = damageType;
        overkill = damage >= maxHp;
        partsDirection = direction;
        hp -= damage;
        if (turret != null) {
            int statDamage = damage;
            if (hp <= 0) {
                turret.killsTotal++;
                statDamage = damage + hp;
            }
            if (statDamage > 0) turret.damageTotal += statDamage;
        }
        int effectTimer = p.frameCount + 10;
        //prevent duplicates
        if (buffs.size() > 0) {
            for (int j = 0; j < buffs.size(); j++) {
                Buff buff = buffs.get(j);
                if (buff.enId == id && buff.name.equals(buffName)) {
                    effectTimer = buff.effectTimer;
                    buffs.remove(j);
                    break;
                }
            }
        }
        if (buffName != null) {
            Buff buff;
            switch (buffName) {
                case "burning":
                    buff = new Burning(p, id, effectLevel, effectDuration, turret);
                    break;
                case "blueBurning":
                    buff = new BlueBurning(p, id, effectLevel, effectDuration, turret);
                    break;
                case "bleeding":
                    buff = new Bleeding(p, id, turret);
                    break;
                case "poisoned":
                    buff = new Poisoned(p, id, turret);
                    break;
                case "decay":
                    if (turret != null) buff = new Decay(p, id, effectLevel, effectDuration, turret);
                    else buff = new Decay(p, id, 1, 120, null);
                    break;
                case "glued":
                    buff = new Glued(p, id, effectLevel, effectDuration, turret);
                    break;
                case "spikeyGlued":
                    buff = new SpikeyGlued(p, id, effectLevel, effectDuration, turret);
                    break;
                case "stunned":
                    buff = new Stunned(p, id, turret);
                    break;
                case "frozen":
                    buff = new Frozen(p, id, turret);
                    break;
                case "electrified":
                    buff = new Electrified(p, id, (int) effectLevel, effectDuration, turret);
                    break;
                default:
                    buff = null;
                    break;
            }
            if (buff != null) {
                //in order to prevent resetting timer after buff is reapplied
                buff.effectTimer = effectTimer;
                buffs.add(buff);
            }
        }
        damageEffect(displayParticles);
    }

    /**
     * Applies damage to the enemy
     * @param damage amount of damage to be applied
     * @param turret the turret that caused the damage, nullable
     * @param damageType determines what effect to apply to corpse
     * @param direction where parts will be flung, (0, 0) for everywhere
     * @param displayParticles whether it should spawn particles
     */
    public void damageWithoutBuff(int damage, @Nullable Turret turret, String damageType, PVector direction, boolean displayParticles) {
        lastDamageType = damageType;
        overkill = damage >= maxHp;
        partsDirection = direction;
        hp -= damage;
        if (turret != null) {
            if (hp <= 0) {
                turret.killsTotal++;
                turret.damageTotal += damage + hp;
            } else turret.damageTotal += damage;
        }
        damageEffect(displayParticles);
    }

    private int getId() {
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i) == this) return i;
        }
        return -1;
    }

    /**
     * Display hp bar.
     * Tint.
     * @param particles whether or not to display hurt particles
     */
    protected void damageEffect(boolean particles) {
        if (hp == maxHp) return;
        showBar = true;
        if (particles) {
            int num = pfSize;
            int chance = 5;
            if (notRecentlyHit()) {
                num = (int) p.random(pfSize, pfSize * pfSize);
                chance = 0;
            }
            if (p.random(6) > chance) {
                for (int j = num; j >= 0; j--) { //sprays ouch
                    PVector partPos = getParticlePosition();
                    if (gore) topParticles.add(new Ouch(p, partPos.x, partPos.y, p.random(0, 360), hitParticle));
                    else topParticles.add(new MiscParticle(p, partPos.x, partPos.y, p.random(0, 360), "stun"));
                }
            }
            currentTintColor = new Color(maxTintColor.getRGB());
        }
    }

    private boolean notRecentlyHit() {
        int totalTintColor = currentTintColor.getRed() + currentTintColor.getGreen() + currentTintColor.getBlue();
        return totalTintColor >= 700;
    }

    public void hpBar() {
        if (!showBar) return;
        Color barColor = new Color(255, 0, 0);
        float barWidth = size.x * (hp / (float) maxHp);
        p.stroke(barColor.getRGB());
        p.noFill();
        p.rect(position.x - size.x / 2, position.y + size.y / 2 + 6, size.x, 6);
        p.fill(barColor.getRGB());
        p.rect(position.x - size.x / 2, position.y + size.y / 2 + 6, barWidth, 6);
    }

    protected void loadStuff() {
        hp = maxHp;
        System.arraycopy(attackDmgFrames, 0, tempAttackDmgFrames, 0, tempAttackDmgFrames.length);
        attackFrames = animatedSprites.get(name + "AttackEN");
        moveFrames = animatedSprites.get(name + "MoveEN");
        sprite = moveFrames[0];
        maxTintColor = getTintColor();
        currentTintColor = new Color(255, 255, 255);
    }

    protected Color getTintColor() {
        switch (hitParticle) {
            case "glowOuch":
                return new Color(0, 255, 195);
            case "greenOuch":
                return new Color(100, 166, 0);
            case "leafOuch":
                return new Color(19, 183, 25);
            case "lichenOuch":
                return new Color(144, 146, 133);
            case "pinkOuch":
                return new Color(255, 0, 255);
            case "redOuch":
                return new Color(216, 0, 0);
            case "iceOuch":
                return new Color(49, 135, 223);
            case "mudOuch":
                return new Color(111, 58, 0);
            case "sapOuch":
                return new Color(0xb76e09);
            case "brownLeafOuch":
                return new Color(0xBB5E3B);
            default:
                return new Color(255, 0, 0);
        }
    }

    /**
     * Angles towards target.
     * Damages target turret or machine.
     * Messes with state a bit.
     * Prevents attacking multiple times at once.
     */
    protected void attack() {
        boolean dmg = false;
        for (int frame : tempAttackDmgFrames) {
            if (attackFrame == frame) {
                if (betweenAttackFrames > 1) attackCount++;
                dmg = true;
                break;
            }
        }
        //all attackCount stuff prevents attacking multiple times
        if (!dmg) attackCount = 0;
        if (attackCount > 1) dmg = false;
        if (targetTower != null && targetTower.alive) {
            if (pfSize > 2) { //angle towards tower correctly
                PVector t = new PVector(targetTower.tile.position.x - 25, targetTower.tile.position.y - 25);
                targetAngle = findAngleBetween(t, position);
            }
            moveFrame = 0;
            //actually do damage to towers
            if (dmg) {
                targetTower.damage(damage);
                //ignored if no assigned attack sound
                playSoundRandomSpeed(p, attackSound, 1);
            }
        } else if (!targetMachine) state = State.Moving;
        if (targetMachine) {
            moveFrame = 0;
            //actually do damage to machines
            if (dmg) {
                machine.damage(damage);
                //ignored if no assigned attack sound
                playSoundRandomSpeed(p, attackSound, 1);
            }
        }
        if (!attackCue && attackFrame == 0) state = State.Moving;
    }

    public boolean onScreen() {
        return position.x > 0 && position.x < 900 && position.y > 0 && position.y < 900;
    }

    public static Enemy get(PApplet p, String name, PVector pos) {
        switch (name) {
            case "smolBug":
                return new SmolBug(p, pos.x, pos.y);
            case "midBug":
                return new MidBug(p, pos.x, pos.y);
            case "Big Bugs":
            case "bigBug":
                return new BigBug(p, pos.x, pos.y);
            case "treeSprite":
                return new TreeSprite(p, pos.x, pos.y);
            case "Tree Spirits":
            case "treeSpirit":
                return new TreeSpirit(p, pos.x, pos.y);
            case "Tree Giants":
            case "treeGiant":
                return new TreeGiant(p, pos.x, pos.y);
            case "snake":
                return new Snake(p, pos.x, pos.y);
            case "littleWorm":
            case "worm":
                return new Worm(p, pos.x, pos.y);
            case "butterfly":
                return new Butterfly(p, pos.x, pos.y);
            case "scorpion":
                return new Scorpion(p, pos.x, pos.y);
            case "sidewinder":
                return new Sidewinder(p, pos.x, pos.y);
            case "emperor":
                return new Emperor(p, pos.x, pos.y);
            case "midWorm":
                return new MidWorm(p, pos.x, pos.y);
            case "Worms":
            case "Megaworms":
            case "bigWorm":
                return new BigWorm(p, pos.x, pos.y);
            case "albinoBug":
                return new AlbinoBug(p, pos.x, pos.y);
            case "bigAlbinoBug":
                return new BigAlbinoBug(p, pos.x, pos.y);
            case "albinoButterfly":
                return new AlbinoButterfly(p, pos.x, pos.y);
            case "smallGolem":
                return new SmallGolem(p, pos.x, pos.y);
            case "midGolem":
                return new Golem(p, pos.x, pos.y);
            case "bigGolem":
                return new GiantGolem(p, pos.x, pos.y);
            case "bat":
                return new Bat(p, pos.x, pos.y);
            case "bigBat":
                return new GiantBat(p, pos.x, pos.y);
            case "wtf":
                return new Wtf(p, pos.x, pos.y);
            case "antlion":
                return new Antlion(p, pos.x, pos.y);
            case "Antlions":
            case "snowAntlion":
                return new SnowAntlion(p, pos.x, pos.y);
            case "Wolves":
            case "wolf":
                return new Wolf(p, pos.x, pos.y);
            case "Snow Sharks":
            case "shark":
                return new Shark(p, pos.x, pos.y);
            case "Velociraptors":
            case "velociraptor":
                return new Velociraptor(p, pos.x, pos.y);
            case "Ice Entities":
            case "iceEntity":
                return new IceEntity(p, pos.x, pos.y);
            case "Ice Monstrosity":
            case "Ice Monstrosities":
            case "iceMonstrosity":
                return new IceMonstrosity(p, pos.x, pos.y);
            case "Frost":
            case "frost":
                return new Frost(p, pos.x, pos.y);
            case "Mammoth":
            case "Mammoths":
            case "mammoth":
                return new Mammoth(p, pos.x, pos.y);
            case "Mud Creatures":
            case "mudCreature":
                return new MudCreature(p, pos.x, pos.y);
            case "Mud Flingers":
            case "mudFlinger":
                return new MudFlinger(p, pos.x, pos.y);
            case "Enraged Giants":
            case "Enraged Giant":
            case "enragedGiant":
                return new EnragedGiant(p, pos.x, pos.y);
            case "Mantises":
            case "Mantis":
            case "mantis":
                return new Mantis(p, pos.x, pos.y);
            case "Roaches":
            case "roach":
                return new Roach(p, pos.x, pos.y);
            case "Roots":
            case "root":
                return new Root(p, pos.x, pos.y);
            case "Mantoids":
            case "mantoid":
                return new Mantoid(p, pos.x, pos.y);
            case "Twisted":
            case "twisted":
                return new Twisted(p, pos.x, pos.y);
            default:
                return null;
        }
    }

    //pathfinding ------------------------------------------------------------------------------------------------------
    //todo: place first turnPoint on top of enemy

    protected boolean intersectTurnPoint() {
        TurnPoint point = trail.get(trail.size() - 1);
        PVector p = point.position;
        float tpSize;
        if (point.combat) tpSize = 3;
        else tpSize = 15;
        PVector pfPosition = new PVector(position.x - ((pfSize - 1) * 12.5f), position.y - ((pfSize - 1) * 12.5f));
        return (pfPosition.x > p.x - tpSize + (NODE_SIZE / 2f)
                    && pfPosition.x < p.x + tpSize + (NODE_SIZE / 2f))
                && (pfPosition.y > p.y - tpSize + (NODE_SIZE / 2f)
                    && pfPosition.y < p.y + tpSize + (NODE_SIZE / 2f));
    }

    /** wtf, why is this a thing */
    protected boolean intersectCombatPoint() {
        if (trail.size() == 0f) return false;
        TurnPoint point = trail.get(trail.size() - 1);
        PVector p = point.position;
        float tpSize;
        if (point.combat) tpSize = 15;
        else return false; //this is the only bit thats different
        PVector pfPosition = new PVector(position.x - ((pfSize - 1) * 12.5f), position.y - ((pfSize - 1) * 12.5f));
        return (pfPosition.x > p.x - tpSize + (NODE_SIZE / 2f)
                    && pfPosition.x < p.x + tpSize + (NODE_SIZE / 2f))
                && (pfPosition.y > p.y - tpSize + (NODE_SIZE / 2f)
                    && pfPosition.y < p.y + tpSize + (NODE_SIZE / 2f));
    }

    public void requestPath(int i) {
        pathFinder.requestQueue.add(new PathRequest(i, this));
        trail = new ArrayList<>();
    }

    public void swapPoints(boolean remove) {
        if (trail.size() != 0) {
            TurnPoint intersectingPoint = trail.get(trail.size() - 1);
            if (remove) {
                if (intersectingPoint.combat) {
                    state = State.Attacking;
                    attackCue = true;
                    targetTower = intersectingPoint.tower;
                    targetMachine = intersectingPoint.machine;
                } else attackCue = false;
                trail.remove(intersectingPoint);
            }
            if (trail.size() != 0) {
                PVector pointPosition = trail.get(trail.size() - 1).position;
                pointPosition = new PVector(pointPosition.x + 12.5f, pointPosition.y + 12.5f);
                pointPosition = new PVector(pointPosition.x + ((pfSize - 1) * 12.5f), pointPosition.y + ((pfSize - 1) * 12.5f));
                targetAngle = findAngleBetween(pointPosition, position);
            }
        }
    }

    /** Reset all combat points to turn points, then recalculate. */
    public void setCombatPoints() {
        //set not combat
        for (TurnPoint point : trail) {
            point.combat = false;
            point.towers = null;
            point.machine = false;
        }
        //set combat
        for (TurnPoint point : trail) {
            point.towers = clearanceTowers(point);
            point.machine = clearanceMachine(point);
        }
        //iterate backwards based on enemy size
        TurnPoint backPoint = backPoint();
        if (backPoint != null) {
            backPoint.combat = true;
            if (backPoint.towers != null && backPoint.towers.size() > 0) { //what the hell is this for??
                backPoint.tower = backPoint.towers.get(floor(backPoint.towers.size() / 2f));
            } else backPoint.tower = null;
        }
    }

    /** returns all towers in point kernel */
    protected ArrayList<Tower> clearanceTowers(TurnPoint point) {
        ArrayList<Tower> towersInKernel = new ArrayList<>();
        boolean clear = true;
        int kernelSize = 1;
        int x = (int) (point.position.x + 100) / 25;
        int y = (int) (point.position.y + 100) / 25;
        while (true) {
            for (int xn = 0; xn < kernelSize; xn++) {
                for (int yn = 0; yn < kernelSize; yn++) {
                    if (!(x + xn >= nodeGrid.length || y + yn >= nodeGrid[x].length)) {
                        Node node = nodeGrid[x + xn][y + yn];
                        if (node.tower != null) towersInKernel.add(node.tower);
                    } else {
                        clear = false;
                        break;
                    }
                }
                if (!clear) break;
            }
            if (clear && kernelSize < pfSize) kernelSize++;
            else break;
        }
        //deletes duplicates
        CopyOnWriteArrayList<Tower> cow = new CopyOnWriteArrayList<>(towersInKernel);
        for (int i = 0; i < cow.size() - 1; i++) {
            if (cow.get(i) == cow.get(i++)) cow.remove(i);
        }
        towersInKernel = new ArrayList<>(cow);
        return towersInKernel;
    }

    private boolean clearanceMachine(TurnPoint point) {
        boolean clear = true;
        int kSize = 1;
        int x = (int) (point.position.x + 100) / 25;
        int y = (int) (point.position.y + 100) / 25;
        while (true) {
            for (int xn = 0; xn < kSize; xn++) {
                for (int yn = 0; yn < kSize; yn++) {
                    if (!(x + xn >= nodeGrid.length || y + yn >= nodeGrid[x].length)) {
                        Node nodeB = nodeGrid[x + xn][y + yn];
                        Tile tileB;
                        if (nodeB != null) {
                            tileB = nodeB.getTile();
                            if (tileB != null && tileB.machine) return true;
                        }
                    } else {
                        clear = false;
                        break;
                    }
                }
                if (!clear) break;
            }
            if (clear && kSize < pfSize) kSize++;
            else break;
        }
        return false;
    }

    private TurnPoint backPoint() {
        TurnPoint bp = null;
        for (int i = trail.size() - 1; i >= 0; i--) {
            if (trail.get(i).towers != null && trail.get(i).towers.size() > 0 || trail.get(i).machine) {
                trail.get(i).combat = true;
                if (i < trail.size() - 1) bp = trail.get(i + 1);
                else bp = trail.get(i);
                bp.towers = trail.get(i).towers;
                bp.machine = trail.get(i).machine;
                bp.combat = true;
                bp.back = true;
                break;
            }
        }
        return bp;
    }

    public static class TurnPoint {

        private final PApplet P;
        public Tower tower;
        public ArrayList<Tower> towers;
        public boolean machine;
        public PVector position;
        boolean combat;
        boolean back;

        public TurnPoint(PApplet p, PVector position, Tower tower) {
            this.P = p;
            this.position = new PVector(position.x, position.y);
            this.tower = tower;

//            machine = tiles.get((int)(position.x/50)+2,(int)(position.y/50)+2).machine;
            combat = false;
            back = false;
        }

        public void display() {
            P.noStroke();
            if (back) P.stroke(0, 255, 0);
            else P.noStroke();
            if (combat) P.fill(255, 0, 0);
            else P.fill(255);
            P.ellipse(position.x + NODE_SIZE / 2f, position.y + NODE_SIZE / 2f, NODE_SIZE, NODE_SIZE);
            hover();
        }

        private void hover() {
            boolean intersecting;
            float tpSize = 10;
            PVector pfPosition = new PVector(P.mouseX, P.mouseY);
            intersecting = (pfPosition.x > position.x - tpSize + (NODE_SIZE / 2f) &&
                    pfPosition.x < position.x + tpSize + (NODE_SIZE / 2f)) &&
                    (pfPosition.y > position.y - tpSize + (NODE_SIZE / 2f) &&
                            pfPosition.y < position.y + tpSize + (NODE_SIZE / 2f));
            if (intersecting && tower != null) {
                P.stroke(255, 255, 0);
                P.noFill();
                P.rect(tower.tile.position.x - 50, tower.tile.position.y - 50, 50, 50);
            }
        }
    }
}
