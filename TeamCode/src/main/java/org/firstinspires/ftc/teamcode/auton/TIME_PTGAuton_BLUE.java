package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.teamcode.util.MecanumDrive;

@Autonomous(name="PTGAuton_BLUE", group="Linear Opmode")
@Config
public class TIME_PTGAuton_BLUE extends LinearOpMode {

    protected MecanumDrive drive;
    public static double POWER = .8;
    public static int PAUSE_TIME = 200;
    public static int BACKWARD_TIME = 650;
    public static int FORWARD_TIME = 300;
    public static int FORWARD2_TIME = 775;

    public static int SIDE_TIME = 400;

    protected void setupDevices(){
        drive = new MecanumDrive(hardwareMap);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        setupDevices();
        waitForStart();

        drive.backwardWithPower(POWER);
        sleep(BACKWARD_TIME);

        drive.stop();
        sleep(PAUSE_TIME);

        drive.forwardWithPower(POWER);
        sleep(FORWARD_TIME);

        drive.stop();
        sleep(PAUSE_TIME);

        drive.turnRightWithPower(POWER);
        sleep(SIDE_TIME);

        drive.stop();
        sleep(PAUSE_TIME);

        drive.forwardWithPower(POWER);
        sleep(FORWARD2_TIME);

        drive.stop();
        sleep(PAUSE_TIME);
    }



}