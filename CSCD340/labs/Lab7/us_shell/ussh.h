#include <stdio.h>
#include "../pipes/pipes.h"
#include "../utils/myUtils.h"
#include "../process/process.h"
#include "../tokenize/makeArgs.h"
#include "../linkedlist/linkedList.h"
#include "../linkedlist/listUtils.h"
//#include "myData/myData.h"
#include "../Commands/commands.h"

struct ussh {
	int histCount, histFileCount;
	char *startDir;
	char curcmd[MAX], lstcmd[MAX];
	LinkedList *alias, *history;
};
typedef struct ussh Shell;

Shell *uShell();
int processCommand(Shell *shell, char *curcmd);

void exitShell(Shell *shell);
