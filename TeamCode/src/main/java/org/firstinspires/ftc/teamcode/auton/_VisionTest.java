package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.GreenShroomVision;

@Autonomous(name="VisionTest", group="Linear Opmode")
@Config
public class _VisionTest extends LinearOpMode {

    protected GreenShroomVision vision;

    public static int position;

    Telemetry dashTelemetry = FtcDashboard.getInstance().getTelemetry();

    protected void setupDevices(){
        vision = new GreenShroomVision(hardwareMap, dashTelemetry);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        setupDevices();
        position = vision.getPosition();
        waitForStart();

        dashTelemetry.addData("Detected: ", position);
        sleep(30000);


    }



}
