all:	lab7

lab7:	cscd340Lab7.c ./tokenize/makeArgs.o ./utils/myUtils.o ./process/process.o ./pipes/pipes.o ./myData/myData.o ./linkedlist/linkedList.o ./linkedlist/listUtils.o ./Commands/commands.o ./us_shell/ussh.o
	gcc -g cscd340Lab7.c ./tokenize/makeArgs.o ./utils/myUtils.o ./process/process.o ./pipes/pipes.o ./myData/myData.o ./linkedlist/linkedList.o ./linkedlist/listUtils.o ./Commands/commands.o ./us_shell/ussh.o -o lab7

makeArgs.o:	./tokenize/makeArgs.c ./tokenize/makeArgs.h
	gcc -g -c ./tokenize/makeArgs.c

myUtils.o:	./utils/myUtils.c ./utils/myUtils.h
	gcc -g -c ./utils/myUtils.c

process.o:	./process/process.c ./process/process.h
	gcc -g -c ./process/process.c	

pipes.o:    ./pipes/pipes.c ./pipes/pipes.h
	gcc -g -c ./pipes/pipes.c

myData.o:   ./myData/myData.c ./myData/myData.h
	gcc -g -c ./myData/myData.c

linkedList.o:   ./linkedlist/linkedList.c ./linkedlist/linkedList.h
	gcc -g -c ./linkedlist/linkedList.c

listUtils.o:    ./linkedlist/listUtils.c ./linkedlist/listUtils.h
	gcc -g -c ./linkedlist/listUtils.c

commands.o:  ./Command/commands.c ./Command/commands.h
	gcc -g -c ./Command/commands.c

ussh.o:   ./us_shell/ussh.c ./us_shell/ussh.h
	gcc -g -c ./us_shell/ussh.c

clean:
	rm ./pipes/pipes.o	
	rm ./utils/myUtils.o
	rm ./process/process.o
	rm ./tokenize/makeArgs.o
	rm ./myData/myData.o
	rm ./linkedlist/linkedList.o
	rm ./linkedlist/listUtils.o
	rm ./Command/command.o
	rm ./us_shell/ussh.o
	rm lab7

