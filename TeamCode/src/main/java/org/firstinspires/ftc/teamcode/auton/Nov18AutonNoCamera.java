package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.teamcode.util.MecanumDrive;
import org.firstinspires.ftc.teamcode.util.GreenShroomVision;

@Autonomous(name="Nov18AutonNoCamera", group="Linear Opmode")
@Config
public class Nov18AutonNoCamera extends LinearOpMode {

    protected MecanumDrive drive;

    public static int FORWARD2_TIME = 700;

    protected void setupDevices(){
        drive = new MecanumDrive(hardwareMap);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        setupDevices();
        waitForStart();

        drive.forwardWithPower(0.8);
        sleep(FORWARD2_TIME);


    }



}
