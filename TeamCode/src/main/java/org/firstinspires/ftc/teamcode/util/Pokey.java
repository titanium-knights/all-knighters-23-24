package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.*;
@Config
public class Pokey {
    public Servo pokey;
    public static double UP_POS = 0.2;
    public static double DOWN_POS = 0.85;
    public static double HALF_POS = 0.75;

    public Pokey(HardwareMap hmap) {
        this.pokey = hmap.servo.get(CONFIG.pokeyServo);
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

    public void goToHalfPosition(){
        pokey.setPosition(HALF_POS);
    }


}

