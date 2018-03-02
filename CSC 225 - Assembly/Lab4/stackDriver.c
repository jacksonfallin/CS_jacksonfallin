#include <stdio.h>
#include <stdlib.h>
#include "stack.h"

int main()
{
	int stack[10];
	char ch;
	int count = 0;
	int n;
	int mode = 0;
	printf("%s", "Welcome to the stack program.\n");
	while (1)
	{
		printf("\nEnter option: ");
		scanf(" %c", &ch);
		if (ch == '+')
		{
			printf("What number? ");
			scanf(" %d", &n);
			if (push(stack, &count, n) == 0)
			{
				printStack(stack, count, mode);
			}
			else
			{
				printf("Error: Stack overflow!\n");
				printStack(stack, count, mode);
			}
		}
		else if (ch == '-')
		{
			if (pop(stack, &count, &n) == 0)
			{
				printf("Popped %d.\n", n);
				printStack(stack, count, mode);
			}
			else
			{
				printf("Error: Stack underflow!\n");
				printf("Stack: []\n");
			}
		}
		else if (ch == 'x')
		{
			mode = 1;
			printStack(stack, count, mode);
		}
		else if (ch == 'd')
		{
			mode = 0;
			printStack(stack, count, mode);
		}
		else if (ch == 'c')
		{
			mode = 2;
			printStack(stack, count, mode);
		}
		else if (ch == 'q')
		{
			printf("%s", "Goodbye!\n");
			exit(0);
		}
		else
		{
			printStack(stack, count, mode);
		}
	}
	return 0;
}