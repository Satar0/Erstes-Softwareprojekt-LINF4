from operator import add
import socket
import json

initialData,conn,addr = None,None,None

def startConnection():
    #methode zu ersten kontaktaufnahme mit neuverbundenen Clients
    HOST = "127.0.0.1"
    PORT= 65432
    global initialData,conn,addr
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM) 
    s.bind((HOST,PORT))
    print(f"Server is listening on port {PORT} ...")
    s.listen()
    conn,addr = s.accept()
    initialData = conn.recv(1024).decode("utf-8")
    print(initialData)

def addUserinfoToDict():
    #fügt die infos des aktuellen clients der liste aller clients hinzu
    jsonData = json.loads(initialData)
    AllUserInfo = {}
    CurrentUserInfo = {"Username" : jsonData["Username"] , "Password" : jsonData["Password"]}
    AllUserInfo[jsonData["IP"]] = CurrentUserInfo
    print(AllUserInfo)
    conn.sendall((str(AllUserInfo)+ "\n").encode("utf-8") )
    
#print(f"connected with{addr}"
def recieveAndEchoData():
    #endlosschleife die alle empfangenen daten zurückschickt außer end == True, dann wird die verbindung beendet
    while True:
        data = conn.recv(1024).decode("utf-8")
        print(f"s rcvd: {data}")

        jsonData = json.loads(data)
    
        print(f"in JSON:{jsonData}")
        match jsonData["end"]:
            case True:
                print(f"connection with client {addr} ended")
                break
            case False:
                conn.sendall((data+ "\n").encode("utf-8"))


startConnection()
addUserinfoToDict()
recieveAndEchoData()