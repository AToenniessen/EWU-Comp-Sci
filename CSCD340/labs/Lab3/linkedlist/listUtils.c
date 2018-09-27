#include "listUtils.h"

Node * buildNode( FILE * in, void *(*buildData)(FILE * fin) )
{
    Node * temp = (Node *)calloc(1, sizeof(Node));
    temp->data = buildData(in);
    return temp;
}// end buildNode

Node * buildNode_Type(void * passedIn)
{
     return buildNode(stdin, passedIn);
}// end buildNode_Type


void buildListTotal(LinkedList * myList, int total, FILE * fin, void * (*buildData)(FILE * in) )
{
    for(int n = 0; n < total; n++)
        addFirst(myList, buildNode(fin, buildData));
}// end buildListTotal


void sort(LinkedList * theList, int (*compare)(const void *, const void *))
{
    Node * start, *search, *min;
    void * temp;

    if(theList->size > 1)
    {

        for(start = theList->head->next; start->next != NULL; start = start->next)
        {
            min = start;

            for(search = start->next; search != NULL; search = search->next)
                if(compare(search->data, min->data) < 0)
                    min = search;

            temp = start->data;
            start->data = min->data;
            min->data = temp;

        }// end for start

    }// end if
}// end sort
