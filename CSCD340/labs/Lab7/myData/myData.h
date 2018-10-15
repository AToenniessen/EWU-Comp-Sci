/**
 * @file myInt.h
 * A simple integer structure that holds a single int type called value
 * generic fashion
 */

#ifndef MYINT_H
#define MYINT_H

#include<stdio.h>
#include<stdlib.h>

struct my_command
{
	char * value;
	int number;
};

typedef struct my_command MyCommand;

void * buildData(int n, char *command);
char * accessVal(void *ptr);
int accessNum(void *ptr);
void printData(void * ptr);
void cleanData(void *ptr);
void writeData(FILE * fin, void *ptr);
void *readData(FILE * fin);

#endif
