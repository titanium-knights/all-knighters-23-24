package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.teamcode.util.MecanumDrive;
import org.firstinspires.ftc.teamcode.util.GreenShroomVision;

@Autonomous(name="VisionTEst", group="Linear Opmode")
@Config
public class VisionTest extends LinearOpMode {

    protected GreenShroomVision vision;

    public static int position;

    protected void setupDevices(){
        vision = new GreenShroomVision(hardwareMap, null);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        setupDevices();
        position = vision.getPosition();
        waitForStart();

        telemetry.addData("Detected: ", position);
        sleep(10000);


    }



}
