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
LinkedList * readList(FILE * fin){
    LinkedList * temp = linkedList();
    if(fin == NULL)
    	return temp;
    void * cur = NULL;
    while((cur = readData(fin)) != NULL){
        Node * nn = (Node *)calloc(1, sizeof(Node));
        nn->data = cur;
        addLast(temp, nn);
    }
    closeFin(fin);
    return temp;
}
void saveList(LinkedList * theList, FILE * fin, int histFileCount){
    if(fin != NULL){
        Node * cur = theList->head->next;
        int n = 0;
        while(cur != NULL && n <= histFileCount){
            writeData(fin, cur->data);
            cur = cur->next;
            n++;
        }
    }
    closeFin(fin);
}
void printRecur(Node * cur, void(*convertData)(void *), int n, int histcount){
	if(cur->next == NULL || n == histcount) {
		convertData(cur->data);
		return;
	}
	printRecur(cur->next, convertData, ++n, histcount);
	convertData(cur->data);
}

void printList(const LinkedList * theList, void (*convertData)(void *), int histCount)
{
    if(theList->size != 0) {
        Node *cur = theList->head->next;
	    printRecur(cur, convertData, 1, histCount);
    }
    else
        printf("\nThe list is empty!\n\n");

}// end printList
Node * getNode(LinkedList * theList, int pos){
	Node *cur = theList->head->next;
	if (pos <= 0 || pos >= theList->size) {
		fprintf(stderr, "\nCommand number requested does not exist\n");
		return NULL;
	} else{
		while(cur->next != NULL){
			if(accessNum(cur->data) == pos)
				break;
			cur = cur->next;
		}
	}
	return cur;
}


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
    Node *head = theList->head;
    if(theList->size == 0) {
        head->next = nn;
        nn->prev = head;
    }
    else{
        Node *temp = head->next;
        nn->prev = head;
        nn->next = temp;
        head->next = nn;
        temp->prev = nn;
    }
    theList->size++;

}// end addFirst

void removeFirst(LinkedList * theList, void (*removeData)(void *))
{
    if(theList == NULL){
        perror("\nThe list passed in is NULL\n");
        exit(-99);
    }
    else if(theList->size != 0) {
        Node *head = theList->head;
        Node *element1 = head->next;
        if(element1->next != NULL) {
            Node *element2 = element1->next;
            removeData(element1->data);
            free(element1);
            head->next = element2;
            element2->prev = head;
        }
        else{
            head->next = NULL;
            removeData(element1->data);
            free(element1);
        }
        theList->size--;
        element1 = NULL;
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
        int found = -1;
        while(cur != NULL){
            found = compare(toRemove->data, cur->data);
            if(found == 0){
                if(cur->next != NULL) {
                    cur->prev->next = cur->next;
                    cur->next->prev = cur->prev;
                }
                else
                    cur->prev->next = NULL;
                removeData(cur->data);
                free(cur);
                cur = NULL;
                theList->size--;
                break;
            }
            cur = cur->next;
        }
        if(found != 0)
            printf("\nNo element matching user input found in list\n");
        removeData(toRemove->data);
        free(toRemove);
        toRemove = NULL;
    }
    else
        printf("\nThe list has no elements to remove!\n");
}// end removeItem

