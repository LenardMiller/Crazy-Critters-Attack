package main.levelStructure;

import main.misc.Polluter;
import processing.core.PApplet;

import java.awt.*;

public class GlacierWaves {

    public static Wave[] genGlacierWaves(PApplet p) {
        Wave[] waves = new Wave[20]; //60

        String snowAntlion = "Antlions";
        String wolf = "Wolves";
        String shark = "Snow Sharks";
        String velociraptor = "Velociraptors";

        waves[0] = wavePreset(p, 65, 30, wolf);
        waves[0].addSpawns(wolf, 5);
        waves[0].polluter = new Polluter(p, 5, "levels/glacier/partial");

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

        waves[6] = wavePreset(p, 65, 30, shark);
        waves[6].addSpawns(shark, 5);

        waves[7] = wavePreset(p, 30, 15, snowAntlion);
        waves[7].addSpawns(snowAntlion, 10);

        waves[8] = wavePreset(p, 55, 25, wolf);
        waves[8].addSpawns(wolf, 20);

        waves[9] = wavePreset(p, 200, 170, "Mini Horde");
        waves[9].addSpawns(wolf, 20);
        waves[9].addSpawns(shark, 30);
        waves[9].addSpawns(snowAntlion, 30);

        waves[10] = wavePreset(p, 65, 25, wolf);
        waves[10].addSpawns(wolf, 20);

        waves[11] = wavePreset(p, 85, 1, "Velociraptor");
        waves[11].addSpawns(velociraptor, 1);

        waves[12] = wavePreset(p, 25, 1, snowAntlion);
        waves[12].addSpawns(snowAntlion, 30);

        waves[13] = wavePreset(p, 85, 20, velociraptor);
        waves[13].addSpawns(velociraptor, 3);

        waves[14] = wavePreset(p, 85, 40, velociraptor);
        waves[14].addSpawns(velociraptor, 5);
        waves[14].addSpawns(shark, 5);
        waves[14].addSpawns(snowAntlion, 5);

        waves[15] = wavePreset(p, 65, 30, wolf);
        waves[15].addSpawns(wolf, 40);

        waves[16] = wavePreset(p, 85, 40, velociraptor);
        waves[16].addSpawns(velociraptor, 5);
        waves[16].addSpawns(shark, 5);
        waves[16].addSpawns(snowAntlion, 5);

        waves[17] = wavePreset(p, 65, 30, velociraptor);
        waves[17].addSpawns(velociraptor, 5);

        waves[18] = wavePreset(p, 55, 25, wolf);
        waves[18].addSpawns(wolf, 20);

        waves[19] = wavePreset(p, 65, 30, velociraptor);
        waves[19].addSpawns(velociraptor, 5);
        waves[19].addSpawns(shark, 5);
        waves[19].addSpawns(snowAntlion, 5);

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
                  title);
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
