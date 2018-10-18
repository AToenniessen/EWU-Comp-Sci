#include "ussh.h"

Shell *uShell() {
	Shell *temp = (Shell *) calloc(1, sizeof(Shell));
	char curcmd[MAX];
	temp->argc = 0;
	temp->startDir = (char *) calloc(4096, sizeof(char));
	getcwd(temp->startDir, 4096);
	temp->PATH = getenv("PATH");
	temp->alias = linkedList();
	temp->history = linkedList();
	FILE *fin = openFin(temp->startDir, "/.usshrc", "r");
	if (fin != NULL) {
		char *token, *save;
		for (int i = 0; i < 2; i++) {
			fgets(curcmd, 100, fin);
			token = strtok_r(curcmd, "=", &save);
			if (strcmp(token, "HISTCOUNT") == 0)
				temp->histCount = (int) strtol(save, (char **) NULL, 10);
			else
				temp->histFileCount = (int) strtol(save, (char **) NULL, 10);
		}
		while (fgets(curcmd, 100, fin) != NULL) {
			strip(curcmd);
			processCommand(temp, curcmd);
		}
		closeFin(fin);
		clearList(temp->history, cleanData);
		free(temp->history->head);
		free(temp->history);
	} else {
		temp->histCount = 5;
		temp->histFileCount = 10;
	}
	temp->history = readList(openFin(temp->startDir, "/.ussh_history", "r"));
	if (temp->history->head->next != NULL)
		strcpy(temp->lstcmd, accessCommand(temp->history->head->next->data));
	return temp;
}

void stitchCmd(Shell * shell, char ** curcmd){
	char temp[100];
	strcpy(temp, *curcmd);

}
void checkAlias(LinkedList *alias, char **curcmd, char *(*accessData)(void *ptr)){
	Node *t = findData(alias, *curcmd, accessData);
	if (t != NULL)
		strcpy(*curcmd, accessCommand(t->data));
}

int processCommand(Shell *shell, char *curcmd) {
	int argc, pipeCount;
	char **argv, *c, *save;

//	if(strstr(curcmd, "|"))
//		stitchCmd(shell, &curcmd);
//	else {
	checkAlias(shell->alias, &curcmd, accessAlias);
//	}

	argc = makeargs(curcmd, &argv);

	if (argc > 0) {
		addCommand(shell->history, shell->lstcmd, shell->curcmd);

		pipeCount = containsPipe(curcmd);
		if (pipeCount > 0) {
			//argc = pipeCount + 1;
			executePipe(pipeCount, curcmd, shell->PATH);
		}// end if pipeCount
		else {
			if (strcmp(argv[0], "alias") == 0) {
				executeAlias(shell->alias, curcmd, argv);
			} else if (strcmp(argv[0], "unalias") == 0) {
				strtok_r(curcmd, " ", &c);
				removeItem(shell->alias, buildNode(c, "", buildCommand), cleanData, compareAlias);
			} else if (strcmp(argv[0], "history") == 0) {
				printList(shell->history->head, printData, shell->histCount, shell->history->size, 1);
			} else if (strcmp(argv[0], "!!") == 0) {
				clean(argc, argv);
				strcpy(shell->curcmd, shell->lstcmd);
				return 1;
			} else if (argv[0][0] == '!') {
				char s[1000];
				strcpy(s, argv[0] + 1);
				int n = (int) strtol(s, (char **) NULL, 10);
				Node *temp;
				if ((temp = getNode(shell->history, n)) != NULL) {
					clean(argc, argv);
					strcpy(shell->curcmd, accessCommand(temp->data));
					return 1;
				}
			} else if (strcmp(argv[0], "cd") == 0) {
				if (!executeCD(argv))
					perror("");
			} else if (strcmp(strtok_r(curcmd, "=", &save), "PATH") == 0) {
				modifyPath(&shell->PATH, save);
			} else{
				forkIt(shell->PATH, argv);
			}

		}
		clean(argc, argv);
		argv = NULL;
	}
	strcpy(shell->lstcmd, curcmd);
	return 0;
}

void clearShell(Shell *shell) {
	free(shell->startDir);
	shell->startDir = NULL;
	free(shell->history->head);
	free(shell->history);
	shell->history = NULL;
	free(shell->alias->head);
	free(shell->alias);
	shell->alias = NULL;
}

void exitShell(Shell *shell) {
	saveList(shell->history, openFin(shell->startDir, "/.ussh_history", "w"), shell->histFileCount);
	clearList(shell->history, cleanData);
	clearList(shell->alias, cleanData);
	clearShell(shell);
	free(shell);
}


