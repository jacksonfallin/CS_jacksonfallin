#include<stdio.h>
#include "wordList.c"

void main()
{
	Node *head = NULL;
	char str[100];
	char word[100];
	char *ptr;
	int lineNum = 0;
	int wordPos = 0;
	FILE * fp = fopen("StringData.txt", "r");
	printf("\n Enter a word: ");
	gets(word);
	if (fp == NULL)
		printf("Cannot open input file\n");
	while (!feof(fp)) // Not end of file
	{
		lineNum++;
		fgets(str, 100, fp); // read 100 characters
		ptr = (char*)strtok(str, " "); // split our findings around the " "
		wordPos = 0;
		while (ptr != NULL) // while there's more to the string
		{
			wordPos++;
			ptr = (char*)strtok(NULL, " "); // and keep splitting
			if (strcasecmp(ptr, word) == 0)
			{
				head = addToTail(head, str, lineNum, wordPos);
				head->lineNum;
			}
		}
	}
	printList(head);
	fclose(fp);
}