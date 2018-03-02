#include <stdio.h>
#include "stack.h"

/**
* * Pushes a value onto a stack of integers.
* * stack - A pointer to the array containing the stack
* * size - A pointer to the number of elements in the stack
* * val - The value to push
* * Returns 0 on success, 1 on overflow.
* */
int push(int *stack, int *size, int val)
{
	if (*size == MAX_SIZE)
		return 1;

	stack[*size] = val;
	*size = *size + 1;

	return 0;
}

/**
* * Pops a value off of a stack of integers.
* * stack - A pointer to the array containing the stack
* * size - A pointer to the number of elements in the stack
* * val - A pointer to the variable in which to place the popped value
* * Returns 0 on success, 1 on underflow.
* */
int pop(int *stack, int *size, int *val)
{
	if (*size == 0)
		return 1;
	*size = *size - 1;
	*val = stack[*size];
	return 0;
}

/**
* * Prints a stack of integers.
* * stack - A pointer to the array containing the stack
* * size - The number of elements in the stack
* * mode - How to print elements, one of: DEC_MODE, HEX_MODE, or CHAR_MODE
* */
void printStack(int *stack, int size, int mode)
{
	int i = 0;
	if (size == 0)
	{
		printf("Stack: [");
		printf("]\n");
		return;
	}

	printf("Stack: [");
	for (i = 0; i<size; i++)
	{
		if (i > 0)
			printf(", ");
		if (mode == 0)
			printf("%d", stack[i]);
		if (mode == 1)
		{
			printf("0x%X", stack[i]);
		}
		if (mode == 2)
			printf("'%c'", stack[i]);

	}
	printf("]\n");
}