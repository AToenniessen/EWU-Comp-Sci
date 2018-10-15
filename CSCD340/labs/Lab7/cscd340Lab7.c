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
  int argc = 0, pipeCount = 0, histCount = 100, histFileCount = 1000;
  char *startDir = (char *)calloc(4096, sizeof(char)), *PATH = getenv("PATH"), init[4096];
  getcwd(startDir, 4096);
	LinkedList * history = readList(openFin(startDir, "/.ussh_history", "r"));
	char **argv = NULL, ***args = NULL, curcmd[MAX] = "", lstcmd[MAX] = "";
	if(history->head->next != NULL)
		strcpy(lstcmd, accessVal(history->head->next->data));

    printf("command?: ");
  fgets(curcmd, MAX, stdin);
  strip(curcmd);

	while(strcmp(curcmd, "exit") != 0)
  {
	  pipeCount = containsPipe(curcmd);
	  if(pipeCount > 0)
	  {
	  	argc = pipeCount + 1;
	  	    args = (char ***)calloc(pipeCount + 2, sizeof(char**));
	  	    parsePipe(curcmd, pipeCount, args);
			pipeIt(PATH, args, pipeCount);
			for(int i = 0; i <= pipeCount + 1; i++){
				free(args[i]);
			}
			free(args);
			args = NULL;
	  }// end if pipeCount
			else
		{
			argc = makeargs(curcmd, &argv);

	        char *save;
	        if(strcmp(argv[0], "history") == 0){
		        printList(history, printData, histCount);
	        }
	        else if(strcmp(argv[0], "!!") == 0){
				strcpy(curcmd, lstcmd);
				if(strcmp(curcmd, "history") == 0)
					printList(history, printData, histCount);
				else {
					clean(argc, argv);
					argc = makeargs(curcmd, &argv);
					forkIt(PATH, argv);
				}
	        }
	        else if(argv[0][0] == '!'){     //figure out why it randomly picks a command to execute
		        char s[1000];
		        strcpy(s,argv[0]+1);
		        clean(argc, argv);
		        int n = (int)strtol(s, (char **)NULL, 10);
		        if((argc = executeHistory(n, history, PATH, &argv, histCount)) != 0)
		            strcpy(curcmd, argv[0]);
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
			if(argc !=0) {
				clean(argc, argv);
				argv = NULL;
			}
		}

		if(strcmp(curcmd, lstcmd) != 0 && argc != 0)
			addFirst(history, buildNode(history->size + 1, curcmd, buildData));

		strcpy(lstcmd, curcmd);
		printf("command?: ");
		fgets(curcmd, MAX, stdin);
		strip(curcmd);

  }// end while
  saveList(history, openFin(startDir, "/.ussh_history", "w"), histFileCount);
	clearList(history, cleanData);
  free(startDir);
  startDir = NULL;
  free(history->head);
  free(history);
  history = NULL;
//  free(PATH);
//  PATH = NULL;

  return 0;

}// end main


