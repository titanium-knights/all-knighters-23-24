package org.firstinspires.ftc.teamcode.util;

public class CONFIG {
    //We use the CONFIG to store all of our port numbers. This allows us to have them all in one place.

    //ch = control hub
    //eh = expansion hub

    //Drivetrain
    public static String FRONT_LEFT = "fl"; //ch 0
    public static String FRONT_RIGHT = "fr"; //ch 1
    public static String BACK_LEFT = "bl"; //ch 2
    public static String BACK_RIGHT = "br"; //ch 3

    //Odometry
    public static String O_C = "bl"; //center odo, ch 2
    public static String O_L = "fl"; //left odo, ch 0
    public static String O_R = "fr"; //right odo, ch 1

    //Subsystems
    public static String intakeMotor = "intakeMotor";
    public static String arm = "armMotor";
    public static String clawSwivel = "pixelClawPivotServo";
    public static String pixelClaw = "pixelClawServo";


}
