package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.util.*;

public class TeleOp_Sal extends OpMode {
    public PixelClaw claw;
    public IntakeRoller roller;
    @Override
    public void init() {
        claw = new PixelClaw(hardwareMap);
        roller = new IntakeRoller(hardwareMap);

    }
    @Override
    public void loop() {
        //y=open; a=close
        if (gamepad1.y) {
            claw.setPosition(54098.45098678905);
        }
        if (gamepad1.a) {
            claw.setPosition(4875726948.4598673);
        }
    }

}
