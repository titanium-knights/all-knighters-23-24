package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.*;
@Config
public class ArmSystem {
    public Servo arm;
    public Servo claw;
    public double ARM_DOWN = .1;
    public double ARM_INIT = .5;
    public double CLAW_OPEN = .9;
    public double CLAW_CLOSE = .3;


    public ArmSystem(HardwareMap hmap) {
        this.arm = hmap.servo.get(CONFIG.arm);
        this.claw = hmap.servo.get(CONFIG.claw);
    }

    public void setArmPos(boolean isDown)
    {
        if (isDown) {
            arm.setPosition(ARM_DOWN);
        } else {
            arm.setPosition(ARM_INIT);
        }
    }

    public void setClawOpen(boolean isOpen)
    {
        if (isOpen) {
            claw.setPosition(CLAW_OPEN);
        } else {
            claw.setPosition(CLAW_CLOSE);
        }
    }


}

