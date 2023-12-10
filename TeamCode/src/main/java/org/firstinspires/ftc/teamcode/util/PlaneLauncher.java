package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.*;
@Config
public class PlaneLauncher {
    public Servo launcher;
    public static double LAUNCH_POS = 0;


    public PlaneLauncher(HardwareMap hmap) {
        this.launcher = hmap.servo.get(CONFIG.planeServo);
    }

    public void launchPlane()
    {
        launcher.setPosition(LAUNCH_POS);

    }


}

