package org.firstinspires.ftc.teamcode.production.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.production.drivetrain.DriveTrain;
import org.firstinspires.ftc.teamcode.production.drivetrain.DriveTrainImpl;
import org.firstinspires.ftc.teamcode.production.intake.Intake;
import org.firstinspires.ftc.teamcode.production.intake.IntakeImpl;
import org.firstinspires.ftc.teamcode.util.MathUtils;

@TeleOp(name="Qual 1 TeleOp New - 9110")
public class QualTeleOpNew extends LinearOpMode {

    private double lx = 0.0;
    private double ly = 0.0;
    private double rx = 0.0;

    private double flPower = 0.0;
    private double frPower = 0.0;
    private double blPower = 0.0;
    private double brPower = 0.0;

    private double lt = 0.0;
    private double rt = 0.0;

    private boolean isSlow = false;

    private double max = 0.0;

    @Override
    public void runOpMode() {
        DriveTrain driveTrain = DriveTrainImpl.builder(this)
                .setBackLeft("BL")
                .setBackRight("BR")
                .setFrontLeft("FL")
                .setFrontRight("FR")
                .build();

        Intake intake = IntakeImpl.builder(this)
                .setIntakeLeft("IL")
                .setIntakeRight("IR")
                .setLinearLeft("LL")
                .setLinearRight("LR")
                .setServo("CS")
                .build();

        driveTrain.initialize(false);
        intake.initialize(false);

        telemetry.setAutoClear(true);
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            lx = -gamepad1.left_stick_x * 1.1;
            ly = gamepad1.left_stick_y;
            rx = -gamepad1.right_stick_x;

            flPower = ly + lx + rx;
            frPower = ly - lx - rx;
            blPower = ly - lx + rx;
            brPower = ly + lx - rx;

            max = Math.max(Math.max(Math.abs(flPower), Math.abs(frPower)),
                    Math.max(Math.abs(blPower), Math.abs(brPower)));

            isSlow = gamepad1.right_bumper;

            driveTrain.setPower(
                    (isSlow ? 0.25 : 1) * (flPower /= max),
                    (isSlow ? 0.25 : 1) * (frPower /= max),
                    (isSlow ? 0.25 : 1) * ( blPower /= max),
                    (isSlow ? 0.25 : 1) * (brPower /= max)
            );

            rt = gamepad1.right_trigger;
            lt = gamepad1.left_trigger;

            if (rt > 0.2 && lt == 0.0) {
                intake.setSlidePower(0.5);
            }
            else if (lt > 0.2 && rt == 0.0) {
                intake.setSlidePower(-0.2);
            }
            else {
                intake.setSlidePower(0.0);
            }

            if (gamepad1.b) {
                intake.activateIntake();
            }
            else if (gamepad1.x) {
                intake.reverseIntake();
            }
            else {
                intake.stopIntake();
            }

            if (gamepad1.y) {
                intake.toggleCarousel();
            }
            else if (gamepad1.a) {
                intake.toggleCarouselReverse();
            }
            else {
                intake.stopCarousel();
            }

            telemetry.update();
        }
    }

}
