#include <string.h>
#include "myData.h"

void * buildData(int n, char *command)
{
	MyCommand * temp = (MyCommand *) calloc(1, sizeof(MyCommand));
	temp->value = (char *) calloc(strlen(command) + 1, sizeof(char));
	strcpy(temp->value, command);
	temp->number = n;
	return temp;	

}
char * accessVal(void *ptr){
	MyCommand * temp = (MyCommand *)ptr;
	return temp->value;
}
int accessNum(void *ptr){
	MyCommand * temp = (MyCommand *)ptr;
	return temp->number;
}
void printData(void * ptr)
{
	MyCommand * temp = (MyCommand *)ptr;
	printf("%i: %s\n",temp->number, temp->value);
}

void cleanData(void *ptr)
{
	MyCommand * temp = (MyCommand *)ptr;
	free(temp->value);
	free(temp);

}
void writeData(FILE * fin, void *ptr){
	MyCommand * temp = (MyCommand *)ptr;
	fprintf(fin, "%i\n", temp->number);
	fprintf(fin, "%s\n", temp->value);
}
void *readData(FILE * fin){
	char n[1001], v[101];
	MyCommand * temp = NULL;
	if(fgets(n, 1000, fin) != NULL && fgets(v, 100, fin) != NULL){
		n[strlen(n) - 1] = '\0';
		v[strlen(v) - 1] = '\0';
		temp = buildData((int)strtol(n, (char**)NULL, 10), v);
	}
//	else{
//		//perror("\nEOF\n");
//		exit(-99);
//	}
	return temp;
}
