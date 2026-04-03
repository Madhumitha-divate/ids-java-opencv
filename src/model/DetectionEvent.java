package model;

public class DetectionEvent {

    private String time;
    private String imagePath;
    private int faceCount;

    public DetectionEvent(String time, String imagePath, int faceCount) {
        this.time      = time;
        this.imagePath = imagePath;
        this.faceCount = faceCount;
    }

    public String getTime()      { return time; }
    public String getImagePath() { return imagePath; }
    public int getFaceCount()    { return faceCount; }

    @Override
    public String toString() {
        return "DetectionEvent[time=" + time +
               ", faces=" + faceCount +
               ", saved=" + imagePath + "]";
    }
}
