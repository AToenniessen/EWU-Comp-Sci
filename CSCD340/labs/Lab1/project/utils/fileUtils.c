#include "fileUtils.h"
#include <stdio.h>

FILE * openInputFile_Prompt(){
    char fileName[100] = "";
    printf("Enter Full File Name: ");
    scanf("%s", fileName);
    FILE *fp = fopen(fileName, "r");
    if(fp == NULL) {
        while (fp == NULL) {
            printf("\nFile does not exist.\n\n");
            printf("Enter Full File Name(example.txt): ");
            scanf("%s", fileName);
            fp = fopen(fileName, "r");
        }
    }
    return fp;
}//end openInputFile_Prompt

int countRecords(FILE * fin, int linesPer){
    if(fin == NULL || linesPer < 1){
        perror("Parameters passed in are incorrect");
        exit(-99);
    }
    int lines = 0;
    char temp [100];
    while(!feof(fin)){
        lines++;
        fgets(temp, 100, fin);
    }
    if(lines == 0){
        perror("file is empty");
        exit(-99);
    }
    else if (lines % linesPer != 0){
        perror("file is improperly formatted");
        exit(-99);
    }
    rewind(fin);
    return lines/linesPer;
}//end countRecords
