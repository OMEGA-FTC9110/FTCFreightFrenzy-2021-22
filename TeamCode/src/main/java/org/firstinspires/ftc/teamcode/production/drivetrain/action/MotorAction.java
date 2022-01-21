package org.firstinspires.ftc.teamcode.production.drivetrain.action;

import com.qualcomm.robotcore.hardware.DcMotorEx;

@FunctionalInterface
public interface MotorAction {

    void perform(DcMotorEx motor);
}
