package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.teamcode.util.MecanumDrive;
import org.firstinspires.ftc.teamcode.util.GreenShroomVision;

@Autonomous(name="InitialTimedPreloadAuton", group="Linear Opmode")
@Config
public class InitialTimedPreloadAuton extends LinearOpMode {

    protected MecanumDrive drive;
    protected GreenShroomVision vision;

    public static int FORWARD13_TIME = 1000;
    public static int FORWARD2_TIME = 700;
    public static int STRAFE_TIME = 400;
    public static int position;

    protected void setupDevices(){
        drive = new MecanumDrive(hardwareMap);
        vision = new GreenShroomVision(hardwareMap, null);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        setupDevices();
        vision.getPosition();
        waitForStart();

        telemetry.addData("Detected: ", position);

        switch (position){
            case 1:
                drive.strafeLeftWithPower(0.8);
                sleep(STRAFE_TIME);
                drive.forwardWithPower(0.8);
                sleep(FORWARD13_TIME);
                break;
            case 2:
                drive.strafeRightWithPower(0.8);
                sleep(STRAFE_TIME);
                drive.forwardWithPower(0.8);
                sleep(FORWARD2_TIME);
                break;
            case 3:
                drive.forwardWithPower(0.8);
                sleep(FORWARD2_TIME);
                break;

        }


    }



}
