package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.HighHang;
import org.firstinspires.ftc.teamcode.util.GreenShroomVision;

@Autonomous(name="HighHangVisionTest", group="Linear Opmode")
@Config
public class _HighHangVisionTest extends LinearOpMode {

    protected GreenShroomVision vision;
    protected HighHang highhang;

    public static int position;

    Telemetry dashTelemetry = FtcDashboard.getInstance().getTelemetry();

    protected void setupDevices(){
        vision = new GreenShroomVision(hardwareMap, dashTelemetry);
        highhang = new HighHang(hardwareMap);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        setupDevices();
        highhang.goToCamera(); //go down
        position = vision.getPosition();
        waitForStart();

        dashTelemetry.addData("Detected: ", position);
        sleep(30000);
        highhang.goToReset(); //go down


    }



}
