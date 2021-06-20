package main.levelStructure;

import main.misc.Polluter;
import processing.core.PApplet;

import java.awt.*;

public class GlacierWaves {

    public static Wave[] genGlacierWaves(PApplet p) {
        Wave[] waves = new Wave[50]; //60

        String snowAntlion = "Antlions";
        String wolf = "Wolves";
        String shark = "Snow Sharks";
        String velociraptor = "Velociraptors";
        String iceEntity = "Ice Entities";
        String iceMonstrosity = "Ice Monstrosities";
        String frost = "Frost";
        String mammoth = "Mammoths";
        String mudFlinger = "Mud Flingers";
        String mudCreature = "Mud Creatures";
        String bigWorm = "Megaworms";

        waves[0] = wavePreset(p, 65, 30, wolf);
        waves[0].addSpawns(wolf, 5);

        waves[1] = wavePreset(p, 55, 25, wolf);
        waves[1].addSpawns(wolf, 5);

        waves[2] = wavePreset(p, 65, 30, snowAntlion);
        waves[2].addSpawns(snowAntlion, 5);

        waves[3] = wavePreset(p, 55, 25, wolf);
        waves[3].addSpawns(wolf, 5);

        waves[4] = wavePreset(p, 65, 30, shark);
        waves[4].addSpawns(shark, 5);

        waves[5] = wavePreset(p, 55, 25, wolf);
        waves[5].addSpawns(wolf, 10);
        waves[5].polluter = new Polluter(p, 5, "glacier/partial");

        waves[6] = wavePreset(p, 65, 30, shark);
        waves[6].addSpawns(shark, 5);

        waves[7] = wavePreset(p, 30, 15, snowAntlion);
        waves[7].addSpawns(snowAntlion, 10);

        waves[8] = wavePreset(p, 55, 25, wolf);
        waves[8].addSpawns(wolf, 20);

        waves[9] = wavePreset(p, 150, 120, "Mini Horde");
        waves[9].addSpawns(wolf, 20);
        waves[9].addSpawns(shark, 30);
        waves[9].addSpawns(snowAntlion, 30);

        waves[10] = wavePreset(p, 65, 25, wolf);
        waves[10].addSpawns(wolf, 20);

        waves[11] = wavePreset(p, 85, 1, "Velociraptor");
        waves[11].addSpawns(velociraptor, 1);

        waves[12] = wavePreset(p, 55, 20, iceEntity);
        waves[12].addSpawns(iceEntity, 3);

        waves[13] = wavePreset(p, 85, 20, velociraptor);
        waves[13].addSpawns(velociraptor, 3);

        waves[14] = wavePreset(p, 85, 40, velociraptor);
        waves[14].addSpawns(velociraptor, 3);
        waves[14].addSpawns(shark, 5);
        waves[14].addSpawns(snowAntlion, 5);

        waves[15] = wavePreset(p, 40, 10, iceEntity);
        waves[15].addSpawns(iceEntity, 5);

        waves[16] = wavePreset(p, 85, 40, velociraptor);
        waves[16].addSpawns(velociraptor, 3);
        waves[16].addSpawns(wolf, 8);
        waves[16].addSpawns(shark, 5);
        waves[16].addSpawns(snowAntlion, 5);

        waves[17] = wavePreset(p, 65, 30, velociraptor);
        waves[17].addSpawns(velociraptor, 8);

        waves[18] = wavePreset(p, 65, 25, iceEntity);
        waves[18].addSpawns(iceEntity, 10);

        waves[19] = wavePreset(p, 65, 30, velociraptor);
        waves[19].addSpawns(velociraptor, 5);
        waves[19].addSpawns(wolf, 10);
        waves[19].addSpawns(shark, 8);
        waves[19].addSpawns(snowAntlion, 8);

        waves[20] = wavePreset(p, 65, 30, frost);
        waves[20].addSpawns(frost, 3);

        waves[21] = wavePreset(p, 55, 20, iceEntity);
        waves[21].addSpawns(iceEntity, 20);

        waves[22] = wavePreset(p, 65, 30, velociraptor);
        waves[22].addSpawns(velociraptor, 5);
        waves[22].addSpawns(wolf, 10);
        waves[22].addSpawns(shark, 8);
        waves[22].addSpawns(snowAntlion, 8);

        waves[23] = wavePreset(p, 65, 30, frost);
        waves[23].addSpawns(frost, 5);
        waves[23].addSpawns(iceEntity, 10);

        waves[24] = wavePreset(p, 50, 20, velociraptor);
        waves[24].addSpawns(velociraptor, 5);

        waves[25] = wavePreset(p, 65, 30, velociraptor);
        waves[25].addSpawns(velociraptor, 10);

        waves[26] = wavePreset(p, 50, 5, iceMonstrosity);
        waves[26].addSpawns(iceEntity, 5);
        waves[26].addSpawns(iceMonstrosity, 3);

        waves[27] = wavePreset(p, 50, 20, iceEntity);
        waves[27].addSpawns(iceEntity, 20);
        waves[27].addSpawns(snowAntlion, 20);

        waves[28] = wavePreset(p, 65, 30, frost);
        waves[28].addSpawns(frost, 5);
        waves[28].addSpawns(iceEntity, 5);

        waves[29] = wavePreset(p, 50, 20, iceMonstrosity);
        waves[29].addSpawns(iceMonstrosity, 5);

        waves[30] = new Wave(p, 40, 15,
          new Color(0, 155, 255),
          new Color(124, 204, 255),
          new Color(0, 52, 184),
          "???");
        waves[30].polluter = new Polluter(p, 0, "glacier/melted");

        waves[31] = wavePreset(p, 150, 120, "Horde");
        waves[31].addSpawns(velociraptor, 10);
        waves[31].addSpawns(iceMonstrosity, 5);
        waves[31].addSpawns(iceEntity, 20);
        waves[31].addSpawns(frost, 10);
        waves[31].polluter = new Polluter(p, 5, "glacier/final");
        waves[31].groundType = "dirt";

        waves[32] = wavePreset(p, 55, 25, velociraptor);
        waves[32].addSpawns(velociraptor, 20);
        waves[32].addSpawns(bigWorm, 5);

        waves[33] = wavePreset(p, 55, 25, mudCreature);
        waves[33].addSpawns(mudCreature, 5);

        waves[34] = wavePreset(p, 55, 25, mudFlinger);
        waves[34].addSpawns(mudFlinger, 5);

        waves[35] = wavePreset(p, 55, 25, iceMonstrosity);
        waves[35].addSpawns(frost, 10);
        waves[35].addSpawns(iceMonstrosity, 10);

        waves[36] = wavePreset(p, 55, 25, mudCreature);
        waves[36].addSpawns(mudCreature, 10);
        waves[36].addSpawns(frost, 5);

        waves[37] = wavePreset(p, 45, 15, iceMonstrosity);
        waves[37].addSpawns(frost, 10);
        waves[37].addSpawns(iceMonstrosity, 10);

        waves[36] = wavePreset(p, 55, 25, mudFlinger);
        waves[36].addSpawns(mudFlinger, 10);
        waves[36].addSpawns(frost, 5);

        waves[37] = wavePreset(p, 55, 25, iceMonstrosity);
        waves[37].addSpawns(frost, 15);
        waves[37].addSpawns(iceMonstrosity, 10);
        waves[37].addSpawns(iceEntity, 40);

        waves[38] = wavePreset(p, 150, 120, "Mud Horde");
        waves[38].addSpawns(mudCreature, 30);
        waves[38].addSpawns(mudFlinger, 30);
        waves[38].addSpawns("littleWorm", 30);
        waves[38].addSpawns(bigWorm, 5);

        waves[39] = wavePreset(p, 85, 1, "Mammoth");
        waves[39].addSpawns(mammoth, 1);

        waves[40] = wavePreset(p, 55, 25, mudFlinger);
        waves[40].addSpawns(mudFlinger, 15);
        waves[40].addSpawns(bigWorm, 5);

        waves[41] = wavePreset(p, 55, 25, mudCreature);
        waves[41].addSpawns(mudCreature, 15);
        waves[41].addSpawns(bigWorm, 5);

        waves[42] = wavePreset(p, 65, 1, "Mammoth");
        waves[42].addSpawns(mammoth, 1);

        //at this point there should no more mud flingers/creatures
        waves[43] = wavePreset(p, 55, 25, mudFlinger);
        waves[43].addSpawns(mudFlinger, 25);

        waves[44] = wavePreset(p, 55, 25, mudCreature);
        waves[44].addSpawns(mudCreature, 25);

        waves[45] = wavePreset(p, 65, 20, mammoth);
        waves[45].addSpawns(mammoth, 3);
        waves[45].addSpawns(bigWorm, 10);

        waves[46] = wavePreset(p, 55, 25, mudFlinger);
        waves[46].addSpawns(mudFlinger, 35);

        waves[47] = wavePreset(p, 55, 25, mudCreature);
        waves[47].addSpawns(mudCreature, 35);

        waves[48] = wavePreset(p, 65, 20, mammoth);
        waves[48].addSpawns(mammoth, 3);
        waves[48].addSpawns(bigWorm, 10);

        waves[49] = wavePreset(p, 150, 120, "Mud Horde");
        waves[49].addSpawns(bigWorm, 20);
        waves[49].addSpawns(mudCreature, 60);
        waves[49].addSpawns(mudFlinger, 60);
        waves[49].addSpawns(mammoth, 1);

        for (Wave wave : waves) wave.load();
        return waves;
    }

    private static Wave wavePreset(PApplet p, int length, int spawnLength, String title) {
        switch (title) {
            case "Antlions":
                return new Wave(p, length, spawnLength,
                  new Color(118, 190, 198),
                  new Color(118, 164, 198),
                  new Color(44, 80, 95),
                  title
                );
            case "Wolves":
                return new Wave(p, length, spawnLength,
                  new Color(171, 170, 165),
                  new Color(117, 113, 105),
                  new Color(0),
                  title
                );
            case "Snow Sharks":
                return new Wave(p, length, spawnLength,
                  new Color(30, 155, 227),
                  new Color(0, 255, 224),
                  new Color(0, 60, 100),
                  title
                );
            case "Velociraptor":
            case "Velociraptors":
                return new Wave(p, length, spawnLength,
                  new Color(116, 156, 160),
                  new Color(0, 157, 208),
                  new Color(255, 200, 0),
                  title
                );
            case "Mini Horde":
            case "Horde":
                return new Wave(p, length, spawnLength,
                  new Color(255, 255, 255),
                  new Color(179, 214, 238),
                  new Color(0, 113, 189),
                  title
                );
            case "Ice Entities":
                return new Wave(p, length, spawnLength,
                  new Color(123, 250, 250),
                  new Color(40, 139, 243),
                  new Color(12, 74, 232),
                  title
                );
            case "Ice Monstrosity":
            case "Ice Monstrosities":
                return new Wave(p, length, spawnLength,
                  new Color(12, 74, 232),
                  new Color(40, 139, 243),
                  new Color(123, 250, 250),
                  title
                );
            case "Frost":
                return new Wave(p, length, spawnLength,
                  new Color(227, 247, 255),
                  new Color(213, 243, 255),
                  new Color(141, 235, 255),
                  title
                );
            case "Mammoth":
            case "Mammoths":
                return new Wave(p, length, spawnLength,
                  new Color(64, 39, 15),
                  new Color(29, 20, 7),
                  new Color(185, 181, 158),
                  title
                );
            case "Mud Creatures":
                return new Wave(p, length, spawnLength,
                  new Color(74, 39, 0),
                  new Color(188, 186, 130),
                  new Color(23, 13, 0),
                  title
                );
            case "Mud Flingers":
                return new Wave(p, length, spawnLength,
                  new Color(74, 39, 0),
                  new Color(139, 75, 0),
                  new Color(23, 13, 0),
                  title
                );
            case "Mud Horde":
                return new Wave(p, length, spawnLength,
                  new Color(74, 39, 0),
                  new Color(0, 155, 255),
                  new Color(100, 100, 100),
                  title
                );
            case "Megaworms":
                return new Wave(p, length, spawnLength,
                  new Color(232, 106, 115),
                  new Color(255, 255, 255),
                  new Color(45, 19, 21),
                  title
                );
            default:
                return new Wave(p, length, spawnLength,
                  new Color(255, 0, 255),
                  new Color(0),
                  new Color(0),
                  title
                );
        }
    }
}
