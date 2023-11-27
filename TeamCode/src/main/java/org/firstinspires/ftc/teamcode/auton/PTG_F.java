package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.teamcode.util.MecanumDrive;

@Autonomous(name="PTG_F", group="Linear Opmode")
@Config
public class PTG_F extends LinearOpMode {

    protected MecanumDrive drive;
    public static double POWER = .8;
    public static int FORWARD_TIME = 650;
    public static int BACKWARD_TIME = 650;

    protected void setupDevices(){
        drive = new MecanumDrive(hardwareMap);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        setupDevices();
        waitForStart();

        drive.forwardWithPower(POWER);
        sleep(FORWARD_TIME);

    }



}