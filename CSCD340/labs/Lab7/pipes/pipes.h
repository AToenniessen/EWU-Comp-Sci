#ifndef PIPES_H
#define PIPES_H

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>

int containsPipe(char *s);
char ** parsePrePipe(char *s, int * preCount);
char ** parseMidPipe(char *s, int * midCount);
char *** parsePipe(char *s, int n, char *** args);
void pipeIt(char *** args, int pipeCount);


#endif 
