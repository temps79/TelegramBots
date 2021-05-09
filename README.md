# Time Tabel Bot
# Table of contents
* [Introduction](#introduction)
* [Note](#note)
* [Telegram API support](#telegram-api-support)
* [Installing](#installing)
* [🔨 Getting Started](#getting-started)
* [License](#license)
# Introduction
This library provides a pure Java interface for the [Telegram Bot API][1].
## Note
This bot allows you to share your schedule from Google calendar, and you can also make an appointment through the bot.
## Telegram API support
All types and methods of the Telegram Bot API 5.2 are supported.
## Installing:
* You can install or upgrade python-telegram-bot with:
  
    ```
    git clone https://github.com/temps79/TelegramBots --recursive
    ```
## 🔨Getting Started
* Accepts input args[0]-NameBot, args[1]-TOKEN 
   <details>
      <summary> TelegramBots/src/main/java/TelegramBot/Application.java:</summary>
      
      public static void main(String[] args) {
          ApiContextInitializer.init();
          Bot telegram_bot = new Bot(args[0], args[1]);
          telegram_bot.botConnect();

          MessageReciever messageReciever=new MessageReciever(telegram_bot);
          Thread threadRecievr=new Thread(messageReciever);
          threadRecievr.start();

          MessageSender messageSender=new MessageSender(telegram_bot);
          Thread threadSender=new Thread(messageSender);
          threadSender.start();
      }
  </details>
  
  
* To enable the Google api, you need to log in via Google at the first launch.
* To connect to the DBMS, you need to create "hibernate.cfg.xml" with the database connection settings in the _TelegramBots/src/main/resources directory/_
  <details>
      <summary> TelegramBots/src/main/resources directory/hibernate.cfg.xml:</summary>
      
      <?xml version="1.0" encoding="utf-8" ?>
      <hibernate-configuration >
          <session-factory>
              <property name="hibernate.connection.driver_class">"Driver"</property>
              <property name="hibernate.connection.url">"postgresql://[user[:password]@][netloc][:port][/dbname][?param1=value1&...]"</property>
              <property name="hibernate.connection.username">"username"</property>
              <property name="hibernate.connection.password">"password"</property>
              <property name="hibernate.dialect">org.hibernate.dialect.PostgreSQL9Dialect</property>
              <property name="show_sql">true</property>
          </session-factory>
      </hibernate-configuration>

  </details>
## License

Copyright (c) 2015 velthune

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

  [1]: https://github.com/rubenlagus/TelegramBots "Telegram Bot API"
