#include "./us_shell/ussh.h"


void getCmd(Shell *shell) {
	printf("command?: ");
	fgets(shell->curcmd, MAX, stdin);
	strip(shell->curcmd);
}
int main() {
	Shell *shell;
	shell = uShell();

	getCmd(shell);
	while (strcmp(shell->curcmd, "exit") != 0) {
		if(processCommand(shell, shell->curcmd))
			continue;
		getCmd(shell);
	}
	exitShell(shell);

	return 0;

}// end main



