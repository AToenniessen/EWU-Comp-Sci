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

char ** parsePrePipe(char *s, int * preCount)
{
    char copy[256];
    strcpy(copy, s);
    char **args = NULL, *token = NULL, *save = NULL;
    token = strtok_r(copy, "|", &save);
    *preCount = makeargs(token, &args);
    return args;
}// end parsePrePipe


char ** parsePostPipe(char *s, int * postCount)
{
    char copy[256];
	strcpy(copy, s);
    char **args = NULL, *save = NULL;
    strtok_r(copy, "|", &save);
    *postCount = makeargs(save, &args);
    return args;
}// end parsePostPipe


void pipeIt(char ** prePipe, char ** postPipe)
{
	int status;
    int fd[2], res;
    pid_t pid = fork();
	if(pid != 0){
		waitpid(pid, &status, 0);
	}
	else{
		res = pipe(fd);
		pid  = fork();
		if(res < 0)
		{
            printf("Pipe Failure\n");
            exit(-1);
        }// end if
        if(pid != 0)
        {
            close(fd[1]);
            close(0);
            dup(fd[0]);
            close(fd[0]);
            if(execvp(postPipe[0], postPipe) == -1) {
                char error[256];
                strcpy(error, postPipe[0]);
                strcat(error,": command not found\n");
                fputs(error , stderr);
                exit(-1);
            }
        }// end if AKA parent
        else
        {
            close(fd[0]);
            close(1);
            dup(fd[1]);
            close(fd[1]);
            if(execvp(prePipe[0], prePipe) == -1) {
                char error[256];
                strcpy(error, prePipe[0]);
                strcat(error,": command not found\n");
                fputs(error , stderr);
                exit(-1);
            }
        }// end else AKA child
	}
}// end pipeIt


