	                                ; Jackson Fallin
	.ORIG x3000       		; Starts program at location x3000
 	AND R1, R1, #0			; Clears R1
 	ADD R1, R1, #1			; Sets R1 as the mask
 	LEA R0, PROMPT1			; Load address of the PROMPT into R0 and ask for encryption key
 	PUTS				; Write PROMPT1 to the screen
 	GETC				; Reads encryption key
 	OUT            			; Prints key to the screen
 	ADD R2, R0, #0 			; Stores encryption key
	AND R2, R2, #15			; Sets key
 	LEA R0, PROMPT2			; Asks user for message to be encrypted
 	PUTS				; Write PROMPT2 to the screen
 	LEA R4, MESSAGE			; Allocates space for 20 character message
LOOP1 	GETC 				; Reads character input
 	OUT				; Prints char to screen
	ADD R6, R0, #-10		; Check if input is ‘enter’
	ADD R6, R6, #0			; Ensures branch checks R6
	BRz LOOP3			; Branches out of the loop if there’s ‘enter’
 	STR R0, R4, #0			; Stores character to MESSAGE block
	ADD R4, R4, #1			; Increments address of MESSAGE
	ADD R0, R0, #0			; Ensures condition checks R0
	BRp LOOP1			; Branches to LOOP1
	
LOOP3 	LEA R3, ENCRYPTED		; Allocates space for the encrypted message
	LEA R4, MESSAGE			; Get first char from MESSAGE
	LEA R0, PROMPT3			; Message to be printed
	PUTS				; Prints encrypted prompt to the screen
LOOP2 	LDR R5, R4,  #0			; Load data from MESSAGE and store in R5
	BRz FINAL			; Branches out of loop when R5 is zero
	AND R6, R1, R5                   ; AND the mask with the char and store in R6
	BRp ODD				; Branches if mask is odd
	BRnzp EVEN			; Branches if mask is even
ODD	ADD R6, R5, #-1			; Toggle right most bit to zero
	BRnzp KEY			; Ensures addition only happens once
EVEN	ADD R6, R5, #1			; Toggle right most bit to one
KEY 	ADD R6, R2, R6			; ADD key to char data
	STR R6, R3, #0			; Store the encrypted char into ENCRYPTED block

	AND R0, R0, #0			; Clears R0
	ADD R0, R3, R0			; Stores encrypted address to R0 to print
	PUTS				; Prints encrypted character to the screen
	
	ADD R3, R3, #1			; Increments address for ENCRYPTED
	ADD R4, R4, #1			; Increments address for MESSAGE
	ADD R5, R5, #0			; Ensures branch checks MESSAGE has characters
	BRp LOOP2			; Loops until it reaches null
	
FINAL   LEA R4, ENCRYPTED		; Loads address of ENCRYPTED to clear data
ERASE	LDR R1, R4, #0			; Load data into R1
	BRz DONE			; Stops erasing data when character is null
	AND R1, R1, #0			; Clears R1 to make it 0
	STR R1, R4, #0			; Stores null in ENCRYPTED
	LDR R7, R4, #0			; Load char into R7
	ADD R4, R4, #1			; Increments address of ENCRYPTED block
	BRnzp ERASE

DONE  	LEA R4, MESSAGE			; Loads address of MESSAGE to clear data
ERASE2 	LDR R1, R4, #0			; Load data into R1
	BRz ZERO			; Stops erasing data when character is null
	AND R1, R1, #0			; Clears R1 to make it 0
	STR R1, R4, #0			; Stores null in MESSAGE
	LDR R7, R4, #0			; Load char into R7
	ADD R4, R4, #1			; Increments address of MESSAGE block
	BRnzp ERASE2			; Loops to ERASE2

ZERO HALT

PROMPT1 	.STRINGZ "\nEncryption Key (1-9): "
PROMPT2 	.STRINGZ "\nInput message: "
PROMPT3 	.STRINGZ "\nEncrypted Message: "
MESSAGE		.BLKW #21
ENCRYPTED    	.BLKW #21
		.END