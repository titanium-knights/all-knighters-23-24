package tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.CONFIG;

@Config
@TeleOp
public class PivotServoTester extends OpMode {
    public static String servoLeftName = CONFIG.carriagePivotL;
    public static String servoRightName = CONFIG.carriagePivotR;

    public static double pos_L = 0;
    public static double pos_R = 1;

    Servo servoLeft;
    Servo servoRight;
    Telemetry dashTelemetry = FtcDashboard.getInstance().getTelemetry();

    @Override
    public void init() {
        servoLeft = hardwareMap.get(Servo.class, servoLeftName);
        servoRight = hardwareMap.get(Servo.class, servoRightName);
    }

    @Override
    public void loop() {
        servoLeft.setPosition(pos_L);
        servoRight.setPosition(pos_R);
        telemetry.addData("Servo (left) position", servoLeft.getPosition());
        telemetry.addData("Servo (right) position", servoRight.getPosition());
        telemetry.update();
    }
}