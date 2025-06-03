import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class WeatherApp extends JFrame {
    private JTextField cityField;
    private JButton getWeatherButton;
    private JPanel resultPanel;
    private JLabel iconLabel;
    private JTextArea weatherDetails;

    private final String API_KEY = "ff366336038b639379de62cfcd619a17"; // Replace with your OpenWeatherMap API Key

    public WeatherApp() {
        setTitle("Weather App");
        setSize(550, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        cityField = new JTextField(15);
        getWeatherButton = new JButton("Get Weather");
        inputPanel.add(new JLabel("Enter City:"));
        inputPanel.add(cityField);
        inputPanel.add(getWeatherButton);
        add(inputPanel, BorderLayout.NORTH);

        // Result display panel
        resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Weather Info"));

        iconLabel = new JLabel("", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        resultPanel.add(iconLabel, BorderLayout.NORTH);

        weatherDetails = new JTextArea();
        weatherDetails.setFont(new Font("Monospaced", Font.PLAIN, 14));
        weatherDetails.setEditable(false);
        weatherDetails.setOpaque(false);
        resultPanel.add(weatherDetails, BorderLayout.CENTER);

        add(resultPanel, BorderLayout.CENTER);

        // Button action
        getWeatherButton.addActionListener(e -> {
            String city = cityField.getText().trim();
            if (!city.isEmpty()) {
                fetchWeather(city);
            } else {
                JOptionPane.showMessageDialog(this, "City name cannot be empty!");
            }
        });

        setVisible(true);
    }

    private void fetchWeather(String city) {
        try {
            String urlStr = String.format(
                    "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric",
                    URLEncoder.encode(city, "UTF-8"), API_KEY);

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                json.append(line);
            }
            in.close();

            String response = json.toString();

            // Extract data
            String description = extractValue(response, "\"description\":\"", "\"");
            String temp = extractValue(response, "\"temp\":", ",");
            String feelsLike = extractValue(response, "\"feels_like\":", ",");
            String tempMin = extractValue(response, "\"temp_min\":", ",");
            String tempMax = extractValue(response, "\"temp_max\":", ",");
            String humidity = extractValue(response, "\"humidity\":", ",");
            String pressure = extractValue(response, "\"pressure\":", ",");
            String visibility = extractValue(response, "\"visibility\":", ",");
            String windSpeed = extractValue(response, "\"speed\":", ",");
            String windDeg = extractValue(response, "\"deg\":", ",");
            String conditionId = extractValue(response, "\"id\":", ",");
            String country = extractValue(response, "\"country\":\"", "\"");
            String cityName = extractValue(response, "\"name\":\"", "\"");

            String emoji = mapWeatherToEmoji(description.toLowerCase());

            iconLabel.setText(emoji);

            String output = String.format(
                    "%s, %s\n\n" +
                            "Weather       : %s\n" +
                            "Condition ID  : %s\n" +
                            "Temperature   : %s °C\n" +
                            "Feels Like    : %s °C\n" +
                            "Min Temp      : %s °C\n" +
                            "Max Temp      : %s °C\n" +
                            "Humidity      : %s %%\n" +
                            "Pressure      : %s hPa\n" +
                            "Visibility    : %s m\n" +
                            "Wind Speed    : %s m/s\n" +
                            "Wind Direction: %s°",
                    cityName, country, capitalize(description), conditionId, temp, feelsLike, tempMin, tempMax,
                    humidity, pressure, visibility, windSpeed, windDeg
            );

            weatherDetails.setText(output);

        } catch (Exception e) {
            weatherDetails.setText("Error fetching weather data.\n" + e.getMessage());
            iconLabel.setText("⚠️");
        }
    }

    private String mapWeatherToEmoji(String desc) {
        if (desc.contains("clear")) return "☀️";
        if (desc.contains("cloud")) return "☁️";
        if (desc.contains("rain")) return "🌧️";
        if (desc.contains("storm") || desc.contains("thunder")) return "⛈️";
        if (desc.contains("snow")) return "❄️";
        if (desc.contains("wind")) return "💨";
        if (desc.contains("mist") || desc.contains("fog")) return "🌫️";
        return "🌡️";
    }

    private String extractValue(String json, String key, String endChar) {
        int start = json.indexOf(key);
        if (start == -1) return "N/A";
        start += key.length();
        int end = json.indexOf(endChar, start);
        if (end == -1) return json.substring(start).trim();
        return json.substring(start, end).trim();
    }

    private String capitalize(String text) {
        if (text == null || text.isEmpty()) return text;
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WeatherApp::new);
    }
}
