#include <string.h>
#include "myData.h"

void * buildCommand(char *alias, char *command)
{
	MyCommand * temp = (MyCommand *) calloc(1, sizeof(MyCommand));
	temp->command = (char *) calloc(strlen(command) + 1, sizeof(char));
	temp->alias = (char *) calloc(strlen(alias) + 1, sizeof(char));
	strcpy(temp->command, command);
	strcpy(temp->alias, alias);
	return temp;

}

char * accessCommand(void *ptr){
	MyCommand * temp = (MyCommand *)ptr;
	return temp->command;
}
char * accessAlias(void *ptr){
	MyCommand * temp = (MyCommand *)ptr;
	return temp->alias;
}
void printData(void * ptr)
{
	MyCommand * temp = (MyCommand *)ptr;
	printf(": %s\n",temp->command);
}

void cleanData(void *ptr)
{
	MyCommand * temp = (MyCommand *)ptr;
	free(temp->command);
	free(temp->alias);
	free(temp);

}
void writeData(FILE * fin, void *ptr){
	MyCommand * temp = (MyCommand *)ptr;
	fprintf(fin, "%s\n", temp->command);
}
void *readData(FILE * fin){
	char v[101];
	MyCommand * temp = NULL;
	if(fgets(v, 100, fin) != NULL){
		v[strlen(v) - 1] = '\0';
		temp = buildCommand("", v);
	}
//	else{
//		//perror("\nEOF\n");
//		exit(-99);
//	}
	return temp;
}
int compareAlias(const void *n1, const void *n2){
	MyCommand *t1 = (MyCommand *) n1;
	MyCommand *t2 = (MyCommand *) n2;
	return strcmp(t1->alias, t2->alias);
}
int compareCommand(const void *n1, const void *n2){
	MyCommand *t1 = (MyCommand *) n1;
	MyCommand *t2 = (MyCommand *) n2;
	return strcmp(t1->command, t2->command);
}
int compare(const void *n1, const void *n2){
	return compareAlias(n1, n2) && compareCommand(n1, n2);
}