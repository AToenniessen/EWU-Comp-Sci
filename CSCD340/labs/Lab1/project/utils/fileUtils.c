#include "fileUtils.h"
#include <stdio.h>

FILE * openInputFile_Prompt(){
    char fileName[32] = "";
    printf("Enter File Name: ");
    scanf("%s", fileName);
    FILE *fp = fopen(fileName, "r");
    if(fp == NULL) {
        while (fp == NULL) {
            printf("\nFile does not exist.\n\n");
            printf("Enter File Name: ");
            scanf("%s", fileName);
            fp = fopen(fileName, "r");
        }
    }
    return fp;
}//end openInputFile_Prompt

int countRecords(FILE * fin, int linesPer){
    int lines = 0;
    for (char ch = getc(fin); ch != EOF; ch = getc(fin))
        if (ch == '\n')
            lines++;
    rewind(fin);
    return lines/linesPer;
}//end countRecords
