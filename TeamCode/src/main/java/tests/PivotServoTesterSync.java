package tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.CONFIG;
import org.firstinspires.ftc.teamcode.util.PixelCarriage;

@Config
@TeleOp
public class PivotServoTesterSync extends OpMode {
    public static String servoLeftName = CONFIG.carriagePivotL;
//    public static String servoRightName = CONFIG.carriagePivotR;

    public static double pos = 0;
    Servo servoLeft;
    Servo servoRight;
    PixelCarriage pixelCarriage;
    Telemetry dashTelemetry = FtcDashboard.getInstance().getTelemetry();

    @Override
    public void init() {
        servoLeft = hardwareMap.get(Servo.class, servoLeftName);
//        servoRight = hardwareMap.get(Servo.class, servoRightName);
        pixelCarriage = new PixelCarriage(hardwareMap);

    }

    @Override
    public void loop() {
        servoRight.setPosition(1-pos);
        servoLeft.setPosition(pos);
        telemetry.addData("Servo (left) position", servoLeft.getPosition());
//        telemetry.addData("Servo (right) position", servoRight.getPosition());
        telemetry.update();

        //CARRIAGE FLAP CONTROLS -- CONTROLLER 1
        if (gamepad1.y) { //if click a, pickup position set
            pixelCarriage.setCarriageOpen(true); //opens the carriage
        }
        if (gamepad1.a) { //place
            pixelCarriage.setCarriageOpen(false); //closes the carriage
        }
    }
}