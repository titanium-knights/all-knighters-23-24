package tests;
import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.util.*;

import com.acmerobotics.dashboard.config.Config;
@TeleOp
public class LocalizationEncoders extends OpMode {
    MecanumDrive drive; // aidawkfafhiuawf
    DcMotor o_c;
    DcMotor o_l;
    DcMotor o_r;

    //woot
    public static double DRIVE_SPEED_CURRENT = .8;

    public void init() { //everything when you press the play button before u start goes here (INITialize, get it?)
        o_l = hardwareMap.get(DcMotorEx.class, CONFIG.O_L);
        o_r = hardwareMap.get(DcMotorEx.class, CONFIG.O_R);
        o_c = hardwareMap.get(DcMotorEx.class, CONFIG.O_C);

        drive = new MecanumDrive(hardwareMap);
    }

    public void loop() { //gamepad buttons that call util methods go here
        telemetry.addData("O_L", o_l.getCurrentPosition());
        telemetry.addData("O_R", o_r.getCurrentPosition());
        telemetry.addData("O_C", o_c.getCurrentPosition());

        drive.teleOpRobotCentric(gamepad1, DRIVE_SPEED_CURRENT); //go drive vroom



    }
}

