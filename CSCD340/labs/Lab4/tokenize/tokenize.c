#include "tokenize.h"

void clean(int argc, char **argv)
{
    for(int n = 0; n < argc; n++){
        //printf("removed %s", argv[n]);
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
	int wrdCount = 0, n = 0;
	char * save;

	char copy[strlen(s)];
	strcpy(copy, s);

	char * temp = strtok_r(copy, " ", &save);

    if(temp == NULL){
        printf("\nThe string passed in is empty\n");
        return -1;
    }
    else{
        wrdCount++;
        while(temp != NULL){
            temp = strtok_r(NULL, " ", &save);
            wrdCount++;
        }
    }

    *argv = (char **)calloc(wrdCount, sizeof(char *));

    strcpy(copy, s);
    temp = strtok_r(copy, " ", &save);
    while(temp != NULL){
        (*argv)[n] = (char *)calloc(strlen(temp)+1, sizeof(char));
	    strcpy((*argv)[n], temp);
	    temp = strtok_r(NULL, " ", &save);
	    n++;
	}

    (*argv)[n] = '\0';

    return n;

}// end makeArgs
