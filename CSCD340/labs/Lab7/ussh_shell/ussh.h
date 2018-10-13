

#ifndef PROJECT_USSH_H
#define PROJECT_USSH_H

#endif //PROJECT_USSH_H

struct ussh
{
	int argc, pipeCount, histcount, histfilecount;
	char * startDir, *PATH;
	//getcwd(startDir, 4096);
	char **argv, ***args, s[MAX];
};
typedef struct ussh Shell;

Shell * uShell();
Shell * uShell(FILE * fin);