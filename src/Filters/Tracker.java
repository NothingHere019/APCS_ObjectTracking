package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class Tracker implements PixelFilter {

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        //green ball = 0, 155, 0
        //yellow ball = 232, 197, 0;
        //orange ball = 253, 163, 0;
        //blue = 10, 59, 174;
        //red = 255, 58, 29;

        //short red_ball_r = 255;
        //short red_ball_g = 58;
        //short red_ball_b = 29;

        for (int r = 0; r < red.length; r++){
            for (int c = 0; c < red[0].length; c++){
                short RedDiffR = (short)(red[r][c] - 255);
                short RedDiffG = (short)(green[r][c] - 58);
                short RedDiffB = (short)(blue[r][c] - 29);
                short OrangeDiffR = (short)(red[r][c] - 253);
                short OrangeDiffG = (short)(green[r][c] - 163);
                short OrangeDiffB = (short)(blue[r][c] - 0);
                short YellowDiffR = (short)(red[r][c] - 232);
                short YellowDiffG = (short)(green[r][c] - 197);
                short YellowDiffB = (short)(blue[r][c] - 0);
                short BlueDiffR = (short)(red[r][c] - 10);
                short BlueDiffG = (short)(green[r][c] - 59);
                short BlueDiffB = (short)(blue[r][c] - 174);

                short redColorDist = (short)(Math.sqrt(RedDiffR * RedDiffR + RedDiffG * RedDiffG + RedDiffB * RedDiffB));
                short orangeColorDist = (short)(Math.sqrt(OrangeDiffR * OrangeDiffR + OrangeDiffG * OrangeDiffG + OrangeDiffB * OrangeDiffB));
                short yellowColorDist = (short)(Math.sqrt(YellowDiffR * YellowDiffR + YellowDiffG * YellowDiffG + YellowDiffB * YellowDiffB));
                short blueColorDist = (short)(Math.sqrt(BlueDiffR * BlueDiffR + BlueDiffG * BlueDiffG + BlueDiffB * BlueDiffB));

                if (redColorDist > 100 && orangeColorDist > 100 && yellowColorDist > 100 && blueColorDist > 100){
                    red[r][c] = 0;
                    green[r][c] = 0;
                    blue[r][c] = 0;
                } //else{
                    //red[r][c] = 255;
                    //green[r][c] = 255;
                    //blue[r][c] = 255;
                //}
            }
        }

        img.setColorChannels(red, green, blue);
        return img;
    }
}
