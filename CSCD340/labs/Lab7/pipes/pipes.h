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

int *parsePipe(char *s, int n, char ***args);

void pipeIt(char *file, char ***args, int pipeCount);


#endif 
