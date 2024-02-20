package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import com.qualcomm.robotcore.hardware.DistanceSensor;

public class DistanceTester {

    public DistanceSensor distance1;
    public DistanceSensor distance2;

    public DistanceTester(HardwareMap hmap) {
        this.distance1 = hmap.get(DistanceSensor.class, "distance1");
        this.distance2 = hmap.get(DistanceSensor.class, "distance2");
    }

    public double returnDistance1(){
        return distance1.getDistance(DistanceUnit.CM);
    }

    public double returnDistance2(){
        return distance2.getDistance(DistanceUnit.CM);
    }
}
