package org.firstinspires.ftc.teamcode.experimental.drivetrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;

public class DriveTrainImpl implements DriveTrain {

    private final DcMotorEx frontLeft;
    private final DcMotorEx frontRight;
    private final DcMotorEx backLeft;
    private final DcMotorEx backRight;

    private final List<DcMotorEx> motors;

    private DriveTrainImpl(DcMotorEx frontLeft, DcMotorEx frontRight, DcMotorEx backLeft, DcMotorEx backRight) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;

        this.motors = Arrays.asList(frontLeft, frontRight, backLeft, backRight);
    }

    public static DriveTrainBuilder builder(HardwareMap hardwareMap) {
        return new DriveTrainBuilder(hardwareMap);
    }

    @Override
    public void initialize() {
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void setRunMode(DcMotor.RunMode runMode) {
        performAction(motor -> motor.setMode(runMode));
    }

    @Override
    public void setPower(double motorPower) {
        performAction(motor -> motor.setPower(motorPower));
    }

    @Override
    public void setPower(double flPower, double frPower, double blPower, double brPower) {
        frontLeft.setPower(flPower);
        frontRight.setPower(frPower);
        backLeft.setPower(blPower);
        backRight.setPower(brPower);
    }

    private void performAction(MotorAction action) {
        for (DcMotorEx motor : motors) {
            action.perform(motor);
        }
    }

    public static class DriveTrainBuilder {

        private final HardwareMap hardwareMap;

        private DcMotorEx frontLeft;
        private DcMotorEx frontRight;
        private DcMotorEx backLeft;
        private DcMotorEx backRight;

        public DriveTrainBuilder(HardwareMap hardwareMap) {
            this.hardwareMap = hardwareMap;
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

            return new DriveTrainImpl(frontLeft, frontRight, backLeft, backRight);
        }

        private DcMotorEx getMotor(String motorName) {
            return hardwareMap.get(DcMotorEx.class, motorName);
        }
    }
}
