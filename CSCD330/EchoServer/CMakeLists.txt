cmake_minimum_required(VERSION 3.6)
project(EchoServer)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES EchoServer.c Client.c)
add_executable(EchoServer ${SOURCE_FILES} EchoServer.c Client.c)