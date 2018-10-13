#include <stdio.h>
#include "./pipes/pipes.h"
#include "./utils/myUtils.h"
#include "./process/process.h"
#include "./tokenize/makeArgs.h"
#include "./linkedlist/linkedList.h"
#include "./linkedlist/listUtils.h"
#include "./myData/myData.h"
#include "./Commands/commands.h"

int main()
{
  int argc, pipeCount, histCount = 0, histFileCount;
  char *startDir = (char *)calloc(4096, sizeof(char)), *PATH = getenv("PATH");
  getcwd(startDir, 4096);
  char **argv = NULL, ***args = NULL, curcmd[MAX], lstcmd[MAX];
  LinkedList * history = linkedList();

  printf("command?: ");
  fgets(curcmd, MAX, stdin);
  strip(curcmd);

	while(strcmp(curcmd, "exit") != 0)
  {
  	if(strcmp(curcmd, "!!") != 0 && strcmp(curcmd, lstcmd) != 0)
  		addFirst(history, buildNode(++histCount, curcmd, buildData));
  	pipeCount = containsPipe(curcmd);
	if(pipeCount > 0)
	{
		args = (char ***) calloc(pipeCount + 2, sizeof(char **));
		//pipeIt(PATH, , pipeCount);
		parsePipe(curcmd, 0, args);
		for(int i = 0; i <= pipeCount; i++){
			free(args[i]);
			args[i] = NULL;
		}
		free(args);
		args = NULL;
	}// end if pipeCount	  

	else
	{
        argc = makeargs(curcmd, &argv);
        char *save;
        if(strcmp(argv[0], "history") == 0){
	        printList(history, printData);
        }
        else if(strcmp(argv[0], "!!") == 0){
        	clean(argc, argv);
			argc = executeHistory(history, PATH, &argv);
		}
        else if(strcmp(argv[0], "cd") == 0){
        	if(executeCD(argv))
        		perror("");
        }
        else if(strcmp(strtok_r(curcmd, "=", &save), "PATH") == 0){
			modifyPath(&PATH, save);
			printf("%s", PATH);
        }
        else if(argc != -1) {
	  		forkIt(PATH, argv);
		}

	  	clean(argc, argv);
	  	argv = NULL;
	}

	strcpy(lstcmd, curcmd);
	printf("command?: ");
	fgets(curcmd, MAX, stdin);
	strip(curcmd);

  }// end while
  free(startDir);
  startDir = NULL;
  clearList(history, cleanData);
  free(history->head);
  free(history);
  history = NULL;
//  free(PATH);
//  PATH = NULL;

  return 0;

}// end main


