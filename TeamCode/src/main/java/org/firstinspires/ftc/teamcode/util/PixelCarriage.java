package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class PixelCarriage {
    public Servo carriagePivotL; //servo
    public Servo carriagePivotR; //servo

    public Servo carriageOpen; //servo

    public static double INTAKE_POS_R = 0;
    public static double OUTTAKE_POS_R = 0.25;

    public static double INTAKE_POS_L = 1;
    public static double OUTTAKE_POS_L = .75;
    public static double HOLD_POS = .5;
    public static double OPEN_POS = 0.8;

    public PixelCarriage(HardwareMap hmap) {
        this.carriagePivotL = hmap.servo.get(CONFIG.carriagePivotL);
        this.carriagePivotR = hmap.servo.get(CONFIG.carriagePivotR);
        this.carriageOpen = hmap.servo.get(CONFIG.carriageFlap);

    }

    public void setPosition(double pos) {
        carriageOpen.setPosition(pos);
    }

    public void setPivotIntake(boolean isIntake)
    {
        if (isIntake) {
            carriagePivotR.setPosition(INTAKE_POS_R);
            carriagePivotL.setPosition(INTAKE_POS_L);

        } else {
            carriagePivotR.setPosition(OUTTAKE_POS_R);
            carriagePivotL.setPosition(OUTTAKE_POS_L);
        }
    }

    public void setCarriageOpen(boolean isOpen)
    {
        if (isOpen) {
            carriageOpen.setPosition(OPEN_POS);
        } else {
            carriageOpen.setPosition(HOLD_POS);
        }
    }

    public void setOuttake(boolean isIntake, boolean isOpen) {
        setPivotIntake(isIntake);
        setCarriageOpen(isOpen);
    }

}