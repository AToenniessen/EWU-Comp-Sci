#include "myUtils.h"

void strip(char *array)
{
	if(array == NULL)
	{
		perror("array is null");
		exit(-99);
	}// end if

	int len = strlen(array), x = 0;
   
	while(array[x] != '\0' && x < len)
	{
	  if(array[x] == '\r')
		 array[x] = '\0';

	  else if(array[x] == '\n')
		 array[x] = '\0';

	  x++;

}// end while
   
}// end strip
FILE * openFin(char * path, char * file, char * m){
	if(strlen(path) >= 4096) {
		perror("\npath is too long\n");
		return NULL;
	}
	char temp[4096];
	strcpy(temp, path);
	strcat(temp, file);
	FILE * fin = fopen(temp, m);
	if(fin != NULL)
		return fin;
	fprintf(stderr, "\nfile cannot be opened, using defaults\n");
	return NULL;
}
void closeFin(FILE * fin){
	if(fin == NULL){
		perror("\nFile pointer is NULL\n");
		exit(-99);
	}
	fclose(fin);
}

