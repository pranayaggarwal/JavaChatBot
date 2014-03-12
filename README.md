MultiUserChat
=============

A multi user chat application in java with public chating, private chating, group chating options

### Welcome to My GitHub Page of Java GUI Chat Client Project.
This java Chat client shows the layman way to develop a chat application in java with public chating, private chating, group chating options options.


To run Chat Client in terminal, browse to src folder and type the following or run project through IDE-
```
javac ChatServer.java 
java ChatServer
```


*  Enter port number in input dialog for eg: 9000
*  Maximum 3 connections accepted which is a pre-defined number


To run Chat Client in terminal, browse to src folder and type the following or run project through IDE-
```
javac ChatClient.java 
java ChatClient
```


*  Enter ip address of server, port number on which connection is opened.
*  Enter your name in name input dialog.
*  Type your messages and enter **Send** button.
*  By default sent messages will be **broadcasted**
*  To send private messages, type - `@<receievername> <message>`
*  To send private messages to many users, type - `@<receievername1> <receivername2> ....<receivernameN>#<message>`
*  To see list of online users - Press **List of all Users** button
*  To quit chat room - type **QUIT**

This is what it looks like :)
![You may open different clients by running different instances of Class](https://raw.github.com/pranayaggarwal25/projectImages/master/Screenshot.png)



### Client Connection Limit
Maximum number of chat clients is a hard-coded value (currently 3) in ChatServer. After that it doesn't create any connection queue, it simply keeps on rejecting all incoming requests till total number of connections are full.


### Author
Hi All, Myself Pranay Kumar Agrawal (IIT Roorkee CSE '13). This was my first project over Github and first assessment in Java :) Apologies if any trouble arises up with the code.


### Support or Contact
Having trouble with Pages? Check out the documentation at http://help.github.com/pages or contact support@github.com and we’ll help you sort it out.
