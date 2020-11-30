## About application 
This is my first client-server application written in java. It looks simple enough, but tastefully, tried to adhere to the concept of minimalism. The application consists of two parts: server and client. Each of the programs is launched separately.
## How to use
#### Server
First of all, the server is started so that the client has something to connect to. This can be done from the folder ```Server``` using the command: 
> ```java -jar target/jfx/app/Server-1.0-jfx.jar```
#### Client
The client program runs from the folder ```Client```.
> ```java -jar target/jfx/app/Client-1.0-jfx.jar```

After starting, a window will open in which you will need to specify the IP address in the local network of the device
 on which the server of this application is running. It looks something like this:
![XhTiUxPwgF8](https://user-images.githubusercontent.com/61560369/100616280-e98deb80-3329-11eb-8008-b4530bc5f960.jpg)

If you run the server and client on the same device, then enter "localhost" in the IP field. But if the server is running on another device, inside the local network, then you need to specify the ip address of this computer. on macOS, you can find out the local ip using the command `ipconfig getifaddr en0`

## Game
The game will start only after both clients connect to the server. Three actions will be available to you during the game: 
* move to the right (Right arrow)
* move to the left (Left arrow)
* shoot (SPACE)

This is what the game looks like:
![a2c8ljLhJt8](https://user-images.githubusercontent.com/61560369/100617926-31ae0d80-332c-11eb-998e-a31fd6243cec.jpg)

Each player has 100 hp. Each hit takes 5 hp.

#### Good luck with the game!
