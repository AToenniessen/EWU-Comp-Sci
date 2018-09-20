#include "stock.h"

void * buildStock(FILE * fin){      //ask stu about this tomorrow
    struct stock *n = (struct stock*)malloc(sizeof(struct stock));
    fscanf(fin, "%p", &n -> symbol);
    fscanf(fin, "%p", &n -> name);
    fscanf(fin, "%lf", &n -> price);
}

void printStock(void * ptr){
    struct stock * temp = (struct stock*) ptr;
    printf(temp->name, " - ", temp->symbol, " - ", temp->price);
}

void cleanStock(void * ptr){
    struct stock * temp = (struct stock*) ptr;
    free(temp->symbol);
    temp->symbol = NULL;
    free(temp->name);
    temp->name = NULL;
    free(temp);
    temp = NULL;
}

int compareSymbols(const void * s1, const void * s2){
    struct stock * val1 = (struct stock *) s1;
    struct stock * val2 = (struct stock *) s2;
    return strcmp(val1->symbol, val2->symbol);
}

int compareNames(const void * s1, const void * s2){
    struct stock * val1 = (struct stock *) s1;
    struct stock * val2 = (struct stock *) s2;
    return strcmp(val1->name, val2->name);
}

int comparePrices(const void * s1, const void * s2){
    struct stock * val1 = (struct stock *) s1;
    struct stock * val2 = (struct stock *) s2;
    return (int) (val1->price * 100 - val2->price * 100);
}





