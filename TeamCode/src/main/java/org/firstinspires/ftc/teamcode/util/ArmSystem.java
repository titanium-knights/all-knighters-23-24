package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.*;
@Config
public class ArmSystem {
    public Servo arm;
    public Servo claw;
    public double ARM_DOWN = 0;
    public double ARM_INIT = 1;
    public double ARM_DUMP = .4;
    public double CLAW_OPEN = .9;
    public double CLAW_CLOSE = .3;


    public ArmSystem(HardwareMap hmap) {
        this.arm = hmap.servo.get(CONFIG.arm);
        this.claw = hmap.servo.get(CONFIG.claw);
    }

    public void setArmPos(boolean isDown, boolean isInit)
    {
        if(isInit){
            arm.setPosition(ARM_INIT);
        }
        else if (isDown) {
            arm.setPosition(ARM_DUMP);
        } else {
            arm.setPosition(ARM_DOWN);
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

