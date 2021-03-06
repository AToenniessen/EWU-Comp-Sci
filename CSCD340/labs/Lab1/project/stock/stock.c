#include "stock.h"


void * buildStock(FILE * fin){
    struct stock *n = (struct stock*)calloc(1, sizeof(struct stock));

    char tempSymbol[100] = "";
    fgets(tempSymbol, 100, fin);
    strtok(tempSymbol, "\n");
    strtok(tempSymbol, "\r");
    n->symbol = (char *)calloc(1, strlen(tempSymbol) + 1);
    memcpy(n->symbol, tempSymbol, strlen(tempSymbol) + 1);

    char tempName[100] = "";
    fgets(tempName, 100, fin);
    strtok(tempName, "\n");
    strtok(tempName, "\r");
    n->name = (char*)calloc(1, strlen(tempName) + 1);
    memcpy(n->name, tempName, strlen(tempName) + 1);

    char tempPrice[100] = "";
    fgets(tempPrice, 100, fin);
    n->price = strtod(tempPrice, NULL);

    return n;
}

void printStock(void * ptr){
    struct stock * temp = (struct stock*) ptr;
    printf("%s - ", temp->name);
    printf("%s - ", temp->symbol);
    printf("%.2lf", temp->price);
    printf("\n\n");
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
    return (int) ((val1->price * 100) - (val2->price * 100));
}





