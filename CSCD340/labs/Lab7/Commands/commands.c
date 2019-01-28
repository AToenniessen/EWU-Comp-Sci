#include "commands.h"

void addCommand(LinkedList *history, char *lstcmd, char *curcmd) {
	if (strcmp(curcmd, lstcmd) != 0 && curcmd[0] != '!')
		addFirst(history, buildNode("", curcmd, buildCommand));
}

int executeCD(char **argv) {
	int changed = 0;
	if (argv[1] == NULL || strcmp(argv[1], "~") == 0) {
		changed = chdir(getenv("HOME")); //finish later
	} else {
		changed = chdir(argv[1]);
	}
	return changed;
}

void executePipe(int pipeCount, char *curcmd) {
	char ***args = (char ***) calloc(pipeCount + 2, sizeof(char **));
	int *r = parsePipe(curcmd, pipeCount, args);
	pipeIt(args, pipeCount);
	for (int i = 0; i <= pipeCount + 1; i++) {
		clean(r[i], args[i]);
	}
	free(r);
	free(args);
	args = NULL;
}

void executeAlias(LinkedList *alias, char *curcmd, char **argv) {
	char *token, *save, *c;
	if (argv[1] != NULL) {
		token = strtok_r(curcmd, "=", &save);
		strtok_r(token, " ", &c);
		if (alias->size > 0)
			removeItem(alias, buildNode(c, save, buildCommand), cleanData, compareAlias);
		addFirst(alias, buildNode(c, save, buildCommand));
	} else
		fprintf(stderr, "\nCannot declare alias\n");
}
void modifyPath(char *s) {
	if(strstr(s, "$PATH")){
		char *path = getenv("PATH");
		char *t = (char *)calloc((strlen(path) + strlen(s + 5)) + 1, sizeof(char));
		strcpy(t, path);
		strcat(t, s + 5);
		setenv("PATH", t, 1);
		free(t);
	}
	else{
		setenv("PATH", s, 1);
	}
}