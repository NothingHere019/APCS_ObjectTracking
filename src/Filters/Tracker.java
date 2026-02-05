package Filters;


import Interfaces.Drawable;
import Interfaces.PixelFilter;
import core.DImage;
import core.Fruit;
import processing.core.PApplet;

import java.util.ArrayList;


public class Tracker implements PixelFilter, Drawable {
    private int centerRow;
    private int centerCol;

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        ArrayList<Integer> avgRowList = new ArrayList<>();
        ArrayList<Integer> avgColList = new ArrayList<>();
        int avgRow = 0;
        int avgCol = 0;
        int counter = 0;
        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[0].length; c++) {
                short RedDiffR = (short) (red[r][c] - 255);
                short RedDiffG = (short) (green[r][c] - 58);
                short RedDiffB = (short) (blue[r][c] - 29);
                short OrangeDiffR = (short) (red[r][c] - 253);
                short OrangeDiffG = (short) (green[r][c] - 163);
                short OrangeDiffB = blue[r][c];
                short YellowDiffR = (short) (red[r][c] - 232);
                short YellowDiffG = (short) (green[r][c] - 197);
                short YellowDiffB = blue[r][c];
                short BlueDiffR = (short) (red[r][c] - 10);
                short BlueDiffG = (short) (green[r][c] - 59);
                short BlueDiffB = (short) (blue[r][c] - 174);

                short redColorDist = (short) (Math.sqrt(RedDiffR * RedDiffR + RedDiffG * RedDiffG + RedDiffB * RedDiffB));
                short orangeColorDist = (short) (Math.sqrt(OrangeDiffR * OrangeDiffR + OrangeDiffG * OrangeDiffG + OrangeDiffB * OrangeDiffB));
                short yellowColorDist = (short) (Math.sqrt(YellowDiffR * YellowDiffR + YellowDiffG * YellowDiffG + YellowDiffB * YellowDiffB));
                short blueColorDist = (short) (Math.sqrt(BlueDiffR * BlueDiffR + BlueDiffG * BlueDiffG + BlueDiffB * BlueDiffB));

                if (redColorDist > 100 && orangeColorDist > 100 && yellowColorDist > 100 && blueColorDist > 100) {
                    red[r][c] = 0;
                    green[r][c] = 0;
                    blue[r][c] = 0;
                } else {
                    avgRow += r;
                    avgCol += c;
                    counter++;
                }

                if (counter == 0) counter++;

                avgRowList.add(avgRow / counter);
                avgColList.add(avgCol / counter);
            }
        }
        centerRow = (!avgRowList.isEmpty()) ? avgRowList.getLast() : 240;
        centerCol = (!avgRowList.isEmpty()) ? avgColList.getLast() : 320;
        if (centerRow > 2 && centerCol > 2 && centerRow < 476 && centerCol < 636) {
            for (int r = avgRowList.getLast() - 2; r < avgRowList.getLast() + 3; r++) {
                for (int c = avgColList.getLast() - 2; c < avgColList.getLast() + 3; c++){
                    red[r][c] = 255;
                    green[r][c] = 255;
                    blue[r][c] = 255;
                }
            }
        }
        img.setColorChannels(red, green, blue);
        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {
        ArrayList<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit(240, 320, 30, 30, 0));
        fruits.add(new Fruit(600, 400, 30, 30, 2));
        drawAndCheckFruits(fruits, window);
    }

    @Override
    public void setup(PApplet window) {

    }

    public boolean isCenterOnFruitAndFruitAlive(Fruit fruit) {
        return (centerCol >= fruit.getX() && centerCol <= fruit.getX() + fruit.getWidth() && centerRow >= fruit.getY() && centerRow <= fruit.getY() + fruit.getHeight() && fruit.isAlive());
    }

    public void drawAndCheckFruits(ArrayList<Fruit> fruits, PApplet window) {
        for (Fruit fruit: fruits) {
            if (isCenterOnFruitAndFruitAlive(fruit)) fruit.setAlive(false);
            fruit.drawFruit(window);
        }
    }
}
