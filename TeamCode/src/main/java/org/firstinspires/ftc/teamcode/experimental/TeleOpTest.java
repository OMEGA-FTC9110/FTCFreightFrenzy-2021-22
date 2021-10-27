package org.firstinspires.ftc.teamcode.experimental;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

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
        DcMotorEx middleMotor = hardwareMap.get(DcMotorEx.class, "MR");

        while (opModeIsActive()) {
            middleMotor.setPower(gamepad1.left_stick_x);
            driveTrain.setPower(gamepad1.left_stick_y / 2);

            double rightStickX = gamepad1.right_stick_x / 2;
            driveTrain.setPower(rightStickX, -rightStickX, rightStickX, -rightStickX);
        }

    }
}
