#include "process.h"

void forkIt(char * file, char ** argv)
{
	int status;
	pid_t pid = fork();

	int c = 0, bckgrnd = 0;
	char ltr, *s = argv[c];
	while(s != NULL) {
		for (int n = 0; n < strlen(s); n++){
			ltr = s[n];
			if(ltr == '&'){
				bckgrnd = 1;
				free(argv[c]);
				argv[c] = NULL;
				break;
			}
		}
		if(bckgrnd)
			break;
		c++;
		s = argv[c];
	}

	if (pid != 0) {
		if (!bckgrnd)
			waitpid(pid, &status, 0);
	}

	else {
		if(bckgrnd)
			setpgid(0, 0);
		setenv("PATH", file, 1);
		if (execvp(argv[0], argv) == -1) {
			char error[256];
			strcpy(error, argv[0]);
			strcat(error, ": command not found\n");
			fputs(error, stderr);
			exit(-1);
		}
	}// end else

}// end forkIt



void modifyPath(char ** PATH, char *s){
	char *token, *save, *t;
	if((token = strtok_r(s, ":", &save)) == NULL){
		t = (char *)calloc(strlen(save), sizeof(char));
		strcpy(t, save);
		*PATH = t;
	}
	else if(strcmp(token, "$PATH") == 0){
		if(save[0] != '/')
			perror("\ninput PATH not formatted correctly\n");
		t = (char *)calloc(strlen(save) + strlen(*PATH) + 1, sizeof(char));
		strcpy(t, *PATH);
		strcat(t, ":");
		strcat(t, save);
		*PATH = t;
	} else
		perror("\nImproper declaration of PATH command\n");
}

