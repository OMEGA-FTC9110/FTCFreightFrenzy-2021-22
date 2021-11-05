package org.firstinspires.ftc.teamcode.util;

public class MathUtils {

    public static double max(double... doubleArray) {
        double currentMax = doubleArray[0];

        for (int i = 1; i < doubleArray.length; i++) {
            double entry = doubleArray[i];

            if (Math.abs(entry) > currentMax) {
                currentMax = entry;
            }
        }

        return currentMax;
    }

}
