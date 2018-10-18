#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>
#include "../pipes/pipes.h"
#include "../tokenize/makeArgs.h"
#include "../linkedlist/linkedList.h"
#include "../linkedlist/listUtils.h"


int executeCD(char **argv);

char *executeAlias(LinkedList *alias, char *curcmd, char **argv);

void executePipe(int pipeCount, char *curcmd, char *PATH);

void addCommand(LinkedList *history, char *lstcmd, char *curcmd);

void modifyPath(char **PATH, char *s);

