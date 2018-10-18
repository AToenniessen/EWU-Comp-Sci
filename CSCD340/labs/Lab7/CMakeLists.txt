cmake_minimum_required(VERSION 3.7)
project(project C)

set(CMAKE_C_STANDARD 99)

add_executable(project cscd340Lab7.c utils/myUtils.c tokenize/makeArgs.c process/process.c pipes/pipes.c us_shell/ussh.c
        myData/myData.c linkedlist/linkedList.c linkedlist/listUtils.c Commands/commands.c Commands/commands.h us_shell/ussh.c us_shell/ussh.h) #us_shell/ussh.c us_shell/ussh.h)