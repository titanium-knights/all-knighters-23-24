package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.*;
@Deprecated
public class Arm {
    public DcMotor arm;
    public int BACK_LIMIT = 100;
    public int FRONT_LIMIT = 200;
    public double ARM_POWER = .9;

    public Arm(HardwareMap hmap) {
        this.arm = hmap.dcMotor.get(CONFIG.arm);
        setInit(); //restart encoders
    }

    public void setInit() {
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //reset encoder to 0 at start
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setPower(double power) { //set power, power is defaulted 0
        arm.setPower(power);
    }

    public int getPosition() { //getter
        return arm.getCurrentPosition();
    }

    public void setPosition(int pos) {
        if (arm.getCurrentPosition() < FRONT_LIMIT) { //should go forward
            while (getPosition() > pos) {
                setPower(ARM_POWER * -1);
            }
            setPower(0);
        } //go backward
        else {
            while (getPosition() < pos) {
                setPower(ARM_POWER* 1);
            }
            setPower(0);

        }
    }



}