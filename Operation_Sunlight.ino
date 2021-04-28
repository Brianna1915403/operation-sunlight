#include "DHT.h"
#include <Wire.h>
#include <SoftwareSerial.h>

#define DHT11_PIN 7
#define DHTTYPE DHT11

DHT dht(DHT11_PIN, DHTTYPE);

unsigned int lightSensorValue = 0;

SoftwareSerial EEBlue(10, 11); 

float temperature = 0;
float humidity = 0;

int moisture = 0;

DS3231  rtc(SDA, SCL);

void setup() {
  Serial.begin(9600);
  EEBlue.begin(38400);
  pinMode(13, OUTPUT);
  pinMode(10, OUTPUT);
  dht.begin();
  rtc.begin();
  //synchronizeClock(15, 34, 45, 21, 4, 2021);
}

void getSoilMoisture(){
  moisture = analogRead(A0);
}

void printSoilMoisture(){
  Serial.print(F("Soil Moisture: "));
  Serial.println(moisture);
}

void getLightLevel(){
  lightSensorValue = analogRead(A1);
}

void printLightLevel(){
  Serial.print(F("Light Level: "));
  Serial.println(lightSensorValue);
}

void getTempHum(){
  temperature = dht.readTemperature();
  humidity = dht.readHumidity();
}

void printTempHum(){
  Serial.print(F("Humidity: "));
  Serial.print(humidity);
  Serial.print(F("%  Temperature: "));
  Serial.print(temperature);
  Serial.println(F("°C "));
}

void loop() {
  getTempHum();
  getLightLevel();
  getSoilMoisture();
  printTempHum();
  printLightLevel();
  printSoilMoisture();
  printTime();
  delay(1000);
  

}
