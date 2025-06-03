# 🌦️ Java Weather App

A simple and elegant Weather App built using **Java Swing GUI** that fetches real-time weather data from the **OpenWeatherMap API**. This app displays temperature, humidity, pressure, feels-like temperature, and a weather emoji based on the condition — all in a user-friendly interface.

---

## 🖥 Features

- 🌤️ Weather emoji based on current conditions
- 🌡️ Temperature and "Feels Like" display
- 💧 Humidity and Atmospheric Pressure
- 💨 Wind Speed & Visibility
- 📍 City-based search
- 🧾 Save weather data to `weather.txt` (optional)
- ✨ Simple and clean Java Swing interface (no external images used)

---

## 🔧 Requirements

- Java JDK 8 or later
- Internet connection
- OpenWeatherMap API key

---

## 📦 Setup Instructions

1. **Clone or download the project:**

   ```bash
   git clone https://github.com/yourusername/java-weather-app.git
   ```

2. **Add your OpenWeatherMap API key:**

   Open the `WeatherApp.java` file and replace:

   ```java
   private final String API_KEY = "YOUR_API_KEY_HERE";
   ```

   with your actual API key from [https://openweathermap.org/api](https://openweathermap.org/api).

3. **Compile and Run:**

   ```bash
   javac WeatherApp.java
   java WeatherApp
   ```

---

## 🧠 How It Works

- Sends an HTTP GET request to the OpenWeatherMap API with the city name.
- Parses the JSON response using string manipulation (no external JSON library).
- Displays key weather data in a styled text area using Java Swing.
- Emojis are used instead of icons to visually represent weather conditions.

---

## 🚫 No External Dependencies

- No JSON or external image libraries used.
- Works purely with core Java libraries.

---

## 📄 License

This project is open-source and free to use under the [MIT License](https://opensource.org/licenses/MIT).

---

## 💬 Author

Developed with ☕ and 🌦️ by [Priyansu Sikdar]

