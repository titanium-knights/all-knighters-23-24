package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.*;
@Config
public class PokeyClaw {
    public Servo pokeyClaw;
    public static double OPEN_POS = .2;
    public static double CLOSE_POS = .5;

    public Servo pokey;
    public static double UP_POS = 0.9;
    public static double DOWN_POS = 0.2;
    public static double HALF_POS = 0.5;

    public PokeyClaw(HardwareMap hmap) {
        this.pokeyClaw = hmap.servo.get(CONFIG.pokeyClawServo);
        this.pokey = hmap.servo.get(CONFIG.pokeyServo);
    }

    public void increment(int multiplier) {
        /*
        If multiplier is negative, then the increment is down
         */
        double currentPos = pokey.getPosition();
        pokey.setPosition(currentPos+(multiplier*.01));
    }

    public void openClaw(boolean goOpen) {
        /*
        If multiplier is negative, then the increment is down
         */
        if (goOpen) {
            pokeyClaw.setPosition(OPEN_POS);
        } else {
            pokeyClaw.setPosition(CLOSE_POS);
        }
    }

    public void goToHalfPosition(){
        pokey.setPosition(HALF_POS);
    }

    public void resetPosition(boolean goUp)
    {
        if (goUp) {
            pokey.setPosition(UP_POS);
        } else {
            pokey.setPosition(DOWN_POS);
        }
    }




}

