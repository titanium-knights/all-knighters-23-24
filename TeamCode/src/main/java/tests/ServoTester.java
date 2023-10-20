package tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.CONFIG;

@Config
@TeleOp
public class ServoTester extends OpMode {
    public static String servoName = CONFIG.pixelClaw;
    public static double pos = 0;
    Servo servo;
    Telemetry dashTelemetry = FtcDashboard.getInstance().getTelemetry();

    @Override
    public void init() {
        servo = hardwareMap.get(Servo.class, servoName);
    }

    @Override
    public void loop() {
        servo.setPosition(pos);
        telemetry.addData("Servo position", servo.getPosition());
        telemetry.update();
    }
}