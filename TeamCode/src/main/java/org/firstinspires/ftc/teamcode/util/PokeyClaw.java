package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.*;
@Config
public class PokeyClaw {
    public Servo pokeyClaw;
    public static double OPEN_POS = .85;
    public static double CLOSE_POS = .98;

    public PokeyClaw(HardwareMap hmap) {
        this.pokeyClaw = hmap.servo.get(CONFIG.pokeyClawServo);
    }

    public void increment(int multiplier) {
        /*
        If multiplier is negative, then the increment is down
         */
        double currentPos = pokeyClaw.getPosition();
        pokeyClaw.setPosition(currentPos+(multiplier*.01));
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


}

