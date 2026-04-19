# 🔒 Intrusion Detection System (IDS) — Java + OpenCV + Telegram

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![OpenCV](https://img.shields.io/badge/OpenCV-27338e?style=for-the-badge&logo=OpenCV&logoColor=white)
![Telegram](https://img.shields.io/badge/Telegram-2CA5E0?style=for-the-badge&logo=telegram&logoColor=white)
![Status](https://img.shields.io/badge/Status-Active-brightgreen?style=for-the-badge)

---

## 📌 About The Project

A **real-time Intrusion Detection System** built in Java using OpenCV for face detection and Telegram Bot API for instant mobile alerts.

When a face is detected via webcam:
1. 🟢 A **green rectangle** is drawn around the detected face
2. 📸 A **snapshot is saved** automatically
3. 📱 A **Telegram alert** is sent to your phone instantly

> 💡 Originally built in Python using Flask + Twilio. Rebuilt in Java with OpenCV + Telegram for deeper backend understanding.

---

## ✨ Features

| Feature | Description |
|---|---|
| ✅ Live Webcam Feed | Real-time video window with face detection overlay |
| ✅ Face Detection | Haar Cascade Classifier (frontal face) |
| ✅ Rectangle Drawing | Green box drawn around every detected face |
| ✅ Snapshot Saving | Auto-saves intrusion images to /snapshots/ folder |
| ✅ Telegram Alert | Instant mobile notification with time + face count |
| ✅ Alert Cooldown | Prevents spam — alerts once every 10 seconds |
| ✅ Detection History | Logs all intrusion events in memory |
| ✅ Clean Exit | Press Q to stop system gracefully |
| ✅ Thread-based | Camera runs on separate thread using Java Thread |

---

## 🗂️ Project Structure

```
IDS-Project/
├── src/
│   ├── main/
│   │   └── IDSApplication.java       → Entry point
│   ├── controller/
│   │   └── IDSController.java        → System controller
│   ├── monitor/
│   │   └── CameraMonitor.java        → Webcam thread + display
│   ├── service/
│   │   ├── DetectionService.java     → Face detection + alert trigger
│   │   └── NotificationService.java  → Telegram bot notification
│   ├── model/
│   │   └── DetectionEvent.java       → Intrusion event data model
│   ├── config/
│   │   └── AppConfig.java            → Bot token + settings
│   ├── util/
│   │   └── DateTimeUtil.java         → Timestamp formatting
│   ├── exception/
│   │   └── DetectionException.java   → Custom exception
│   └── haarcascade_frontalface_default.xml
├── snapshots/                         → Auto-created, stores captured images
└── README.md
```

---

## 🛠️ Tech Stack

- **Language:** Java (Core Java, Java 8+, Multithreading)
- **Computer Vision:** OpenCV 4.x (Java bindings)
- **Notification:** Telegram Bot API
- **Algorithm:** Haar Cascade Classifier
- **IDE:** Eclipse

---

## ⚙️ Setup & Installation

### Prerequisites
- Java JDK 8 or above
- OpenCV 4.x JAR + native library (`.dll` on Windows)
- Eclipse IDE
- A Telegram account

---

### Step 1 — Clone the Repository
```bash
git clone https://github.com/Madhumitha-divate/ids-java-opencv.git
```

### Step 2 — Add OpenCV to Eclipse
1. Right click project → **Build Path** → **Configure Build Path**
2. **Libraries** → **Add External JARs** → select `opencv-4xx.jar`
3. Expand the JAR → **Native library location** → add folder with `opencv_java4xx.dll`

### Step 3 — Download Haarcascade XML
Download `haarcascade_frontalface_default.xml` from:
```
https://github.com/opencv/opencv/tree/master/data/haarcascades
```
Place it in the `/src/` folder of your project.

### Step 4 — Create Telegram Bot
1. Open Telegram → search **@BotFather**
2. Type `/newbot` → follow instructions → copy your **Bot Token**
3. Message your bot once
4. Open in browser:
```
https://api.telegram.org/bot<YOUR_BOT_TOKEN>/getUpdates
```
5. Find `"chat" → "id"` — that's your **Chat ID**

### Step 5 — Configure AppConfig.java
```java
public static final String BOT_TOKEN = "your_bot_token_here";
public static final String CHAT_ID   = "your_chat_id_here";
```

### Step 6 — Run
Right click `IDSApplication.java` → **Run As** → **Java Application**

---

## 📱 Sample Telegram Alert

```
🚨 INTRUDER ALERT!
📅 Time     : 2026-03-31 14:32:05
👤 Faces    : 1 detected
📸 Snapshot : snapshots/intrusion_20260331_143205.jpg
🔴 Please check your premises immediately!
```

---

## 📸 How It Works

```
Webcam Frame
     ↓
Convert to Grayscale
     ↓
Haar Cascade detectMultiScale()
     ↓
Face Found?
  YES → Draw Rectangle → Save Snapshot → Send Telegram Alert
  NO  → Show "Monitoring..." status
     ↓
Display Live Window
     ↓
Press Q → Stop Cleanly
```

---

## 🔑 Key Concepts Demonstrated

- **OpenCV Java Integration** — VideoCapture, CascadeClassifier, HighGui
- **Multithreading** — CameraMonitor extends Thread
- **Haar Cascade Algorithm** — real-time object detection
- **Telegram Bot API** — HTTP GET request for notifications
- **Custom Exception Handling** — DetectionException
- **MVC Architecture** — controller, service, model, monitor separation
- **Cooldown Mechanism** — prevents notification spam

---

## 🚀 Future Improvements

- [ ] Face Recognition (identify known vs unknown faces)
- [ ] Email alert with snapshot attachment
- [ ] Web dashboard to view detection history
- [ ] Multiple camera support
- [ ] Save detection logs to MySQL database
- [ ] Spring Boot REST API version

---

## 👩‍💻 Author

**Madhumitha Divate**
- 🔗 [LinkedIn](https://linkedin.com/in/madhumitha-divate)
- 💻 [GitHub](https://github.com/Madhumitha-divate)
- 📧 madhudivate003@gmail.com

---

## ⭐ If you found this project helpful, please give it a star!
