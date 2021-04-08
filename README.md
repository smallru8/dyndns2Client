# ![](https://i.imgur.com/EzuQV9b.png)yndns2Client

Update your ip to DNS which supports dyndns2 protocol.


## Description
Dyndns2Client is a cross-platform software. You can install it on Linux, macOS, and Windows.

On Windows
![](https://i.imgur.com/oXspE0e.png)
After minimize window
![](https://i.imgur.com/uHpGCX0.png)
It would run in the background. Click tray icon to open control window.

## Installation
Require Java 8 or later version.
Download Dyndns2Client and configure ```conf.d/DD.conf```

### DD.conf
```
#protocol
poto=dyndns2
#Service provider
server=dyndns.com
#Use SSL
ssl=yes
#Account
username=
#Password
passwd=
#Your domain
domain=yourdomain.dd.com
#Start gui, if you want to use CLI mode turn this option to no
gui=yes
checkip=https://checkip.amazonaws.com/
#every 60 sec check ip
time=60
```
Start service: ```java -jar Dyndns2Client.jar```
