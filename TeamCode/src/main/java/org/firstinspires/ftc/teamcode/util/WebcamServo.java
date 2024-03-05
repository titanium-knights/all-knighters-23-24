package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.*;
@Config
public class WebcamServo {
    public Servo webcamServo;
    public static double UP_POS = 0.67;
    public static double DOWN_POS = 0.1;

    public WebcamServo(HardwareMap hmap) {
        this.webcamServo = hmap.servo.get(CONFIG.webcamServo);
    }

    public void setPosition(boolean goUp)
    {
        if (goUp) {
            webcamServo.setPosition(UP_POS);
        } else {
            webcamServo.setPosition(DOWN_POS);
        }
    }

}

