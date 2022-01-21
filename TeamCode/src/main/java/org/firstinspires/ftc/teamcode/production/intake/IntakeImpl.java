package org.firstinspires.ftc.teamcode.production.intake;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.ObjectUtils;

public class IntakeImpl implements Intake {

    private final DcMotorEx intakeLeft;
    private final DcMotorEx intakeRight;
    private final DcMotorEx linearLeft;
    private final DcMotorEx linearRight;

    private final CRServo servo;

    private IntakeImpl(DcMotorEx intakeLeft, DcMotorEx intakeRight, DcMotorEx linearLeft, DcMotorEx linearRight, CRServo servo) {
        this.intakeLeft = intakeLeft;
        this.intakeRight = intakeRight;
        this.linearLeft = linearLeft;
        this.linearRight = linearRight;
        this.servo = servo;
    }

    public static IntakeImpl.IntakeBuilder builder(HardwareMap hardwareMap) {
        return new IntakeBuilder(hardwareMap);
    }

    @Override
    public void initialize(boolean isAuto) {
        intakeRight.setDirection(DcMotorSimple.Direction.REVERSE);
        linearRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void setSlidePower(double power) {
        linearLeft.setPower(power);
        linearRight.setPower(power);
    }

    @Override
    public void activateIntake() {
        setIntakePower(0.8);
    }

    @Override
    public void stopIntake() {
        setIntakePower(0.0);
    }

    @Override
    public void reverseIntake() {
        setIntakePower(-0.8);
    }

    @Override
    public void toggleCarousel() {
        servo.setPower(1.0);
    }

    @Override
    public void toggleCarouselReverse() {
        servo.setPower(-1.0);
    }

    @Override
    public void stopCarousel() {
        servo.setPower(0.0);
    }

    private void setIntakePower(double power) {
        intakeLeft.setPower(power);
        intakeRight.setPower(power);
    }

    public static class IntakeBuilder {

        private final HardwareMap hardwareMap;

        private DcMotorEx intakeRight;
        private DcMotorEx intakeLeft;
        private DcMotorEx linearLeft;
        private DcMotorEx linearRight;

        private CRServo servo;

        public IntakeBuilder(HardwareMap hardwareMap) {
            this.hardwareMap = hardwareMap;
        }

        public IntakeImpl.IntakeBuilder setIntakeRight(String intakeRight) {
            this.intakeRight = getMotor(intakeRight);

            return this;
        }

        public IntakeImpl.IntakeBuilder setIntakeLeft(String intakeLeft) {
            this.intakeLeft = getMotor(intakeLeft);

            return this;
        }

        public IntakeImpl.IntakeBuilder setLinearLeft(String linearLeft) {
            this.linearLeft = getMotor(linearLeft);

            return this;
        }

        public IntakeImpl.IntakeBuilder setLinearRight(String linearRight) {
            this.linearRight = getMotor(linearRight);

            return this;
        }

        public IntakeImpl.IntakeBuilder setServo(String servoString) {
            this.servo = hardwareMap.get(CRServo.class, servoString);

            return this;
        }

        public Intake build() {
            ObjectUtils.requireNonNull(intakeLeft, intakeRight, linearLeft, linearRight, servo);

            return new IntakeImpl(intakeLeft, intakeRight, linearLeft, linearRight, servo);
        }

        private DcMotorEx getMotor(String motorName) {
            return hardwareMap.get(DcMotorEx.class, motorName);
        }
    }
}
