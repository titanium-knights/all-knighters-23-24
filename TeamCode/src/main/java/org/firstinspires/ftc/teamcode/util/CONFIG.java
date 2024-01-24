package org.firstinspires.ftc.teamcode.util;

public class CONFIG {
    //We use the CONFIG to store all of our port numbers. This allows us to have them all in one place.

    //ch = control hub
    //eh = expansion hub

    //Drivetrain
    public static String FRONT_LEFT = "fl"; //ch 1
    public static String FRONT_RIGHT = "fr"; //ch 3
    public static String BACK_LEFT = "bl"; //ch 0
    public static String BACK_RIGHT = "br"; //ch 2

    //Odometry
    public static String O_C = "fl"; //ch 1
    public static String O_L = "fr"; //ch 3
    public static String O_R = "bl"; //ch 0

    //Subsystems
    public static String intakeMotor = "intakeMotor"; //eh 0
    //slides
    public static String slidesMotor = "slidesMotor"; //eh 1
    public static String smr = "";
    public static String sml = "";

    //High hang motor 2
    public static String highHangMotor = "highHangMotor"; // Eh 2
    //Servos
    public static String arm = "armServo"; //ch 3
    public static String claw = "clawServo"; //ch 2
    public static String pokey = "pokey"; //ch 4

    public static String clawSwivel = "";
    public static String carriagePivotL = "carriagePivotServoL"; //eh 5
//    public static String carriagePivotR = "carriagePivotServoR"; //eh 3

    public static String carriageFlap = "carriageFlapServo"; //eh 4
    public static String intakeServo = "intakePivotServo"; //eh 2



    // plane

    public static String planeServo = "planeLauncher"; //ch 0



}
