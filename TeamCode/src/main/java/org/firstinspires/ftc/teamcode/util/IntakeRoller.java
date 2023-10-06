package org.firstinspires.ftc.teamcode.util;
import com.qualcomm.robotcore.hardware.*; //import this to use servos and DC motors


public class IntakeRoller {
    public DcMotor IntakeMotor; //servo or motor???

    public IntakeRoller(HardwareMap hmap) { //get motor from port
        this.IntakeMotor = hmap.dcMotor.get(Config.intakeMotor);
    }
}
