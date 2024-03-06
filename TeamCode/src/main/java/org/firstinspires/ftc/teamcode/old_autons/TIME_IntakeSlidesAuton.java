package org.firstinspires.ftc.teamcode.old_autons;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.IntakeRoller;
import org.firstinspires.ftc.teamcode.util.MecanumDrive;
import org.firstinspires.ftc.teamcode.util.PixelCarriage;
import org.firstinspires.ftc.teamcode.util.Slides;

//@Config
@Deprecated

public class TIME_IntakeSlidesAuton extends LinearOpMode {

    protected MecanumDrive drive;
//    protected GreenShroomVision vision;
    protected Slides slides;
    protected PixelCarriage carriage;

    protected IntakeRoller intake;
    public static double POWER = .78;
    public static int PAUSE_TIME = 600;
    public static int FORWARD_TIME = 525;

    //intake
    public static double INTAKE_POW = .8;
    public static int INTAKE_TIME = 3000;

    //slides
    public static int SLIDE_POS_UP = -600;
    public static int SLIDE_POS_DOWN = -50;
    public static double SLIDE_POW = .4;

    public static int TURN_TIME_LEFT = 400;
    public static int TURN_TIME_RIGHT = 400;

    public static int BACKWARD_TIME_TO_BOARD = 675;

    public static int STRAFE_TIME_LEFT = 425;
    public static int STRAFE_TIME_RIGHT = 425;

    public static int BACKWARD_TIME_SLIGHT = 250;

    public static int WAIT_VISION = 425;

    public static int position = 1;



    Telemetry dashTelemetry = FtcDashboard.getInstance().getTelemetry();


    protected void setupDevices(){
        drive = new MecanumDrive(hardwareMap);
//        vision = new GreenShroomVision(hardwareMap, dashTelemetry);
        slides = new Slides(hardwareMap);
        carriage = new PixelCarriage(hardwareMap);
        intake = new IntakeRoller(hardwareMap);

    }
    @Override
    public void runOpMode() throws InterruptedException {
        setupDevices();
        sleep(WAIT_VISION);
//        position = vision.getPosition();
        telemetry.addData("Detected: ", position);
        dashTelemetry.addData("Detected", position);
        dashTelemetry.update();

        waitForStart();
        sleep(WAIT_VISION);
//        position = vision.getPosition();
        telemetry.addData("Detected: ", position);
        dashTelemetry.addData("Detected", position);

        telemetry.addData("Slides Position", slides.getPosition());
        dashTelemetry.addData("Slides Position", slides.getPosition());

        //start of timed auton
        drive.forwardWithPower(POWER); //goes forward to tape
        sleep(FORWARD_TIME);

        drive.stop();
        sleep(PAUSE_TIME);

        if (position != 2) {
            drive.turnRightWithPower(POWER);
            sleep(TURN_TIME_RIGHT);
            drive.stop();
            sleep(PAUSE_TIME);
        }

        if (position == 1) { //if left
            drive.backwardWithPower(POWER);
            sleep(BACKWARD_TIME_SLIGHT*2);
            drive.stop();
            sleep(PAUSE_TIME);
        }

        //spit out purple
        intake.intake(INTAKE_POW);
        sleep(INTAKE_TIME);
        intake.intake(0); //stop intake

        if (position == 2) { //if center needs to turn
            //face backboard
            drive.turnRightWithPower(POWER); //turns 90, facing backboard
            sleep(TURN_TIME_RIGHT);
        }

        if (position != 3) {
            //toward backboard
            drive.backwardWithPower(POWER);
            sleep(BACKWARD_TIME_TO_BOARD);
        } else { //is already backward
            drive.backwardWithPower(POWER);
            sleep(BACKWARD_TIME_TO_BOARD - BACKWARD_TIME_SLIGHT * 2);
        }

        //strafe according to position
        switch (position) {
            case 1: //goes to left
                drive.strafeRightWithPower(POWER);
                sleep(STRAFE_TIME_RIGHT);
                drive.stop();
                sleep(PAUSE_TIME);
                break;
            case 2:
                break;

            case 3:
                drive.strafeLeftWithPower(POWER);
                sleep(STRAFE_TIME_LEFT);
                drive.stop();
                sleep(PAUSE_TIME);
                break;

            default:
                break;
        }

        //dumping
        slides.setPosition(SLIDE_POS_UP, SLIDE_POW);
        drive.stop();
        sleep(PAUSE_TIME * 3);

        carriage.setPivotIntake(false); //faces outtake
        drive.stop();
        sleep(PAUSE_TIME * 3);

        carriage.setCarriageOpen(true); //opens the carriage
        drive.stop();
        sleep(PAUSE_TIME * 3);

        carriage.setPivotIntake(true); //faces outtake
        drive.stop();
        sleep(PAUSE_TIME * 2);

        slides.setPosition(SLIDE_POS_DOWN, SLIDE_POW);
        drive.stop();
        sleep(PAUSE_TIME);

        drive.backwardWithPower(BACKWARD_TIME_SLIGHT);
        drive.stop();
        sleep(PAUSE_TIME);
    }



}
