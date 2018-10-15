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
  int argc = 0, pipeCount = 0, histCount = 1000, histFileCount = 2000;
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
	  	    int * r = parsePipe(curcmd, pipeCount, args);
			pipeIt(PATH, args, pipeCount);
			for(int i = 0; i <= argc; i++){
				clean(r[i], args[i]);
			}
		  free(r);
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
				continue;
	        }
	        else if(argv[0][0] == '!'){
		        char s[1000];
		        strcpy(s,argv[0]+1);
		        clean(argc, argv);
		        int n = (int)strtol(s, (char **)NULL, 10);
		        Node * temp;
		        if((temp = getNode(history, n)) != NULL) {
			        strcpy(curcmd, accessVal(temp->data));
			        continue;
		        }
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


