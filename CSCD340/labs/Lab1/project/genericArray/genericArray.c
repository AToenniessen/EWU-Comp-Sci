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
    GenericArray * array = (GenericArray *)calloc(length, sizeof(GenericArray));
    for(int n = 0; n < length; n++){
        array[n].data = buildType(in);
    }

    return array;
}

void sortArray(GenericArray * array, int length, int (*compar)(const void * v1, const void * v2)){
    void * smallest;
    for(int n = 0; n < length - 1; n++) {
        smallest = array[n].data;
        for(int m = n + 1; m < length; m++){
            int c = compar(smallest, array[m].data);
            if(c > 0) {
                smallest = array[m].data;
                array[m].data = array[n].data;
                array[n].data = smallest;
            }
        }
    }
}




