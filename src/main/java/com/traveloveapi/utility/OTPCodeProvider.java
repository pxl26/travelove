package com.traveloveapi.utility;

import java.util.Random;
import java.util.UUID;

public class OTPCodeProvider {
    static public String GenegateOTP(int length) {
        Random rand = new Random();
        int random_num = rand.nextInt(9);
        int code = random_num*10000 + rand.nextInt(10000);
        int temp = 10;
        for (int i=1;i<length;i++) {
            random_num *= 10;
            temp*=10;
        }

        code += temp/10;
        return String.valueOf(code);
    }
}
