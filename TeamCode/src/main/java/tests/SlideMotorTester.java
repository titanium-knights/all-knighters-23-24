package tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
@TeleOp
public class SlideMotorTester extends OpMode {
    public DcMotor smr; //lift right
    public DcMotor sml; //lift left

    public static String motorName1 = "smr";
    public static String motorName2 = "sml";

    Telemetry dashTelemetry = FtcDashboard.getInstance().getTelemetry();

    @Override
    public void init() {
        smr = hardwareMap.get(DcMotorEx.class, motorName1);
        sml = hardwareMap.get(DcMotorEx.class, motorName2);

        smr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        smr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sml.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sml.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        if(Math.abs(gamepad1.left_stick_y)>0.1){
            smr.setPower(-gamepad1.left_stick_y);
            sml.setPower(gamepad1.left_stick_y);

        }
        else{
            smr.setPower(0);
            sml.setPower(0);
        }
        telemetry.addData("Motor 1 (right) Position", smr.getCurrentPosition());
        dashTelemetry.addData("Motor (right) Position", smr.getCurrentPosition());

        telemetry.addData("Motor 2 (left) Position", sml.getCurrentPosition());
        dashTelemetry.addData("Motor 2 (left) Position", sml.getCurrentPosition());

    }


}