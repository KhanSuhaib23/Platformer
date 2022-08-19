package com.suhaib.packer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class Packer {

    public static void main(String[] args) throws IOException {
        BufferedImage image = ImageIO.read(Packer.class.getResource("/res/raw/level.png"));

        int width = image.getWidth();
        int height = image.getHeight();

        int[] pixels = new int[width * height];

        image.getRGB(0, 0, width, height, pixels, 0, width);

        Map<Integer, Integer> tileMap = Map.of(
                0xff7f3300, 0,
                0xff0094ff, 1
        );

        File file = new File("src/res/data/level_1_1.lvl");


        if (!file.exists()) file.createNewFile();

        PrintWriter writer = new PrintWriter(file);

        writer.println(image.getWidth() + " " + image.getHeight());

        for (int p : pixels) {
            writer.print(tileMap.getOrDefault(p, 1) + " ");
        }

        writer.close();
    }
}
