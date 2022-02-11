package org.firstinspires.ftc.teamcode.production.intake;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.production.drivetrain.action.MotorController;
import org.firstinspires.ftc.teamcode.util.ObjectUtils;

public class IntakeImpl implements Intake {

    private final LinearOpMode opMode;

    private final DcMotorEx intakeLeft;
    private final DcMotorEx intakeRight;
    private final DcMotorEx linearLeft;
    private final DcMotorEx linearRight;
    private final CRServo servo;

    private final MotorController slides;
    private final MotorController intakeController;

    private final double TICKS_PER_REV = 560; // 40s over 2; try 537.6
    private final double WHEEL_DIAMETER_INCHES = 4;
    private final double WHEEL_CIRCUMFERENCE_INCHES = WHEEL_DIAMETER_INCHES * Math.PI;
    private final double TICKS_PER_INCH = TICKS_PER_REV / WHEEL_CIRCUMFERENCE_INCHES;

    private IntakeImpl(LinearOpMode opMode, DcMotorEx intakeLeft, DcMotorEx intakeRight,
                       DcMotorEx linearLeft, DcMotorEx linearRight, CRServo servo) {
        this.opMode = opMode;
        this.intakeLeft = intakeLeft;
        this.intakeRight = intakeRight;
        this.linearLeft = linearLeft;
        this.linearRight = linearRight;
        this.servo = servo;

        this.slides = new MotorController(linearLeft, linearRight);
        this.intakeController = new MotorController(intakeLeft, intakeRight);
    }

    public static IntakeImpl.IntakeBuilder builder(LinearOpMode opMode) {
        return new IntakeBuilder(opMode);
    }

    @Override
    public void initialize(boolean isAuto) {
        intakeRight.setDirection(DcMotorSimple.Direction.REVERSE);
        linearRight.setDirection(DcMotorSimple.Direction.REVERSE);

        slides.performAction(motor -> motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE));

        opMode.telemetry.addLine("Intake module initialized.");
    }

    @Override
    public void setSlidePower(double power) {
        slides.performAction(motor -> motor.setPower(power));
    }

    @Override
    public void setSlidePosition(double inches) {
        double target = inches * TICKS_PER_INCH;

        slides.performAction(motor -> {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setTargetPosition((int) target);

            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        });

        while (linearRight.isBusy() && linearLeft.isBusy()) {
            setSlidePower(0.5);
        }

        slides.performAction(motor -> {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setPower(0.0);

            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        });
    }

    @Override
    public void activateIntake() {
        setIntakePower(1.0);
    }

    @Override
    public void stopIntake() {
        setIntakePower(0.0);
    }

    @Override
    public void reverseIntake() {
        setIntakePower(-1.0);
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
        intakeController.performAction(motor -> motor.setPower(power));
    }

    public static class IntakeBuilder {

        private final LinearOpMode opMode;

        private DcMotorEx intakeRight;
        private DcMotorEx intakeLeft;
        private DcMotorEx linearLeft;
        private DcMotorEx linearRight;

        private CRServo servo;

        public IntakeBuilder(LinearOpMode opMode) {
            this.opMode = opMode;
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
            this.servo = opMode.hardwareMap.get(CRServo.class, servoString);

            return this;
        }

        public Intake build() {
            ObjectUtils.requireNonNull(intakeLeft, intakeRight, linearLeft, linearRight, servo);

            return new IntakeImpl(opMode, intakeLeft, intakeRight, linearLeft, linearRight, servo);
        }

        private DcMotorEx getMotor(String motorName) {
            return opMode.hardwareMap.get(DcMotorEx.class, motorName);
        }
    }
}
