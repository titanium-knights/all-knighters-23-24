package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class PixelCarriage {
    public Servo carriagePivotL; //servo
//    public DistanceSensor distance; //distance sensor
//    public Servo carriagePivotR; //servo

    public Servo carriageOpen; //servo

    public static double INTAKE_POS_R = .1;
    public static double OUTTAKE_POS_R = 0.7;

    public static double INTAKE_POS_L = .05;
    public static double OUTTAKE_POS_L = .72;
    public static double HOLD_POS = .05;
    public static double OPEN_POS = 0.6;

    public PixelCarriage(HardwareMap hmap) {
        this.carriagePivotL = hmap.servo.get(CONFIG.carriagePivotL);
//        this.carriagePivotR = hmap.servo.get(CONFIG.carriagePivotR);
        this.carriageOpen = hmap.servo.get(CONFIG.carriageFlap);
//        this.distance = hmap.get(DistanceSensor.class, CONFIG.distanceSensor);
    }

    public void setPosition(double pos) {
        carriageOpen.setPosition(pos);
    }

    public void setPivotIntake(boolean isIntake)
    {
        if (isIntake) {
//            carriagePivotR.setPosition(INTAKE_POS_R);
            carriagePivotL.setPosition(INTAKE_POS_L);

        } else {
//            carriagePivotR.setPosition(OUTTAKE_POS_R);
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

//    public boolean isPixelInCarriage() {
//        if (distance.getDistance(DistanceUnit.CM) < 1) {
//            return true;
//        } return false;
//    }

}