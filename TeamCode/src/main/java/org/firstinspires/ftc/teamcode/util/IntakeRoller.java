package org.firstinspires.ftc.teamcode.util;
import com.qualcomm.robotcore.hardware.*; //import this to use servos and DC motors

//this is a util class! util classes can be used for the subsystems to store methods
public class IntakeRoller {
    public DcMotor intakeMotor; //DcMotor is taken from the hardware import

    public IntakeRoller(HardwareMap hmap) { //get motor from port
        this.intakeMotor = hmap.dcMotor.get(Config.intakeMotor);
        //Config.intakeMotor connected to a variable WE created
    }
}
