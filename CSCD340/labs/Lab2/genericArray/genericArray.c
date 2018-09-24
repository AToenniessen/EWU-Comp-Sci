#include "genericArray.h"
#include "../utils/myUtils.h"


GenericArray * fillArray(FILE * in, int length, void * (*buildType)(FILE * input))
{
	int x;
	
	GenericArray * array = calloc(length, sizeof(GenericArray));

	for(x = 0; x < length; x++)
		array[x].data = buildType(in);

	return array;
}


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


void sortArray(GenericArray * array, int length, int (*compar)(const void * v1, const void * v2))
{
	int start, search, min;
	GenericArray temp;

	for(start = 0; start < length - 1; start++)
	{
		min = start;
		
		for(search = start + 1; search < length; search ++)
			if(compar(array[search].data, array[min].data) < 0)
				min = search;

		temp = array[min];
		array[min] = array[start];
		array[start] = temp;

	}// for start

}// end sortArray


/**
 * The addItem method, adds an item into the array.  It first creates a new array of size length + 1
 * then it copies over all the elements.  After the copy it prompts the user to enter the new item 
 * from the command line which it stores in the last element of the array. The old array is freed and 
 * the length is updated before the new array is returned.
 *
 * @param array The original array to be copied 
 * @param length The total number of elements in the array as a pointer
 * @param function pointer for building the type from the keyboard
 * @return GenericArray * Representing the new array
 */
GenericArray * addItem(GenericArray * array, int *length, void * (*buildType_prompt)())
{
	GenericArray *newArray = (GenericArray*) calloc((size_t) (*length + 1), sizeof(GenericArray));

	for(int n = 0; n < *length; n++){
		memcpy(&newArray[n].data, &array[n].data, sizeof(array[n].data));
	}

	GenericArray * data = (GenericArray*) calloc(1, sizeof(GenericArray));
	data->data = buildType_prompt();
	memcpy(&newArray[*length + 1].data, &data->data, sizeof(data));

	free(array);
	array = NULL;

	*length = *length + 1;

	return newArray;

}// end addItem



/**
 * The removeItemByValue method, removes an item from the array.  It first prompts the user to enter a type
 * via the function pointer buildType_ propmt.  It then check to see if that value is in the array. 
 * If not, then an "Item Not Found! No Changes\n" message is displayed and the original array is returned.
 * If the item is in the array, then item is removed using the function 
 * pointer cleanType. A new array is made one smaller and the elements except the one being removed
 * are copied.  the local item is freed via cleanType, and the old array is freed and the length is updated.
 * NOTE: What happens if the array is length 0?
 *
 * @param array The original array to be changed 
 * @param length The total number of elements in the array as a pointer
 * @param function pointer for creating the type from the keyboard
 * @param function pointer for removing the type 
 * @param function pointer for comparing the type 
 * @return GenericArray * Representing the new array
 */
GenericArray * removeItemByValue(GenericArray * array, int *length, void * (*buildType_prompt)(), void (*cleanType)(void * ptr), int (*compar)(const void * v1, const void * v2))
{
	void * data = buildType_prompt();
	int indexFound = -1;
	for(int n = 0; n < *length; n++){
		if(compar(data, array[n].data) == 0) {
			indexFound = n;
			break;
		}
	}
	if(indexFound > -1)
		return removeItemByIndexPassedIn(array, length, cleanType, indexFound);
	else {
		printf("Item Not Found! No Changes\n");
		return array;
	}
}//end removeItemByValue


/**
 * The actuallyRemoveItemByIndex method, would be a private method in Java or an overload method
 * in Java.  The method removes an item from the array. 
 * The method first prompts for an index -- look in myUtils 
 * Then item is removed using the function pointer cleanType. 
 * A new array is made one smaller and the elements except the one being removed
 * are copied.  The old array is freed and the length is updated.
 * NOTE: What happens if the array is length 0? Can you actually get here if the length is 0?
 *
 * @param array The original array to be changed 
 * @param length The total number of elements in the array as a pointer
 * @param function pointer for removing the type 
 * @param index The index to remove
 * @return GenericArray * Representing the new array
 */
GenericArray * removeItemByIndexPassedIn(GenericArray * array, int *length, void (*cleanType)(void * ptr), int index)
{
	if(*length <= 0){
		printf("\nThere are no elements to remove\n");
		return array;
	}
	else if(array == NULL){
		printf("\n\nThe array passed in is null");
		exit(-99);
	}

	cleanType(&array[index]);
	GenericArray *newArray = (GenericArray*) calloc((size_t) (*length - 1), sizeof(GenericArray));

	for(int n = 0; n < *length; n++){
		if(n >= index){
			memcpy(&newArray[n].data, &array[n + 1].data, sizeof(array[n + 1].data));
		}
		else{
			memcpy(&newArray[n].data, &array[n].data, sizeof(array[n].data));
		}
	}

	free(array);
	array = NULL;

	*length = *length - 1;

	return newArray;
}//end removeItemByIndexPassedIn


/**
 * The removeItemByIndex method, removes an item from the array. 
 * The method first prompts for an index -- look in myUtils 
 * Then item is removed using the function pointer cleanType. 
 * A new array is made one smaller and the elements except the one being removed
 * are copied.  The old array is freed and the length is updated.
 * NOTE: What happens if the array is length 0? Can you actually get here if the length is 0?
 *
 * @param array The original array to be changed 
 * @param length The total number of elements in the array as a pointer
 * @param function pointer for removing the type 
 * @return GenericArray * Representing the new array
 */
GenericArray * removeItemByIndex(GenericArray * array, int *length, void (*cleanType)(void * ptr))		//check if start at 0 or change to start at 1
{
	if(*length <= 0){
		printf("\nThere are no elements to remove\n");
		return array;
	}
	else if(array == NULL){
		printf("\n\nThe array passed in is null");
		exit(-99);
	}
	return removeItemByIndexPassedIn(array, length, cleanType, readIndex(*length));
}//end actuallyRemoveItemByIndex





