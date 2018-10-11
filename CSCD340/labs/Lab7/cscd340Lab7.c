#include "./pipes/pipes.h"
#include "./utils/myUtils.h"
#include "./process/process.h"
#include "./tokenize/makeArgs.h"
#include "./linkedlist/linkedList.h"
#include "linkedlist/listUtils.h"
#include "myCommand/myData.h"

int main()
{
  int argc, pipeCount;
  char **argv = NULL, ***args = NULL, s[MAX];
  int preCount = 0, midCount = 0, postCount = 0;
  char ** prePipe = NULL, ** midPipe = NULL, ** postPipe = NULL;
  LinkedList *cmds = linkedList();
  
  printf("command?: ");
  fgets(s, MAX, stdin);
  strip(s);

  while(strcmp(s, "exit") != 0)
  {
	pipeCount = containsPipe(s);
	if(pipeCount > 0)
	{
		args = (char ***) calloc(pipeCount + 2, sizeof(char **));
		pipeIt(parsePipe(s, 0, args), pipeCount);

	}// end if pipeCount	  

	else
	{
        argc = makeargs(s, &argv);
        if(argc != -1) {
	  		forkIt(argv);
		}
	  
	  	clean(argc, argv);
	  	argv = NULL;
	}
	
	printf("command?: ");
	fgets(s, MAX, stdin);
      	strip(s);

  }// end while

  return 0;

}// end main

