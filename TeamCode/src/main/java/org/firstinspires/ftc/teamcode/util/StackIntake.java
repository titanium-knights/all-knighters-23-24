package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.*;
@Config
public class StackIntake {
    public Servo stackIntakeL;
    public Servo stackIntakeR;

    public static double R_IN = 0.22;
    public static double R_OUT = 0.8;
    public static double R_RELOAD = 0.8;

    public static double L_IN = 0.8;
    public static double L_OUT = .1;

    public static double L_RELOAD = .32;



    public StackIntake(HardwareMap hmap) {
        this.stackIntakeL = hmap.servo.get(CONFIG.stackIntakeL);
        this.stackIntakeR = hmap.servo.get(CONFIG.stackIntakeR);
    }

//    public void flipFlopOut() {
//        stackIntakeL.setPosition(L_IN);
//        stackIntakeL.setPosition(R_IN);
//    }

    public void flipFlop() {

        stackIntakeL.setPosition(L_IN);
        stackIntakeR.setPosition(R_IN);

        stackIntakeL.setPosition(L_OUT);
        stackIntakeR.setPosition(R_OUT);

        stackIntakeL.setPosition(L_IN);
        stackIntakeR.setPosition(R_IN);
    }

    public void flipFlop(boolean goOut) {
        if (goOut) {
            stackIntakeL.setPosition(L_OUT);
            stackIntakeR.setPosition(R_OUT);
        } else {
            stackIntakeL.setPosition(L_IN);
            stackIntakeR.setPosition(R_IN);
        }
    }

}

