package org.firstinspires.ftc.teamcode.production.drivetrain;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.production.RobotMechanism;

public interface DriveTrain extends RobotMechanism {

    void setRunMode(DcMotor.RunMode mode);

    void setPower(double motorPower);

    void setPower(double flPower, double frPower, double blPower, double brPower);
}
