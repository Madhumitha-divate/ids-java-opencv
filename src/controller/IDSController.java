package controller;

import exception.DetectionException;
import monitor.CameraMonitor;
import service.DetectionService;

public class IDSController {

    public void startSystem() {

        System.out.println("==========================================");
        System.out.println("   🔒 Intrusion Detection System (IDS)   ");
        System.out.println("==========================================");

        try {
            // ✅ DetectionException is now properly thrown and caught
            DetectionService detectionService = new DetectionService();
            CameraMonitor monitor = new CameraMonitor(detectionService);

            System.out.println("🚀 IDS System Starting...");
            monitor.start(); // starts camera in separate thread

        } catch (DetectionException e) {
            // ✅ Proper use of custom exception
            System.out.println("❌ IDS Failed to Start!");
            System.out.println("Reason: " + e.getMessage());
            System.out.println("👉 Fix the issue and restart.");
        }
    }
}
