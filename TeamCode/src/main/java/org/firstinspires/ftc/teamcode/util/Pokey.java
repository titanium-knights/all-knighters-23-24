package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.*;
@Config
public class Pokey {
    public Servo pokey;
    public static double UP_POS = .9;
    public static double DOWN_POS = .18;
    public static double PIXEL_STACK = .75;
    public static double TAP_BACKBOARD = .4;

    public Pokey(HardwareMap hmap) {
        this.pokey = hmap.servo.get(CONFIG.pokey);
    }

    public void resetPosition(boolean goUp)
    {
        if (goUp) {
            pokey.setPosition(UP_POS);
        } else {
            pokey.setPosition(DOWN_POS);
        }
    }

    public void increment(int multiplier) {
        /*
        If multiplier is negative, then the increment is down
         */
        double currentPos = pokey.getPosition();
        pokey.setPosition(currentPos+(multiplier*.01));
    }


}

