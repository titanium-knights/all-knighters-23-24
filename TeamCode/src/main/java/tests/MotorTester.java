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
public class MotorTester extends OpMode {
    DcMotorEx motor;
    public static String motorName = "highHangMotor";
    Telemetry dashTelemetry = FtcDashboard.getInstance().getTelemetry();

    @Override
    public void init() {
        motor = hardwareMap.get(DcMotorEx.class, motorName);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        if(Math.abs(gamepad1.left_stick_y)>0.1){
            motor.setPower(gamepad1.left_stick_y);
        }
        else{
            motor.setPower(0);
        }
        telemetry.addData("Motor Position", motor.getCurrentPosition());
        dashTelemetry.addData("Motor Position", motor.getCurrentPosition());

    }


}