#include "process.h"

void forkIt(char *file, char **argv) {
	int status;
	pid_t pid = fork();

	int c = 0, bckgrnd = 0;
	char ltr, *s = argv[c];
	while (s != NULL) {
		for (int n = 0; n < strlen(s); n++) {
			ltr = s[n];
			if (ltr == '&') {
				bckgrnd = 1;
				free(argv[c]);
				argv[c] = NULL;
				break;
			}
		}
		if (bckgrnd)
			break;
		c++;
		s = argv[c];
	}

	if (pid != 0) {
		if (!bckgrnd)
			waitpid(pid, &status, 0);
	} else {
		if (bckgrnd)
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

