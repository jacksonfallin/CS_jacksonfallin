#include "wordList.h"
#include<stdio.h>
#include<string.h>
#include<stdlib.h>

Node *addToTail(Node *tail, char *line, int lineNum, int wordNum)
{
	Node *temp = (Node*)malloc(sizeof(Node)); 

	strcpy(temp->line, line); 
	temp->lineNum = lineNum;
	temp->wordNum = wordNum;
	temp->next = NULL;

	if (tail == NULL)
		return temp;
	else
		tail->next = temp; 
		return temp; 
	return 0;
}

Node *rmFromHead(Node *head, char *line, int *lineNum, int *wordNum)
{
	if (head == NULL)
		return NULL;

	strcpy(line, head->line); 
	*lineNum = head->lineNum;
	*wordNum = head->wordNum;

	if (head->next == NULL)
		return NULL;
	else
		head = head->next; 
		return head; 
	return 0;
}

void printList(Node *head)
{
	Node *temp = head;
	while (temp != NULL) 
	{
		printf("Node:\n-line: %s\n-lineNum: %d\n-wordNum: %d\n\n", temp->line, temp->lineNum, temp->wordNum); 
		temp = temp->next;
	}
	return;
}