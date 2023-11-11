// arduino code for PIP-E (Piss in Peace - Electronic)
// by: GottaGoPiss
// contains temperature sensor water sensor and an LCD 128x64 screen

#include <qrcode.h>
#include <Wire.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>

#define SCREEN_WIDTH 128 // OLED display width, in pixels
#define SCREEN_HEIGHT 64 // OLED display height, in pixels
#define FSRPIN A0
#define BUTPIN A2

Adafruit_SSD1306 display(SCREEN_WIDTH, SCREEN_HEIGHT, &Wire, -1);

int score = 0;
int dec_acc = 0;
int highest = 0;
int time_before = 40;

QRCode qrcode;

void setup()
{

    Serial.begin(115200);
    pinMode(FSRPIN, INPUT);
    pinMode(BUTPIN, INPUT);

    if (!display.begin(SSD1306_SWITCHCAPVCC, 0x3C))
    { // Address 0x3D for 128x64
        Serial.println(F("SSD1306 allocation failed"));
        for (;;)
            ;
    }

    delay(500);
    display.clearDisplay();
    display.setTextColor(WHITE);
}

void loop()
{

    if (digitalRead(BUTPIN) == HIGH)
    {
        char numArray[20];
        itoa(score, numArray, 10);
        uint8_t qrcodeBytes[qrcode_getBufferSize(3)];
        qrcode_initText(&qrcode, qrcodeBytes, 3, 0, numArray);

        score = 0;
        dec_acc = 0;
        highest = 0;
        time_before = 40;

        // disp qr code
        display.clearDisplay();
        display.setCursor(0, 0);

        Serial.println(numArray);

        for (uint8_t y = 0; y < qrcode.size; y++)
        {
            for (uint8_t x = 0; x < qrcode.size; x++)
            {
                if (qrcode_getModule(&qrcode, x, y))
                {
                    display.fillRect(32 + x * 2, y * 2, 2, 2, WHITE);
                }
            }
        }
        display.display();

        // wait for second press
        while (digitalRead(BUTPIN) == LOW)
        {
          delay(10);
        }
        while (digitalRead(BUTPIN) == HIGH)
        {
          delay(10);
        }
        return;
    }

    int force = analogRead(FSRPIN);
    Serial.println(force);
    if (force > 100)
    {
        score += force / 100;
        time_before = 40;
    }
    else
    {
        time_before -= 1;
    }

    if (time_before == 0)
    {
        time_before = 40;
        score = 0;
    }

    if (score > highest)
        highest = score;

    display.clearDisplay();
    display.setTextSize(2);

    display.setCursor(0, 0);
    display.print("Score:");
    display.setCursor(0, 20);
    display.print(score);
    display.setCursor(0, 40);
    display.print(highest);
    display.display();

    delay(50);
}
