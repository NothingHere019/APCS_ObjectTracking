package core;

import Filters.Tracker;
import processing.core.PApplet;

public class Fruit {
    private int x;
    private float y;
    private int width;
    private int height;
    private int color;
    private boolean isAlive;
    private float counter = 0F;

    public Fruit(int x, int y, int width, int height, int color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.isAlive = true;
    }

    public int getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void drawFruit(PApplet window) {
        if (isAlive) {
            if (color == 0) window.fill(255, 0, 0);
            else if (color == 1) window.fill(0, 255, 0);
            else if (color == 2) window.fill(0, 0, 255);
            else window.fill(255, 255, 255);
        } else {
            window.fill(0, 0, 0, 0);
        }
        window.rect(x, y, width, height);
        y += counter;
        counter += 0.4F;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
