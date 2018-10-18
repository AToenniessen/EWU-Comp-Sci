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

void executePipe(int pipeCount, char *curcmd, char *PATH) {
	char ***args = (char ***) calloc(pipeCount + 2, sizeof(char **));
	int *r = parsePipe(curcmd, pipeCount, args);
	pipeIt(PATH, args, pipeCount);
	for (int i = 0; i <= pipeCount + 1; i++) {
		clean(r[i], args[i]);
	}
	free(r);
	free(args);
	args = NULL;
}

char *executeAlias(LinkedList *alias, char *curcmd, char **argv) {
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
void modifyPath(char **PATH, char *s) {
	char *token, *save, *t;
//	if ((token = strtok_r(s, ":", &save)) == NULL) {
//		t = (char *) calloc(strlen(save), sizeof(char));
//		strcpy(t, save);
//		*PATH = t;
//	}
//	else if(strcmp(token, "$PATH") == 0){
//		if(save[0] != '/')
//			perror("\ninput PATH not formatted correctly\n");
//		t = (char *)calloc(strlen(save) + strlen(*PATH) + 2, sizeof(char));
//		strcpy(t, *PATH);
//		strcat(t, ":");
//		strcat(t, save);
//		*PATH = t;
//	} else
//		perror("\nImproper declaration of PATH command\n");
}