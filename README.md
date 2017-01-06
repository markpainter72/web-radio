##Usage:
  
Install mpg123:  
  
>sudo apt-get install mpg123  

Download the radio.jar  
  
Create a file "radio.config" for the stream urls and place it next to the binary/in the project root   
The location of this file can also be specified by starting the program with the following parameter:  
java -jar radio.jar [path to config]  

Insert the stream urls of the radio stations in the following format:  
  
'[station name]~[url]' - seperated by a newline  
  
##Screenshot:
![Screenshot](/radio.png?raw=true)