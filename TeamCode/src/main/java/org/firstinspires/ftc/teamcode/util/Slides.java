package org.firstinspires.ftc.teamcode.util;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.*;

@Config
public class Slides {
    public DcMotor smr; //lift right
    public DcMotor sml; //lift left

    public static double LIFT_POWER = .6;
    public static double LIFT_POWER_MULTIPLYER = .9;

    public static int HIGH_POSITION = 950;
    public static int MID_POSITION = 800;
    public static int LOW_POSITION = 650;
    public static int GROUND_POSITION = 50;

    public static int MAX_LIMIT = 1000;
    public static int MIN_LIMIT = -300;

    public static int AVERAGE_BUFFER = 15;

    public static double DIFFERENCE = 0;

    public static double SENSITIVITY = 0.05;
    public static double TOLERANCE = 20;

    public static double Y = 0;

    public double LEFT_JOYSTICK_Y_Positive = 0;
    public double LEFT_JOYSTICK_Y_Negative = 0;

    public static double MULTIPLIER_DOWN = 0.4;
    public static double MULTIPLIER_UP = 0.6;
    public static double POWER_UP = 0.4;
    public static double POWER_DOWN = 0.3;


    public Slides(HardwareMap hmap) {
        this.smr = hmap.dcMotor.get(CONFIG.smr);
        this.sml = hmap.dcMotor.get(CONFIG.sml);
    }

    public void setPower(double power) {
        smr.setPower(-power * LIFT_POWER_MULTIPLYER);
        sml.setPower(-power * LIFT_POWER_MULTIPLYER);
    }

    public void setPosition(int pos, double power) {
        smr.setTargetPosition(pos);
        smr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        smr.setPower(power);

        sml.setTargetPosition(pos);
        sml.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        sml.setPower(power);
    }

    //public void runToPosition()

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
        return (getPositionL() + getPositionR())/2;
    }

    public void correctMotorPositions(double pressedVal) {
        //if the difference between the two motors is larger than the difference

        if(-pressedVal > 0) {
            if(getAverage() < MAX_LIMIT){
                LEFT_JOYSTICK_Y_Positive = -pressedVal;
            }
            else{
                LEFT_JOYSTICK_Y_Positive = 0;
            }
        }


        else if(-pressedVal < 0){
            if(getAverage() > MIN_LIMIT){
                LEFT_JOYSTICK_Y_Negative = -pressedVal;
            }
            else{
                LEFT_JOYSTICK_Y_Negative = 0;
            }
        }

        else{
            LEFT_JOYSTICK_Y_Positive = 0;
            LEFT_JOYSTICK_Y_Negative = 0;
        }


        if(Math.abs(-pressedVal)>0.1){
            if(-pressedVal > 0){
                smr.setPower(LEFT_JOYSTICK_Y_Positive);
                sml.setPower(LEFT_JOYSTICK_Y_Positive);
            }
            else if(-pressedVal < 0){
                smr.setPower(LEFT_JOYSTICK_Y_Negative * 0.5);
                sml.setPower(LEFT_JOYSTICK_Y_Negative * 0.5);
            }
        }

        else {
            setPower(0);

//            if (Math.abs(getPositionR() - Math.abs(getPositionL())) < AVERAGE_BUFFER) {
//                lmr.setPower(0);
//            }
//            else if (Math.abs(getPositionR() - Math.abs(getPositionL())) > AVERAGE_BUFFER) {
//                if (getPositionR() > getPositionL()) {
//                    lmr.setPower(-POWER_DOWN); //multiply by .8 since gravity helps
//                }
//                if (getPositionR() < getPositionL())
//                    lmr.setPower(POWER_UP);
//                }
        }
    }



    public void correctMotorVed(double gamepadVal) {

        DIFFERENCE = (getPositionR() - getPositionL());

        if(Math.abs(gamepadVal)>0.1)
        {
            smr.setPower((SENSITIVITY * -DIFFERENCE) + (0.9 * Y));
            sml.setPower((SENSITIVITY * DIFFERENCE) + (0.9 * Y));
        }

        else
        {
            if (DIFFERENCE > TOLERANCE)
            {
                smr.setPower((SENSITIVITY * -DIFFERENCE));
                sml.setPower((SENSITIVITY * DIFFERENCE));
            }
            else
            {
                smr.setPower(0);
                sml.setPower(0);
            }
        }

    }


    public double getDIFFERENCE() {
        return DIFFERENCE;
    }

    public void runToPosition(int pos, double multiplier){
        int currentPos =             sml.getCurrentPosition();
        //double multiplier = Math.min(1, Math.max(0, Math.abs(pos - currentPos) / 150.0));
        if(pos - currentPos > 30){
            setPower(-1 * multiplier);
        }
        else if(pos - currentPos < -30){
            setPower(1 * multiplier);
        }
        else if (pos == 0) {
            setPower(0);
        } else {
            setPower(0);
        }
    }

}