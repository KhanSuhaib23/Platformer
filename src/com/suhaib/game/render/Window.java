package com.suhaib.game.render;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.util.Objects;

public class Window {
    private final int width;
    private final int height;
    private final int scale;
    private final Canvas canvas;
    private final BufferedImage bufferedImage;
    private final int[] imagePixels;


    private Window(Builder builder) {
        this.width = builder.width;
        this.height = builder.height;
        this.scale = builder.scale;
        canvas = new Canvas();
        Dimension windowDimension = new Dimension(width * scale, height * scale);

        canvas.setPreferredSize(windowDimension);
        canvas.setMinimumSize(windowDimension);
        canvas.setMaximumSize(windowDimension);
        canvas.addKeyListener(Objects.requireNonNull(builder.keyListener, "KeyListener is required for creating a window"));

        JFrame frame = new JFrame();

        frame.setTitle(builder.title);
        frame.setResizable(builder.resizable);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(builder.visible);

        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        imagePixels = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
    }

    public void requestFocus() {
        canvas.requestFocus();
    }

    public void display(int[] pixels) {
        BufferStrategy buffer = canvas.getBufferStrategy();
        if (buffer == null) {
            canvas.createBufferStrategy(3);
            return;
        }
        Graphics g = buffer.getDrawGraphics();

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                imagePixels[x + (height - y - 1) * width] = pixels[x + y * width];
            }
        }
        g.drawImage(bufferedImage, 0, 0, width * scale, height * scale, null);
        g.dispose();
        buffer.show();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int width = 640;
        private int height = 480;
        private int scale = 1;
        private boolean resizable = false;
        private String title = "Window";
        private boolean visible = true;
        private KeyListener keyListener = null;

        public Builder() {

        }

        public Builder width(int val) {
            width = val;
            return this;
        }

        public Builder height(int val) {
            height = val;
            return this;
        }

        public Builder scale(int val) {
            scale = val;
            return this;
        }

        public Builder resizable(boolean val) {
            resizable = val;
            return this;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder visible(boolean val) {
            visible = val;
            return this;
        }

        public Builder keyListener(KeyListener val) {
            keyListener = val;
            return this;
        }

        public Window build() {
            return new Window(this);
        }
    }

}
