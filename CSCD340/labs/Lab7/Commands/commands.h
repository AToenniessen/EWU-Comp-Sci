#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>
#include "../linkedlist/linkedList.h"
#include "../myData/myData.h"
#include "../tokenize/makeArgs.h"
#include "../process/process.h"


int executeCD(char ** argv);