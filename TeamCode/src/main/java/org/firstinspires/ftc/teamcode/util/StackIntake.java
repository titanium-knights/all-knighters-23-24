package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.*;
@Config
public class StackIntake {
    public Servo stackIntakeL;
    public Servo stackIntakeR;

    public static double L_IN = 0.85;
    public static double L_OUT = 0.24;
    public static double R_IN = 0.2;
    public static double R_OUT = 0.2;

    public StackIntake(HardwareMap hmap) {
        this.stackIntakeL = hmap.servo.get(CONFIG.stackIntakeL);
        this.stackIntakeR = hmap.servo.get(CONFIG.stackIntakeR);
    }

//    public void flipFlopOut() {
//        stackIntakeL.setPosition(L_IN);
//        stackIntakeL.setPosition(R_IN);
//    }

    public void flipFlop() {
        /*
        Flips in, waits half a second, flips out
         */
//        flipFlopOut();

        stackIntakeL.setPosition(L_IN);
        stackIntakeL.setPosition(R_IN);

        stackIntakeL.setPosition(L_OUT);
        stackIntakeL.setPosition(R_OUT);

        stackIntakeL.setPosition(L_IN);
        stackIntakeL.setPosition(R_IN);
    }

    public void flipFlop(boolean goOut) {
        if (goOut) {
            stackIntakeL.setPosition(L_OUT);
            stackIntakeL.setPosition(R_OUT);
        } else {
            stackIntakeL.setPosition(L_IN);
            stackIntakeL.setPosition(R_IN);
        }
    }

}

