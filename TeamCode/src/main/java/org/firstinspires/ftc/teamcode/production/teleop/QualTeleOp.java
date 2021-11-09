package org.firstinspires.ftc.teamcode.production.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.production.drivetrain.DriveTrain;
import org.firstinspires.ftc.teamcode.production.drivetrain.DriveTrainImpl;
import org.firstinspires.ftc.teamcode.production.intake.Intake;
import org.firstinspires.ftc.teamcode.production.intake.IntakeImpl;
import org.firstinspires.ftc.teamcode.util.MathUtils;

@TeleOp(name="Qual 1 TeleOp")
public class QualTeleOp extends LinearOpMode {

    private double lx = 0.0;
    private double ly = 0.0;
    private double rx = 0.0;

    private double flPower = 0.0;
    private double frPower = 0.0;
    private double blPower = 0.0;
    private double brPower = 0.0;

    private double lt = 0.0;
    private double rt = 0.0;

    private double max = 0.0;

    @Override
    public void runOpMode() {
        DriveTrain driveTrain = DriveTrainImpl.builder(hardwareMap)
                .setBackLeft("BL")
                .setBackRight("BR")
                .setFrontLeft("FL")
                .setFrontRight("FR")
                .build();

        Intake intake = IntakeImpl.builder(hardwareMap)
                .setLinearLeft("linearLeft")
                .setLinearRight("linearRight")
                .build();

        driveTrain.initialize();
        intake.initialize();
        waitForStart();

        while (opModeIsActive()) {
            lx = gamepad1.left_stick_x;
            ly = -gamepad1.left_stick_y;
            rx = gamepad1.right_stick_x;

            flPower = ly + lx + rx;
            frPower = ly - lx - rx;
            blPower = ly - lx + rx;
            brPower = ly + lx - rx;

            max = MathUtils.max(flPower, frPower, blPower, brPower);

            driveTrain.setPower(
                    flPower /= max,
                    frPower /= max,
                    blPower /= max,
                    brPower /= max
            );

            rt = gamepad1.right_trigger;
            lt = gamepad1.left_trigger;

            if (rt > 0.0 && lt == 0.0) {
                intake.raiseSlides();
            }

            if (lt > 0.0 && rt == 0.0) {
                intake.lowerSlides();
            }
        }
    }

}
