#include "fileUtils.h"
#include <stdio.h>

FILE * openInputFile_Prompt(){
    char fileName[20];
    printf("Enter File Name: ");
    fgets(fileName, 20, stdin);
    FILE *fp = fopen(fileName, "r");
    if(fp == NULL){
        printf("File does not exist.");
        return 0;
    }
    else
        return fp;
}//end openInputFile_Prompt

int countRecords(FILE * fin, int linesPer){
    int lines = 0;
    int record = 0;
    int ch = 0;
    while(!feof(fin)){
        ch = fgetc(fin);
        if(ch == '\n')
            lines++;
        if(lines / linesPer == 0)
            record++;
    }
}//end countRecords
