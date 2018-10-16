#include <stdio.h>
#include "./pipes/pipes.h"
#include "./utils/myUtils.h"
#include "./process/process.h"
#include "./tokenize/makeArgs.h"
#include "./linkedlist/linkedList.h"
#include "./linkedlist/listUtils.h"
//#include "myData/myData.h"
#include "./Commands/commands.h"

int main()
{
	int argc = 0, pipeCount = 0, histCount = 5, histFileCount = 10;
	char *startDir = (char *)calloc(4096, sizeof(char)), *PATH = getenv("PATH"), init[4096];
	char **argv = NULL, ***args = NULL, curcmd[MAX] = "", lstcmd[MAX] = "";
	getcwd(startDir, 4096);
	FILE * fin = openFin(startDir, "/.usshrc", "r");
	if(fin != NULL){

	}
	LinkedList * history = readList(openFin(startDir, "/.ussh_history", "r"));
	LinkedList * alias = linkedList();
	if(history->head->next != NULL)
		strcpy(lstcmd, accessCommand(history->head->next->data));

    printf("command?: ");
  fgets(curcmd, MAX, stdin);
  strip(curcmd);


	while(strcmp(curcmd, "exit") != 0)
  {

	  if(strcmp(curcmd, lstcmd) != 0 && curcmd[0] != '!')
		  addLast(history, buildNode("", curcmd, buildCommand));
	  Node * t = findData(alias, curcmd, accessAlias);
	  if(t != NULL)
	  	strcpy(curcmd, accessCommand(t->data));
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

			char *token, *save, *c;
			if(strcmp(argv[0], "alias") == 0){
				if(argv[1] != NULL) {
					token = strtok_r(curcmd, "=", &save);
					strtok_r(token, " ", &c);
					if(alias->size > 1)
						removeItem(alias, buildNode(c, save, buildCommand), cleanData,  compare);                         //finish remove alias!
					addFirst(alias, buildNode(c, save, buildCommand));
		        } else
		        	fprintf(stderr, "\nCannot declare alias\n");
	        } else if(strcmp(argv[0], "unalias") == 0) {
				token = strtok_r(curcmd, " ", &c);
				removeItem(alias, buildNode(c, "", buildCommand), cleanData, compareAlias);
			} else if(strcmp(argv[0], "history") == 0){
					strcpy(lstcmd, curcmd);
					printList(history, printData, histCount);
			}
			else if(strcmp(argv[0], "!!") == 0){
				strcpy(curcmd, lstcmd);
				continue;
	        } else if(argv[0][0] == '!'){
		        char s[1000];
		        strcpy(s,argv[0]+1);
		        clean(argc, argv);
		        int n = (int)strtol(s, (char **)NULL, 10);
		        Node * temp;
		        if((temp = getNode(history, n)) != NULL) {
			        strcpy(curcmd, accessCommand(temp->data));
			        continue;
		        } else
		        	argc = 0;
	        } else if(strcmp(argv[0], "cd") == 0){
	            if(executeCD(argv))
	                perror("");
	        } else if(strcmp(strtok_r(curcmd, "=", &save), "PATH") == 0){
				modifyPath(&PATH, save);
				printf("%s", PATH);
	        } else if(argc != -1) {
		        forkIt(PATH, argv);
			}
			if(argc !=0) {
				clean(argc, argv);
				argv = NULL;
			}
		}

		strcpy(lstcmd, curcmd);
		printf("command?: ");
		fgets(curcmd, MAX, stdin);
		strip(curcmd);

  }// end while
  saveList(history, openFin(startDir, "/.ussh_history", "w"), histFileCount);
	clearList(history, cleanData);
	clearList(alias, cleanData);
  free(startDir);
  startDir = NULL;
  free(history->head);
  free(alias->head);
  free(alias);
  free(history);
  history = NULL;
//  free(PATH);
//  PATH = NULL;

  return 0;

}// end main


