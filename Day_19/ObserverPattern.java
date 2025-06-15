import java.util.ArrayList;
import java.util.List;

// 1. Observer Interface
interface WeatherDisplay {
    void update(float temperature, float humidity, float pressure);
}

// 2. Subject Interface (Optional, but good practice for clarity)
interface WeatherStationSubject {
    void addObserver(WeatherDisplay display);
    void removeObserver(WeatherDisplay display);
    void notifyObservers();
}

// 3. Concrete Subject
class WeatherStation implements WeatherStationSubject {
    private List<WeatherDisplay> observers;
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherStation() {
        observers = new ArrayList<>();
    }

    @Override
    public void addObserver(WeatherDisplay display) {
        observers.add(display);
    }

    @Override
    public void removeObserver(WeatherDisplay display) {
        observers.remove(display);
    }

    @Override
    public void notifyObservers() {
        for (WeatherDisplay display : observers) {
            display.update(temperature, humidity, pressure);
        }
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }

    public void measurementsChanged() {
        notifyObservers();
    }
}

// 4. Concrete Observers
class CurrentConditionsDisplay implements WeatherDisplay {
    private float temperature;
    private float humidity;

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }

    public void display() {
        System.out.println("Current conditions: " + temperature + "F degrees and " + humidity + "% humidity");
    }
}

class StatisticsDisplay implements WeatherDisplay {
    private float maxTemp = 0.0f;
    private float minTemp = 200.0f;
    private float tempSum = 0.0f;
    private int numReadings;

    @Override
    public void update(float temperature, float humidity, float pressure) {
        tempSum += temperature;
        numReadings++;

        if (temperature > maxTemp) {
            maxTemp = temperature;
        }

        if (temperature < minTemp) {
            minTemp = temperature;
        }
        display();
    }

    public void display() {
        System.out.println("Avg/Max/Min temperature = " + (tempSum / numReadings)
                + "/" + maxTemp + "/" + minTemp);
    }
}

// Client Code
public class ObserverPattern {
    public static void main(String[] args) {
        WeatherStation weatherStation = new WeatherStation();

        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay();
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay();

        weatherStation.addObserver(currentDisplay);
        weatherStation.addObserver(statisticsDisplay);

        weatherStation.setMeasurements(80, 65, 30.4f);
        System.out.println("--- Second Measurement ---");
        weatherStation.setMeasurements(82, 70, 29.2f);
        System.out.println("--- Third Measurement ---");
        weatherStation.removeObserver(currentDisplay); // Remove one observer
        weatherStation.setMeasurements(78, 90, 29.2f);
    }
}