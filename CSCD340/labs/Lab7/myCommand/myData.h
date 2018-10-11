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
};

typedef struct my_command MyCommand;

void * buildData(char *command);
void cleanData(void *ptr);

#endif
