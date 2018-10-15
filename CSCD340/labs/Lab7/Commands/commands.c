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
int executeHistory(int n, LinkedList *history, char *PATH, char ***argv, int histCount) {
	if (history->size == 0)
		perror("\nNo commands have been used yet\n");
	int argc;
	Node *cur = history->head->next;
	if (n <= 0 || n >= history->size) {
		fprintf(stderr, "\nCommand number requested does not exist\n");
		return 0;
	} else{
		while(cur->next != NULL){
			if(accessNum(cur->data) == n)
				break;
			cur = cur->next;
		}
	}
	argc = makeargs(accessVal(cur->data), argv);
	if(strcmp((*argv)[0], "history") == 0){
		printList(history, printData, histCount);
		return argc;
	}

	forkIt(PATH, (*argv));
	return argc;
}