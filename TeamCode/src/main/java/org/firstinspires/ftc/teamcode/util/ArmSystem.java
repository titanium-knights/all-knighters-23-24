package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.*;
@Config
public class ArmSystem {
    enum ARM_HEIGHT {
        LOW,
        MID,
        HIGH;
    }
    public Servo arm;
    public Servo claw;
    public double ARM_DOWN = 0;
    public double ARM_INIT = .88;
    public double ARM_PICKUP_STACK = .07;
    public double ARM_DUMP_MID = .37;
    public double ARM_DUMP_LOW = .25;

    public double CLAW_OPEN = .9;
    public double CLAW_CLOSE = .65;


    public ArmSystem(HardwareMap hmap) {
        this.arm = hmap.servo.get(CONFIG.arm);
        this.claw = hmap.servo.get(CONFIG.claw);
    }

    public void setArmPos(boolean isDown, boolean isInit, int am)
    {
        if(isInit){
            arm.setPosition(ARM_INIT);
        }
        else if (isDown) {
            switch (am) {
                case 0:
                    arm.setPosition(ARM_PICKUP_STACK);
                    break;
                case 1:
                    arm.setPosition(ARM_DUMP_LOW);
                    break;
                case 2:
                    arm.setPosition(ARM_DUMP_MID);
                    break;

                    default:
                        arm.setPosition(ARM_DUMP_MID);

            }
        } else {
            arm.setPosition(ARM_DOWN);
        }
    }

    public void setArmPos(boolean isDown, boolean isInit)
    {
        if(isInit){
            arm.setPosition(ARM_INIT);
        }
        else if (isDown) {
            arm.setPosition(ARM_DUMP_MID);
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

