cmake_minimum_required(VERSION 3.12)
project(project C)

set(CMAKE_C_STANDARD 99)

add_executable(project cscd340Lab3.c utils/myUtils.c utils/fileUtils.c
        linkedlist/linkedList.c linkedlist/listUtils.c myInt/myInt.c)