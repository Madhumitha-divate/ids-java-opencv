package service;

import config.AppConfig;
import exception.DetectionException;
import model.DetectionEvent;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import util.DateTimeUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DetectionService {

    private CascadeClassifier faceClassifier;
    private NotificationService notificationService;
    private List<DetectionEvent> history;

    // ✅ Cooldown — track last alert time
    private long lastAlertTime = 0;
    private int totalDetections = 0;

    public DetectionService() throws DetectionException {

        notificationService = new NotificationService();
        history = new ArrayList<>();

        // ✅ Create snapshots folder
        new File("snapshots").mkdirs();

        // ✅ Load face cascade
        faceClassifier = new CascadeClassifier(AppConfig.CASCADE_PATH);

        if (faceClassifier.empty()) {
            throw new DetectionException(
                "❌ Haarcascade XML not found at: " + AppConfig.CASCADE_PATH +
                "\n👉 Make sure haarcascade_frontalface_default.xml is in /src/ folder"
            );
        }

        System.out.println("✅ Face detection model loaded successfully!");
    }

    // ✅ Main method — called for every camera frame
    // Returns the frame with rectangles drawn (for display)
    public Mat processFrame(Mat frame) {

        if (faceClassifier.empty() || frame.empty()) {
            return frame;
        }

        // Convert to grayscale for detection
        Mat gray = new Mat();
        Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(gray, gray); // improve detection in low light

        // Detect faces
        MatOfRect faces = new MatOfRect();
        faceClassifier.detectMultiScale(
            gray,
            faces,
            1.1,   // scaleFactor
            4,     // minNeighbors (higher = fewer false positives)
            0,
            new Size(60, 60),  // minSize
            new Size(400, 400) // maxSize
        );

        Rect[] faceArray = faces.toArray();
        int faceCount = faceArray.length;

        // ✅ Draw green rectangle around each detected face
        for (Rect face : faceArray) {
            Imgproc.rectangle(
                frame,
                new Point(face.x, face.y),
                new Point(face.x + face.width, face.y + face.height),
                new Scalar(0, 255, 0), // green color
                2                      // thickness
            );

            // ✅ Label above the rectangle
            Imgproc.putText(
                frame,
                "FACE DETECTED",
                new Point(face.x, face.y - 10),
                Imgproc.FONT_HERSHEY_SIMPLEX,
                0.7,
                new Scalar(0, 255, 0),
                2
            );
        }

        // ✅ Show face count on screen
        String statusText = faceCount > 0
            ? "⚠ INTRUDER! Faces: " + faceCount
            : "✅ Monitoring...";

        Imgproc.putText(
            frame,
            statusText,
            new Point(10, 30),
            Imgproc.FONT_HERSHEY_SIMPLEX,
            0.8,
            faceCount > 0 ? new Scalar(0, 0, 255) : new Scalar(0, 255, 0),
            2
        );

        // ✅ Show timestamp on screen
        Imgproc.putText(
            frame,
            DateTimeUtil.getCurrentTime(),
            new Point(10, frame.rows() - 10),
            Imgproc.FONT_HERSHEY_SIMPLEX,
            0.5,
            new Scalar(255, 255, 255),
            1
        );

        // ✅ Face detected — trigger alert with cooldown
        if (faceCount > 0) {
            long now = System.currentTimeMillis();
            long cooldownMs = AppConfig.ALERT_COOLDOWN_SECONDS * 1000L;

            if (now - lastAlertTime >= cooldownMs) {
                lastAlertTime = now;
                totalDetections++;
                System.out.println("🔥 Face(s) detected! Count: " + faceCount);
                handleDetection(frame, faceCount);
            }
        }

        return frame; // return frame with drawings
    }

    // ✅ Save snapshot + send Telegram alert
    private void handleDetection(Mat frame, int faceCount) {
        try {
            String time      = DateTimeUtil.getCurrentTime();
            String timestamp = DateTimeUtil.getFileTimestamp();
            String filePath  = "snapshots/intrusion_" + timestamp + ".jpg";

            // Save snapshot
            Imgcodecs.imwrite(filePath, frame);
            System.out.println("📸 Snapshot saved: " + filePath);

            // Create event and store in history
            DetectionEvent event = new DetectionEvent(time, filePath, faceCount);
            history.add(event);

            // Send Telegram notification
            notificationService.sendAlert(event);

            System.out.println("🚨 Intrusion #" + totalDetections + " at " + time);

        } catch (Exception e) {
            System.out.println("❌ Error handling detection: " + e.getMessage());
        }
    }

    // ✅ Get full detection history
    public List<DetectionEvent> getHistory() {
        return history;
    }

    // ✅ Get total detections count
    public int getTotalDetections() {
        return totalDetections;
    }
}
