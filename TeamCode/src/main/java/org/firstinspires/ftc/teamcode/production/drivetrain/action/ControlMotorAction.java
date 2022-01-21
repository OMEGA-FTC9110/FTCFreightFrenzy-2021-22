package org.firstinspires.ftc.teamcode.production.drivetrain.action;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public interface ControlMotorAction {

    void performAction(DcMotorEx frontLeft, DcMotorEx frontRight, DcMotorEx backLeft, DcMotorEx backRight);
}
