package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.*;
@Config
public class HighHang {
    public DcMotor highHangMotor;
    public double HANG_POWER = 1;
    public int HANGING_POS = 1900;
    public int LOWER_HANGING = 600;


    public HighHang(HardwareMap hmap) {
        this.highHangMotor = hmap.dcMotor.get(CONFIG.highHangMotor);
        setInit(); //restart encoders
    }

    public void setInit() {
        highHangMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //reset encoder to 0 at start
        highHangMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setPower(double power) { //set power, power is defaulted 0
        highHangMotor.setPower(power);
    }

    public void goToHang() { //getter
        highHangMotor.setTargetPosition(HANGING_POS);
        highHangMotor.setPower(HANG_POWER);
        highHangMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void goBackDown() { //getter
        highHangMotor.setTargetPosition(LOWER_HANGING);
        highHangMotor.setPower(LOWER_HANGING);
        highHangMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }






}