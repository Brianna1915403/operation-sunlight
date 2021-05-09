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

String msg;

const int ledPin = 13;

void setup() {
  Serial.begin(9600);
  EEBlue.begin(9600);
  pinMode(13, OUTPUT);
  dht.begin();
  msg = "";
  //synchronizeClock(15, 34, 45, 21, 4, 2021);
}

void getSoilMoisture(){
  moisture = analogRead(A0);
}

void printSoilMoisture(){
  Serial.print(F("Soil/"));
  Serial.print(moisture);
}

void getLightLevel(){
  lightSensorValue = analogRead(A1);
}

void printLightLevel(){
  Serial.print(F("Light/"));
  Serial.print(lightSensorValue);
  Serial.print(F("/"));
}

void getTempHum(){
  temperature = dht.readTemperature();
  humidity = dht.readHumidity();
}

void printTempHum(){
  Serial.print(F("Humidity/"));
  Serial.print(humidity);
  Serial.print(F("/"));
  Serial.print(F("Temperature/"));
  Serial.print(temperature);
  Serial.print(F("/"));
}

void loop() {
      
  getTempHum();
  getLightLevel();
  getSoilMoisture();
  printTempHum();
  printLightLevel();
  printSoilMoisture();
  Serial.println(F("\n"));
  
  
     
      // Feed all data from termial to bluetooth
  if (Serial.available())
      EEBlue.write(Serial.read());
      
  serialFlush();
  delay(5000);
  

}

void serialFlush(){
  while(Serial.available() > 0) {
    char t = Serial.read();
  }
}
