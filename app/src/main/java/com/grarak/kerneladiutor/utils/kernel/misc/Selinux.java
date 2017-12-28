package com.grarak.kerneladiutor.utils.kernel.misc;

import android.content.Context;

import com.grarak.kerneladiutor.fragments.ApplyOnBootFragment;
import com.grarak.kerneladiutor.utils.Utils;
import com.grarak.kerneladiutor.utils.root.Control;

/**
 * Created by Morogoku on 11/09/2017.
 */

public class Selinux {

    private static final String SELINUX_ENFORCE = "/sys/fs/selinux/enforce";

    public static void setEnforceMode(int mode, Context context) {
        run(Control.chmod("644", SELINUX_ENFORCE), SELINUX_ENFORCE, context);
        run(Control.write(String.valueOf(mode), SELINUX_ENFORCE), SELINUX_ENFORCE, context);
    }

    public static String getStringEnforceMode() {
        int mode = Utils.strToInt(Utils.readFile(SELINUX_ENFORCE));
        switch (mode){
            case 0:
                return "Permissive";
            case 1:
                return "Enforcing";
        }
        return null;
    }

    public static int getEnforceMode(){
        return Utils.strToInt(Utils.readFile(SELINUX_ENFORCE));
    }

    public static boolean supported() {
        return Utils.existFile(SELINUX_ENFORCE);
    }

    private static void run(String command, String id, Context context) {
        Control.runSetting(command, ApplyOnBootFragment.MISC, id, context);
    }
}
