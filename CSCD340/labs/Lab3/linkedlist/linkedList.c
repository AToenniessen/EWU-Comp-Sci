#include "linkedList.h"

LinkedList * linkedList()
{
    LinkedList * list = (LinkedList *)calloc(1, sizeof(LinkedList));
    list->head = (Node *)calloc(1, sizeof(Node));
    list->size = 0;
    return list;
}// end linkedList


void clearList(LinkedList * theList, void (*removeData)(void *))
{
    Node *cur = theList->head->next, *temp;
    while(cur != NULL){
        temp = cur;
        cur = cur->next;
        removeData(temp->data);
        free(temp);
        temp = NULL;
    }
    theList->size = 0;
}// end clearList

void printList(const LinkedList * theList, void (*convertData)(void *))
{
    if(theList->size != 0) {
        Node *cur = theList->head->next;
        while(cur != NULL) {
            printf("\n");
            convertData(cur->data);
            cur = cur->next;
        }
        printf("\n");
    }
    else
        printf("\nThe list is empty!\n\n");

}// end printList

void addLast(LinkedList * theList, Node * nn)
{
    if(theList == NULL || nn == NULL){
        perror("NULL variable passed in\n");
        exit(-99);
    }
    int n;
    Node *cur = theList->head;
    for(n = 0; n < theList->size; n++){
        cur = cur->next;
    }
    nn->prev = cur;
    cur->next = nn;
    theList->size++;
}// end addLast

void addFirst(LinkedList * theList, Node * nn)
{
    if(theList == NULL || nn == NULL){
        perror("NULL variable passed in\n");
        exit(-99);
    }
    else if(theList->size == 0) {
        theList->head->next = nn;
        nn->prev = theList->head;
        theList->size++;
    }
    else{
        Node *temp = theList->head->next;
        nn->prev = theList->head;
        nn->next = temp;
        theList->head->next = nn;
        temp->prev = nn;
        theList->size++;
    }
}// end addFirst

void removeFirst(LinkedList * theList, void (*removeData)(void *))
{
    if(theList == NULL){
        perror("\nThe list passed in is NULL\n");
        exit(-99);
    }
    else if(theList->size != 0) {
        Node * temp = theList->head->next->next;
        removeData(theList->head->next->data);
        free(theList->head->next);
        theList->head->next = NULL;
        theList->head->next = temp;
        temp->prev = theList->head;
        theList->size--;
    }
    else{
        printf("\nThe list has no elements to remove!\n");
    }
}// end removeFirst

void removeLast(LinkedList * theList, void (*removeData)(void *))
{
    if(theList == NULL){
        perror("\nThe list passed in is NULL\n");
        exit(-99);
    }
    if(theList->size > 0) {
        Node *cur = theList->head->next;
        for (int n = 0; n < theList->size-1; n++){
            cur = cur->next;
        }
        cur->prev->next = NULL;
        removeData(cur->data);
        free(cur);
        cur = NULL;
        theList->size--;
    }
    else
        printf("\nThe list has no elements to remove!\n");
}// end removeLast


void removeItem(LinkedList * theList, void * (*buildType)(FILE * stream), void (*removeData)(void *), int (*compare)(const void *, const void *))
{
    if(theList == NULL){
        perror("\nThe list passed in is NULL\n");
        exit(-99);
    }
    else if(theList->size != 0) {
        Node *toRemove = (Node *) calloc(1, sizeof(Node));
        printf("\nTo remove element\n");
        toRemove->data = buildType(stdin);
        Node *cur = theList->head->next;
        while(cur != NULL){
            if(compare(toRemove->data, cur->data) == 0){
                if(cur->next != NULL) {
                    cur->prev->next = cur->next;
                    cur->next->prev = cur->prev;
                }
                else
                    cur->prev->next = NULL;
                removeData(cur);
                free(cur);
                cur = NULL;
                break;
            }
            cur = cur->next;
        }
    }
    else
        printf("\nThe list has no elements to remove!\n");
}// end removeItem

