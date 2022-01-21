package org.firstinspires.ftc.teamcode.production.drivetrain;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.production.drivetrain.action.ControlMotorAction;
import org.firstinspires.ftc.teamcode.production.drivetrain.action.MotorController;
import org.firstinspires.ftc.teamcode.util.ObjectUtils;

public class DriveTrainImpl implements DriveTrain {

    private final LinearOpMode opMode;
    private final DcMotorEx frontLeft;
    private final DcMotorEx frontRight;
    private final DcMotorEx backLeft;
    private final DcMotorEx backRight;

    private final MotorController controller;

    private final double TICKS_PER_REV = 1680;
    private final double WHEEL_DIAMETER_INCHES = 4;
    private final double WHEEL_CIRCUMFERENCE_INCHES = WHEEL_DIAMETER_INCHES * Math.PI;
    private final double TICKS_PER_INCH = TICKS_PER_REV / WHEEL_CIRCUMFERENCE_INCHES;

    private DriveTrainImpl(LinearOpMode opMode, DcMotorEx frontLeft, DcMotorEx frontRight, DcMotorEx backLeft, DcMotorEx backRight) {
        this.opMode = opMode;
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;

        this.controller = new MotorController(frontLeft, frontRight, backLeft, backRight);
    }

    public static DriveTrainBuilder builder(LinearOpMode opMode) {
        return new DriveTrainBuilder(opMode);
    }

    @Override
    public void initialize(boolean isAuto) {
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        if (isAuto) {
            setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        else {
            setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }

        opMode.telemetry.addLine("DriveTrain module initialized.");
        opMode.telemetry.update();
    }

    @Override
    public void setRunMode(DcMotor.RunMode runMode) {
        controller.performAction(motor -> motor.setMode(runMode));
    }

    @Override
    public void setPower(double motorPower) {
        controller.performAction(motor -> motor.setPower(motorPower));
    }

    @Override
    public void setPower(double flPower, double frPower, double blPower, double brPower) {
        performAction((fl, fr, bl, br) -> {
            fl.setPower(flPower);
            fr.setPower(frPower);

            bl.setPower(blPower);
            br.setPower(brPower);
        });

        controller.performAction(motor -> opMode.telemetry.addLine(motor.getDeviceName() + ": " + motor.getPower()));
    }

    @Override
    public void move(double inches, MoveDirection direction) {
        move(inches, direction, direction.isStrafe() ? 0.6 : 0.8);
    }

    @Override
    public void move(double inches, MoveDirection direction, double power) {
        double target = inches * TICKS_PER_INCH;

        setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        switch (direction) {
            case FORWARD:
                setTargetPosition(target, 1, 1, 1, 1);
                break;
            case BACKWARD:
                setTargetPosition(target, -1, -1, -1, -1);
                break;
            case STRAFE_RIGHT:
                setTargetPosition(target, 1, -1, -1, 1);
                break;
            case STRAFE_LEFT:
                setTargetPosition(target, -1, 1, 1, -1);
                break;
        }

        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        setRunMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (isBusy()) {
            setPower(power);
        }

        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        setPower(0.0);

        setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void rotate(double angle) {

    }

    @Override
    public void rotate(double angle, double power) {

    }

    @Override
    public void setTargetPosition(double distance, double flPower, double frPower, double blPower, double brPower) {
        performAction((fl, fr, bl, br) -> {
            fl.setTargetPosition((int) (flPower * distance));
            fr.setTargetPosition((int) (frPower * distance));

            bl.setTargetPosition((int) (blPower * distance));
            br.setTargetPosition((int) (brPower * distance));
        });
    }

    @Override
    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior) {
        controller.performAction(motor -> motor.setZeroPowerBehavior(behavior));
    }

    private void performAction(ControlMotorAction controlMotorAction) {
        controlMotorAction.performAction(frontLeft, frontRight, backLeft, backRight);
    }

    private boolean isBusy() {
        return frontLeft.isBusy() && frontRight.isBusy() && backRight.isBusy() && backLeft.isBusy();
    }

    public static class DriveTrainBuilder {

        private final LinearOpMode opMode;

        private DcMotorEx frontLeft;
        private DcMotorEx frontRight;
        private DcMotorEx backLeft;
        private DcMotorEx backRight;

        public DriveTrainBuilder(LinearOpMode opMode) {
            this.opMode = opMode;
        }

        public DriveTrainBuilder setFrontLeft(String frontLeft) {
            this.frontLeft = getMotor(frontLeft);

            return this;
        }

        public DriveTrainBuilder setFrontRight(String frontRight) {
            this.frontRight = getMotor(frontRight);

            return this;
        }

        public DriveTrainBuilder setBackLeft(String backLeft) {
            this.backLeft = getMotor(backLeft);

            return this;
        }

        public DriveTrainBuilder setBackRight(String backRight) {
            this.backRight = getMotor(backRight);

            return this;
        }

        public DriveTrain build() {
            ObjectUtils.requireNonNull(frontLeft, frontLeft, backLeft, backRight);

            return new DriveTrainImpl(opMode, frontLeft, frontRight, backLeft, backRight);
        }

        private DcMotorEx getMotor(String motorName) {
            return opMode.hardwareMap.get(DcMotorEx.class, motorName);
        }
    }
}
