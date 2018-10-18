/**
 * @file myInt.h
 * A simple integer structure that holds a single int type called value
 * generic fashion
 */

#include<stdio.h>
#include<stdlib.h>

struct my_command {
	char *command;
	char *alias;
};

typedef struct my_command MyCommand;

void *buildCommand(char *alias, char *command);

char *accessCommand(void *ptr);

char *accessAlias(void *ptr);

void printData(void *ptr);

void cleanData(void *ptr);

void writeData(FILE *fin, void *ptr);

void *readData(FILE *fin);

int compareAlias(const void *n1, const void *n2);

int compareCommand(const void *n1, const void *n2);

int compare(const void *n1, const void *n2);

