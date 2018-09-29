#include "tokenize.h"

void clean(int argc, char **argv)
{
    for(int n = 0; n < argc + 1; n++){
        free(argv[n]);
        argv[n] = NULL;
    }
    free(argv);
}// end clean

void printargs(int argc, char **argv)
{
	int x;
	for(x = 0; x < argc; x++)
		printf("%s\n", argv[x]);

}// end printargs

int makeargs(char *s, char *** argv)
{
	if(strlen(s) == 0){
	    printf("\nThe string passed in is empty\n");
	    return -1;
	}
	int wrdCount = 1, n;
	char c;
	for(n = 0; n < strlen(s); n++){
        c = s[n];
        if(c == ' '){
	        wrdCount++;
	    }
	}
	*argv = (char **)calloc(wrdCount + 1, sizeof(char *));
	char * save;
	char * temp = strtok_r(s, " ", &save);
	n = 0;
	while(temp != NULL){
        (*argv)[n] = (char *)calloc(strlen(temp)+1, sizeof(char));
	    strcpy((*argv)[n], temp);
	    temp = strtok_r(NULL, " ", &save);
	    n++;
	}
    (*argv)[n] = (char *)calloc(1, sizeof("\0"));
    strcpy((*argv)[n], "\0");
    return n;

}// end makeArgs
