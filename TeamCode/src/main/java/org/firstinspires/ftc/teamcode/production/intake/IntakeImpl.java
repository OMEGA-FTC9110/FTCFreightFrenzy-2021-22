package org.firstinspires.ftc.teamcode.production.intake;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.ObjectUtils;

public class IntakeImpl implements Intake {

    private final DcMotorEx linearLeft;
    private final DcMotorEx linearRight;

    private IntakeImpl(DcMotorEx linearLeft, DcMotorEx linearRight) {
        this.linearLeft = linearLeft;
        this.linearRight = linearRight;
    }

    public static IntakeImpl.IntakeBuilder builder(HardwareMap hardwareMap) {
        return new IntakeBuilder(hardwareMap);
    }

    @Override
    public void initialize() {
        linearLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void raiseSlides() {
        setSlidePower(0.25);
    }

    @Override
    public void lowerSlides() {
        setSlidePower(-0.25);
    }

    private void setSlidePower(double power) {
        if (!isBusy()) {
            return;
        }

        linearRight.setPower(power);
        linearLeft.setPower(power);
    }

    private boolean isBusy() {
        return linearLeft.isBusy() || linearRight.isBusy();
    }

    public static class IntakeBuilder {

        private final HardwareMap hardwareMap;

        private DcMotorEx linearRight;
        private DcMotorEx linearLeft;

        public IntakeBuilder(HardwareMap hardwareMap) {
            this.hardwareMap = hardwareMap;
        }

        public IntakeImpl.IntakeBuilder setLinearLeft(String linearLeft) {
            this.linearLeft = getMotor(linearLeft);

            return this;
        }

        public IntakeImpl.IntakeBuilder setLinearRight(String linearRight) {
            this.linearRight = getMotor(linearRight);

            return this;
        }

        public Intake build() {
            ObjectUtils.requireNonNull(linearLeft, linearRight);

            return new IntakeImpl(linearLeft, linearRight);
        }

        private DcMotorEx getMotor(String motorName) {
            return hardwareMap.get(DcMotorEx.class, motorName);
        }
    }
}
