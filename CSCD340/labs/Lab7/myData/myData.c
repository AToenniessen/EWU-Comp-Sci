#include <string.h>
#include "myData.h"

void * buildData(int n, char *command)
{
	MyCommand * temp = calloc(1, sizeof(MyCommand));
	temp->value = (char *) calloc(1, sizeof(command));
	strcpy(temp->value, command);
	temp->number = n;
	return temp;	

}
char * accessData(void * ptr){
	MyCommand * temp = (MyCommand *)ptr;
	return temp->value;
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
