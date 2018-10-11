#include "./pipes/pipes.h"
#include "./utils/myUtils.h"
#include "./process/process.h"
#include "./tokenize/makeArgs.h"
#include "./linkedlist/linkedList.h"
#include "linkedlist/listUtils.h"
#include "myData/myData.h"

int main()
{
  int argc, pipeCount;
  char **argv = NULL, ***args = NULL, s[MAX];
  
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
        if(strcmp(argv[0], "cd") == 0){
        	int changed = 0;
        	if(argv[1] == NULL) {
		        changed = chdir(""); //finish later
	        }
	        else {
		        changed = chdir(argv[1]);
	        }
	        if(changed == -1){
	        	perror("");
	        }
        }
        else if(argc != -1) {
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

