package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.*;
@Config
public class PlaneLauncher {
    public CRServo launcher;


    public PlaneLauncher(HardwareMap hmap) {
        this.launcher = hmap.crservo.get(CONFIG.plane_launcher);
    }

    public void launchPlane()
    {
        launcher.setPower(.1);

    }

    public void stopLaunch(){
        launcher.setPower(0);
    }


}

