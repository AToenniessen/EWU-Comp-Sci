#include "myData.h"

void * buildData(char *command)
{
	MyCommand * temp = calloc(1, sizeof(MyCommand));
	temp->value = command;

	return temp;	

}

void cleanData(void *ptr)
{
	MyCommand * temp = (MyCommand *)ptr;
	free(temp);

}
