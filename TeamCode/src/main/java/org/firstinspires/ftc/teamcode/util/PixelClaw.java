package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class PixelClaw {
//    public Servo pixelClawPivot; //servo
    public Servo pixelClaw; //servo

//    public static double PICKUP_POS = 0;
//    public static double PLACE_POS = 0.1;
//    public static double LOOSE_POS = 0;
//    public static double GRAB_POS = 0.1;

    public PixelClaw(HardwareMap hmap) {
//        this.pixelClawPivot = hmap.servo.get(CONFIG.pixelClawPivot);
        this.pixelClaw = hmap.servo.get(CONFIG.pixelClaw);

    }

    public void setPosition(double pos) {
        pixelClaw.setPosition(pos);
    }

//    public void setPivotPos(String posType)
//    {
////        if (posType == "PICKUP") {
////            setPosition(pixelClawPivot, PICKUP_POS);
////        } else if (posType == "PLACE") {
////            setPosition(pixelClawPivot, PLACE_POS);
////        }
////    }

//    public void setClawPos(double pos)
//    {
//        setPosition(pos);
//
//    }
}