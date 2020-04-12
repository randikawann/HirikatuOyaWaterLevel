
#include <SoftwareSerial.h>

SoftwareSerial mySerial(9, 10);

void setup() {
  pinMode(5,OUTPUT);
  pinMode(6,OUTPUT);
  pinMode(10,OUTPUT);
  pinMode(11,OUTPUT);
  Serial.begin(9600);
}
int waterLevel;

void loop() {
  
  waterLevel=analogRead(A0);
  
  Serial.print(" ");
  
  waterLevel=map(waterLevel,0,700,0,12);
  Serial.println(waterLevel);

 if(waterLevel<=4){
    setColor(0,255,0);
    digitalWrite(11,LOW );//green
  }

  if(waterLevel>4 && waterLevel<8){
    setColor(255,50,0);
    digitalWrite(11,LOW);//yellow
  }

  if(waterLevel>=8){
    digitalWrite(11,HIGH);
    setColor(255,0,0);//red
    waterLevel=waterLevel;
  }
  
 /* if(waterLevel<0){
    waterLevel=0;
  } 
    if (waterLevel>2)

    {

      SendMessage();


    }


    if (waterLevel>4)

  {

      DialCall();

  }
}

 void SendMessage(){

  mySerial.println("AT+CMGF=1");    //Sets the GSM Module in Text Mode

  delay(1000);  // Delay of 1000 milli seconds or 1 second

  mySerial.println("AT+CMGS=\"+94715943978\"\r"); // Replace x with mobile number

  delay(1000);

  mySerial.println("Duwapalla..gan watura galano...");// The SMS text you want to send

  delay(100);

  mySerial.println((char)26);// ASCII code of CTRL+Z

  delay(1000);

}

 /*void RecieveMessage()

{

  mySerial.println("AT+CNMI=2,2,0,0,0"); // AT Command to recieve a live SMS

  delay(1000);

}

*/
/*
  void DialCall()

 {

  mySerial.println("ATD+94715943978;"); // ATDxxxxxxxxxx; -- watch out here for semicolon at the end!!

  delay(100);

 }
 */

}

void setColor(int redValue,int greenValue,int blueValue){
    analogWrite(8, redValue);
    analogWrite(9, greenValue);
    analogWrite(10, blueValue);
    
  }

  
