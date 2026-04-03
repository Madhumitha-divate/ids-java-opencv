package monitor;

import exception.DetectionException;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;
import service.DetectionService;

public class CameraMonitor extends Thread {

    private DetectionService detectionService;
    private volatile boolean running = true; // ✅ volatile for thread safety

    private static final String WINDOW_NAME = "🏠 IDS - Intrusion Detection System";

    public CameraMonitor(DetectionService detectionService) {
        this.detectionService = detectionService;
    }

    @Override
    public void run() {

        System.out.println("📷 Starting Camera...");

        // ✅ Open webcam (0 = default camera)
        VideoCapture camera = new VideoCapture(0);

        if (!camera.isOpened()) {
            System.out.println("❌ Camera not found! Make sure webcam is connected.");
            return;
        }

        System.out.println("✅ Camera opened successfully!");
        System.out.println("👁  Monitoring started — Press Q in the video window to stop.");
        System.out.println("----------------------------------------------------");

        Mat frame = new Mat();

        while (running) {

            // Read frame from camera
            if (!camera.read(frame) || frame.empty()) {
                System.out.println("⚠ Empty frame — skipping...");
                continue;
            }

            // ✅ Process frame — detect faces + draw rectangles
            Mat processedFrame = detectionService.processFrame(frame);

            // ✅ Show live video window
            HighGui.imshow(WINDOW_NAME, processedFrame);

            // ✅ Press 'Q' to quit (waitKey returns ASCII value)
            int key = HighGui.waitKey(30);
            if (key == 'q' || key == 'Q') {
                System.out.println("\n👋 Q pressed — stopping IDS...");
                running = false;
            }
        }

        // ✅ Clean up properly
        camera.release();
        HighGui.destroyAllWindows();

        System.out.println("----------------------------------------------------");
        System.out.println("🛑 Camera stopped.");
        System.out.println("📊 Total Intrusions Detected: "
            + detectionService.getTotalDetections());
        System.out.println("📋 Detection History:");

        detectionService.getHistory().forEach(e ->
            System.out.println("  → " + e.toString())
        );
    }

    // ✅ Stop from outside (optional)
    public void stopMonitor() {
        running = false;
    }
}
