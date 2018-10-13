#include "commands.h"


int executeCD(char **argv)
{
	int changed = 0;
	if(argv[1] == NULL || strcmp(argv[1], "~") == 0) {
		changed = chdir(getenv("HOME")); //finish later
	}
	else {
		changed = chdir(argv[1]);
	}
	return changed;
}
int executeHistory(LinkedList *history, char *PATH, char ***argv){
	if(history->size == 0)
		perror("\nNo commands have been used yet\n");
	int argc = makeargs(accessData(history->head->next->data), argv);;
	if(strcmp((*argv)[0], "history") == 0){
		printList(history, printData);
	}
	else {
		forkIt(PATH, (*argv));
	}
	return argc;
}