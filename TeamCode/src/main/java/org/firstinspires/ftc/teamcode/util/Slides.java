package org.firstinspires.ftc.teamcode.util;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.*;

@Config
public class Slides {
    public DcMotor smr; //lift right
    public DcMotor sml; //lift left
    public static double LIFT_POWER = .6;
    public static double LIFT_POWER_MULTIPLIER = .9;

    public static int MAX_LIMIT = 1500;
    public static int MAX_LIMIT_BUFFER = 150;
    public static int MIN_LIMIT = 50;
    public static int MIN_LIMIT_BUFFER = 25;

    public static double IDLE_SLIDE_POWER = .1;

    public Slides(HardwareMap hmap) {
        this.smr = hmap.dcMotor.get(CONFIG.smr);
        this.sml = hmap.dcMotor.get(CONFIG.sml);
        setInit();
    }

    public void setPower(double power) {
        if(getAverage()<MAX_LIMIT){ //if under max limit, operate slides normally
            //these powers are opposite due to how they are set up mechanically
            smr.setPower(power);
            sml.setPower(-power);
        }
    }

    public void withinBounds() {
        if(getAverage()>MAX_LIMIT){ //if above the max limit, force the slides to be the buffer below the max using while
            while(getAverage()>(MAX_LIMIT-MAX_LIMIT_BUFFER)) {
                //opposite power
                smr.setPower(-IDLE_SLIDE_POWER);
                sml.setPower(IDLE_SLIDE_POWER);
            }
        } else if(getAverage()<MIN_LIMIT){
            while(getAverage()<(MIN_LIMIT+MIN_LIMIT_BUFFER)) {
                //opposite power
                smr.setPower(IDLE_SLIDE_POWER);
                sml.setPower(-IDLE_SLIDE_POWER);
            }
        }
    }
//    public void setPosition(int pos, double power) {
//        smr.setTargetPosition(pos);
//        smr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        smr.setPower(power);
//
//        sml.setTargetPosition(pos);
//        sml.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        sml.setPower(power);
//    }

    public void setInit() {
        smr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sml.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        smr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sml.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public int getPositionR() {
        return smr.getCurrentPosition();
    }

    public int getPositionL() {
        return sml.getCurrentPosition();
    }

    public int getAverage() {
        return (Math.abs(getPositionL()) + Math.abs(getPositionR()))/2;
    }



}