//
// Created by Alex
// Code found and utilized from https://vcansimplify.wordpress.com/2013/03/14/c-socket-tutorial-echo-server/
// Original code author is VIGHNESH BIRODKAR
//
#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <strings.h>
#include <afxres.h>
#include <io.h>

int main(){
    char string[100];
    int read_in, write_out;
    struct sockaddr_in server_address;

    read_in = socket(AF_INET, SOCK_STREAM, 0);

    bzero(&server_address, sizeof(server_address));

    server_address.sin_family = AF_INET;
    server_address.sin_addr.s_addr = htons(INADDR_ANY);
    server_address.sin_port = htons(3200);

    bind(read_in, (struct sockaddr *) &server_address, sizeof(server_address));
    listen(read_in, 10);
    write_out = accept(read_in, (struct sockaddr *) NULL, NULL);
    while(1){
        bzero(&string, 100);
        read(read_in, string, 100);
        printf("Echoing back - %s", string);
        write(read_in, string, strlen(string)+1);
    }
}
