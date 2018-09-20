#include "genericArray.h"


void printArray(GenericArray * array, int length, void (*printType)(void *))
{
	int x;
	printf("\n");
	for(x = 0; x < length; x++)
		printType(array[x].data);
	
	printf("\n");
}


void cleanArray(GenericArray * array, int length, void (*cleanType)(void *))
{
	int x;
	for(x = 0; x < length; x++)
		cleanType(array[x].data);
	
	free(array);
	array = NULL;

}

GenericArray * fillArray(FILE * in, int length, void * (*buildType)(FILE * input)){

}

void sortArray(GenericArray * array, int length, int (*compar)(const void * v1, const void * v2)){
    
}




