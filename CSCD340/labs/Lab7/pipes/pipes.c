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

int * parsePipe(char *s, int n, char *** args) //unknown memory leak, fix later
{
    char *save = NULL, *token = NULL, temp[100];
    int *r = (int *)calloc(n + 2, sizeof(int));
    strcpy(temp, s);
    token = strtok_r(temp, "|", &save);
    for(int i = 0; i <= n + 1; i++){
        if(token == NULL)
            break;
        r[i] = makeargs(token, &args[i]);
	    token = strtok_r(NULL, "|", &save);
    }
    return r;
}// end parsePostPipe


void pipeIt(char * file, char *** args, int pipeCount)
{
    int   fd[2];
    pid_t pid = 0;
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
            setenv("PATH", file, 1);
            execvp(cmd[0], cmd);
            exit(EXIT_FAILURE);
        }
        else
        {
            waitpid(pid, &status, 0);
            close(fd[1]);
            fd_in = fd[0];
            //exit(-1);
        }
    }
}// end pipeIt


