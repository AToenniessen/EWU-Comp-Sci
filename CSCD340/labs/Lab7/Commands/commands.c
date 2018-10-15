#include "commands.h"


int executeCD(char **argv)
{
	int changed = 0;
	if(argv[1] == NULL || strcmp(argv[1], "~") == 0) {
		changed = chdir(getenv("HOME")); //finish later
	}
	else {
		changed = chdir(argv[1]);
	}
	return changed;
}