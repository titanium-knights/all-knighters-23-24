package org.firstinspires.ftc.teamcode.util;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.*;

@Config
public class Slides {
    public DcMotor slidesMotor; //lift right

    public static int MAX_LIMIT = -1400; //actual encoder
    public static int MAX_LIMIT_BUFFER = 150;
    public static int MIN_LIMIT = 20;
    public static int MIN_LIMIT_BUFFER = 25;

    public static double IDLE_SLIDE_POWER = .1;

    public Slides(HardwareMap hmap) {
        this.slidesMotor = hmap.dcMotor.get(CONFIG.slidesMotor);
        setInit();
    }

    public void setPower(double power) {
            slidesMotor.setPower(power);
    }

    /*
    Checks if slides are within bounds by getting the average
    If the slides are not in bounds, stop all movement and force into
    a buffer.
     */
    public void withinBounds() {
        if(getPosition()<MAX_LIMIT){ //if above the max limit, force the slides to be the buffer below the max using while
            while(getPosition()<(MAX_LIMIT+MAX_LIMIT_BUFFER)) {
                //opposite power
                slidesMotor.setPower(IDLE_SLIDE_POWER);
            }
        } else if(getPosition()>MIN_LIMIT){
            while(getPosition()>(MIN_LIMIT-MIN_LIMIT_BUFFER)) {
                //opposite power
                slidesMotor.setPower(-IDLE_SLIDE_POWER);
            }
        }
    }

    /*
    Runs to a position by setting target positions and changing the runmode, holds power
     */
    public void setPosition(int pos, double power) {
        if (getPosition() == pos) {
            stop();
        } else {
            slidesMotor.setTargetPosition(pos);
            slidesMotor.setPower(power);
            slidesMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

    }

    public void stop() {
        slidesMotor.setPower(0);
        slidesMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setInit() {
        slidesMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slidesMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    /*
    Returns the positive of the position
     */
    public int getPosition() {

        return slidesMotor.getCurrentPosition();
    }




}