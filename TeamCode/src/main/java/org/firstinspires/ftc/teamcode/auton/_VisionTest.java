package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.GreenShroomVision;
import org.firstinspires.ftc.teamcode.util.WebcamServo;

@Autonomous(name="VisionTest", group="Linear Opmode")
@Config
public class _VisionTest extends LinearOpMode {

    protected GreenShroomVision vision;
    protected WebcamServo webcamServo;

    public static int position;


    Telemetry dashTelemetry = FtcDashboard.getInstance().getTelemetry();

    protected void setupDevices(){

        vision = new GreenShroomVision(hardwareMap, dashTelemetry);
        webcamServo = new WebcamServo(hardwareMap);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        setupDevices();
        webcamServo.setPosition(true);
        position = vision.getPosition();
        dashTelemetry.addData("Detected: ", position);

        waitForStart();

        sleep(3000);
        webcamServo.setPosition(false);

    }



}
