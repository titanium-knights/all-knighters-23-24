package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Slides {

    public DcMotor slideMotor1;
    public DcMotor slideMotor2;

    public Slides(HardwareMap hmap) { //get motor from port
        this.slideMotor1 = hmap.dcMotor.get(CONFIG.);
        this.slideMotor2 = hmap.dcMotor.get(CONFIG.);

    }

    public void move(double power) {
        slideMotor1.setPower(power);
        slideMotor2.setPower(power);
    }
}
