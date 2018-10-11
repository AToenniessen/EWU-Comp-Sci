#include "pipes.h"
#include "../tokenize/makeArgs.h"

int containsPipe(char *s)
{
	int count = 0;
	for(int n = 0; n < strlen(s); n++){
		if('|' == s[n])
			count++;
	}
	return count;
}// end containsPipe

char *** parsePipe(char *s, int n, char *** args)
{
    char copy[256];
	strcpy(copy, s);
    char *save = NULL, *token = NULL;
    if((token = strtok_r(copy, "|", &save)) == NULL){
        return args;
    }
    else {
        makeargs(token, &args[n]);
    }
    return parsePipe(save, n + 1, args);
}// end parsePostPipe


void pipeIt(char *** args, int pipeCount)
{
    int   fd[2];
    pid_t pid;
    int   fd_in = 0, status;
    char ** cmd;

    for(int n = 0; n <= pipeCount; n++){
        cmd = args[n];
        pipe(fd);
        if ((pid = fork()) == -1)
        {
            exit(EXIT_FAILURE);
        }
        else if (pid == 0)
        {
            dup2(fd_in, 0);
            if (args[n + 1] != NULL)
                dup2(fd[1], 1);
            close(fd[0]);
            execvp(cmd[0], cmd);
            exit(EXIT_FAILURE);
        }
        else
        {
            waitpid(pid, &status, 0);
            close(fd[1]);
            fd_in = fd[0];
        }
    }
}// end pipeIt


