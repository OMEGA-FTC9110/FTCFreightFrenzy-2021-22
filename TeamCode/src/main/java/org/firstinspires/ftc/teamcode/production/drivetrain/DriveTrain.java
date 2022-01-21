package org.firstinspires.ftc.teamcode.production.drivetrain;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.production.RobotModule;

public interface DriveTrain extends RobotModule {

    void setRunMode(DcMotor.RunMode mode);

    void setPower(double motorPower);

    void setPower(double flPower, double frPower, double blPower, double brPower);

    void move(double inches, MoveDirection direction);

    void move(double inches, MoveDirection direction, double power);

    void rotate(double angle);

    void rotate(double angle, double power);

    void setTargetPosition(double distance, double flPower, double frPower, double blPower, double brPower);

    void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior);
}
