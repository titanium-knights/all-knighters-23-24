package org.firstinspires.ftc.teamcode.util;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.pipelines.GreenShroomPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.firstinspires.ftc.robotcore.external.Telemetry;


public class GreenShroomVision {
    OpenCvCamera camera;
    public GreenShroomPipeline pipeline;

    Telemetry dashTelemetry = FtcDashboard.getInstance().getTelemetry();

    public GreenShroomVision(HardwareMap hmap, Telemetry telemetry) {
        int cameraMonitorViewId = hmap.appContext.getResources().getIdentifier("cameraMonitorViewId",
                "id", hmap.appContext.getPackageName());
        WebcamName webcamName = hmap.get(WebcamName.class, "Webcam 1");
        camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        pipeline = new GreenShroomPipeline(telemetry);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
                FtcDashboard.getInstance().startCameraStream(camera, 0);
                camera.setPipeline(pipeline);
            }
            @Override
            public void onError(int errorCode) {

            }
        });
    }
    public int getPosition(){
        GreenShroomPipeline.CapstonePosition capstonePosition = pipeline.getAnalysis();
        dashTelemetry.addData("Capstone Position: ", capstonePosition);
        telemetry.addData("Capstone Position: ", capstonePosition);

        if(capstonePosition == GreenShroomPipeline.CapstonePosition.LEFT){
            return 1;
        }
        else if (capstonePosition == GreenShroomPipeline.CapstonePosition.RIGHT) {
            return 3;
        }
        else{
            return 2;
        }
    }
}
