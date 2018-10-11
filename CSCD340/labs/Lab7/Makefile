all:	lab7

lab7:	cscd340Lab7.c ./tokenize/makeArgs.o ./utils/myUtils.o ./process/process.o ./pipes/pipes.o
	gcc -g cscd340Lab7.c ./tokenize/makeArgs.o ./utils/myUtils.o ./process/process.o ./pipes/pipes.o -o lab7

makeArgs.o:	./tokenize/makeArgs.c ./tokenize/makeArgs.h
	gcc -g -c ./tokenize/makeArgs.c

myUtils.o:	./utils/myUtils.o ./utils/myUtils.h
	gcc -g -c ./utils/myUtils.c

process.o:	./process/process.c ./process/process.h
	gcc -g -c ./process/process.c	

pipes.o:	./pipes/pipes.c ./pipes/pipes.h
	gcc -g -c ./pipes/pipes.c

myCommand.o:    ./myCommand/myCommand.c ./myCommand/myCommand.h
    gcc -g -c ./myCommand/myCommand.c

linkedlist.o:   ./linkedlist/linkedlist.c ./linkedlist/linkedlist.h
    gcc -g -c ./linkedlist/linkedlist.c

listUtils.o: ./linkedlist/listUtils.c ./linkedlist/listUtils.h
    gcc -g -c ./linkedlist/listUtils.c

clean:
	rm ./pipes/pipes.o	
	rm ./utils/myUtils.o
	rm ./process/process.o
	rm ./tokenize/makeArgs.o
	rm ./myCommand/myCommand.o
	rm ./linkedlist/linkedlist.o
	rm ./linkedlist/listUtils.o
	rm lab7

