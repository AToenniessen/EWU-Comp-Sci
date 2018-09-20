cmake_minimum_required(VERSION 3.12)
project(project C)

set(CMAKE_C_STANDARD 99)

add_executable(project cscd340Lab1.c utils/fileUtils.c stock/stock.c)