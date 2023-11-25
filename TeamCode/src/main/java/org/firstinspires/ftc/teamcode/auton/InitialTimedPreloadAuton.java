package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.teamcode.util.MecanumDrive;
import org.firstinspires.ftc.teamcode.util.GreenShroomVision;
import org.firstinspires.ftc.teamcode.util.PixelCarriage;
import org.firstinspires.ftc.teamcode.util.Slides;

@Autonomous(name="InitialTimedPreloadAuton", group="Linear Opmode")
@Config
public class InitialTimedPreloadAuton extends LinearOpMode {

    protected MecanumDrive drive;
    protected GreenShroomVision vision;

    protected Slides slides;

    protected PixelCarriage carriage;

    public static double POWER = .8;
    public static int PAUSE_TIME = 200;

    public static int STRAFE_TIME = 550;

    public static int BACKWARD_13_TIME = 650;
    public static int BACKWARD_2_TIME = 750;
    public static int FORWARD_TIME = 300;
    public static int FORWARD2_TIME = 1050;

    public static int SIDE_TIME = 400;
    public static int position;

    protected void setupDevices(){
        drive = new MecanumDrive(hardwareMap);
        vision = new GreenShroomVision(hardwareMap, null);
        slides = new Slides(hardwareMap);
        carriage = new PixelCarriage(hardwareMap);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        setupDevices();
        vision.getPosition();
        waitForStart();

        telemetry.addData("Detected: ", position);

        switch (position){
            case 1:
                drive.strafeRightWithPower(POWER);
                sleep(STRAFE_TIME);
                drive.stop();
                sleep(PAUSE_TIME);
                drive.backwardWithPower(POWER);
                sleep(BACKWARD_13_TIME);
                drive.stop();
                sleep(PAUSE_TIME);

                break;
            case 2:
                drive.backwardWithPower(POWER);
                sleep(BACKWARD_2_TIME);

                drive.stop();
                sleep(PAUSE_TIME);

                drive.forwardWithPower(POWER);
                sleep(FORWARD_TIME);

                drive.stop();
                sleep(PAUSE_TIME);
                break;
            case 3:
                drive.strafeLeftWithPower(POWER);
                sleep(STRAFE_TIME);
                drive.stop();
                sleep(PAUSE_TIME);
                drive.forwardWithPower(POWER);
                sleep(BACKWARD_13_TIME);
                drive.stop();
                sleep(PAUSE_TIME);
                break;

        }


    }



}
