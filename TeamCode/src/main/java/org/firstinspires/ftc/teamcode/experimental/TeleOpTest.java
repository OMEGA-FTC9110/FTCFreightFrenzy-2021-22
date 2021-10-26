package org.firstinspires.ftc.teamcode.experimental;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Experimental: TeleOp")
public class TeleOpTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        waitForStart();

        telemetry.setAutoClear(true);

        DriveTrain driveTrain = DriveTrainImpl.builder(hardwareMap)
                .setBackLeft("BL")
                .setBackRight("BR")
                .setFrontLeft("FL")
                .setFrontRight("FR")
                .build();

        driveTrain.initialize();

        while (opModeIsActive()) {

        }

    }
}
