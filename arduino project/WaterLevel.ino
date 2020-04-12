#include <LiquidCrystal.h>

LiquidCrystal lcd(12,11,5,4,3,2);

void setup() {
  lcd.begin(16,2);
  lcd.setCursor(0,0);
  Serial.begin(9600);
}
int waterLevel;

void loop() {
  lcd.setCursor(0,0);
  
  waterLevel=analogRead(A0);
  Serial.println(waterLevel);
  //Serial.print(" ");
  
  waterLevel=map(waterLevel,470,630,0,5);
  if(waterLevel<0){
    waterLevel=0;
  }
  //Serial.println(waterLevel);
  lcd.print("Water Level=");
  lcd.print(waterLevel);
  lcd.print("cm");
  delay(100);
  lcd.clear();

  lcd.setCursor(4,2);
  lcd.print("CIS SUSL");
  /*for(int i=0;i<11;i++){
    lcd.setCursor(i,0);
    lcd.print("Nayana");
    delay(200);
    lcd.clear();
  }
  for(int i=11;i>0;i--){
    lcd.setCursor(i,0);
    lcd.print("Nayana");
    delay(200);
    lcd.clear();
  }*/
}



