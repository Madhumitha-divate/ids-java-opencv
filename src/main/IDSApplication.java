package main;

import controller.IDSController;

public class IDSApplication {

    public static void main(String[] args) {

        // ✅ Load OpenCV native library
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
        System.out.println("✅ OpenCV Library Loaded: " + org.opencv.core.Core.VERSION);

        // ✅ Start the IDS system
        IDSController controller = new IDSController();
        controller.startSystem();
    }
}
