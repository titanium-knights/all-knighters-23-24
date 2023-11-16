package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class PixelClaw {
    public Servo carriagePivot; //servo
    public Servo carriageOpen; //servo

    public static double INTAKE_POS = 0;
    public static double OUTTAKE_POS = 0.1;
    public static double HOLD_POS = 0;
    public static double OPEN_POS = 0.1;

    public PixelClaw(HardwareMap hmap) {
        this.carriagePivot = hmap.servo.get(CONFIG.carriagePivot);
        this.carriageOpen = hmap.servo.get(CONFIG.carriageOpen);

    }

    public void setPosition(double pos) {
        carriageOpen.setPosition(pos);
    }

    public void setPivotIntake(boolean isIntake)
    {
        if (isIntake) {
            carriagePivot.setPosition(INTAKE_POS);
        } else {
            carriagePivot.setPosition(OUTTAKE_POS);
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

}