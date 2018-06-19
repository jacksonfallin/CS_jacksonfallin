;-------------------------------------editing the function-------------------------
;--------set up self---------

.ORIG X3300

; save space for return value and return &
REC	ADD R6, R6, #-2
STR R7, R6, #0	; push return address

; push dynamic link
ADD R6, R6, #-1
STR R5, R6, #0

; new frame pointer
ADD R5, R6, #-1

; new stack pointer
; only one local var because we eval one char at a time
ADD R6, R6, #-1

;-----------------charcount code---------------

; clear R0 to store key
	AND R0, R0, #0
LDR R0, R5, #5	; R0 now has the key
LDR R1, R5, #4	; R1 now has address of string

	; if (*str == ‘\0’)
	LDR R2, R1, #0	; load first char into R2
				; R2 has char
ADD R3, R2, #0 	; check for a newline
	BRz Z
	BRnzp EQUAL
Z	AND R3, R3, #0
	STR R3, R5, #0	; store 0 into result if not equal to key
	BRnzp DONE

	; if (*str == key)
EQUAL NOT R2, R2		; makes char negative
	ADD R2, R2, #1
ADD R3, R2, R0	; check if char == key
	BRz ONE
	BRnzp ZERO
ONE	AND R3, R3, #0	; clear R3
	ADD R3, R3, #1
	STR R3, R5, #0	; store 1 into result if equal to key
	BRnzp X

	; if (*str != key)
ZERO	AND R3, R3, #0
	STR R3, R5, #0	; store 0 into result if not equal to key

;------------------func setup-------------------
; set up parameters for recursion
X	ADD R6, R6, #-1 	; push first argument
	STR R0, R6, #0
	ADD R6, R6, #-1	; push second argument
	LDR R3, R5, #4	; put string address into R3
	ADD R3, R3, #1	; increment address
	STR R3, R6, #0	; store second argument
	
	JSR REC		; assume there’s a value in the return value spot

;-----------------func teardown--------------------

; return result
DONE	LDR R0, R5, #0
	STR R0, R5, #3

	; pop local vars
	ADD R6, R5, #1

	; pop dynamic link
	LDR R5, R6, #0
	ADD R6, R6, #1

	; restore return address
	LDR R7, R6, #0
	ADD R6, R6, #1	; R6 is at the return val

;-----------------self teardown-----------------------

	; save return value into R0
LDR R0, R6, #0
; pop return value and params
ADD R6, R6, #3
; place result into R1
LDR R1, R6, #0
; add the ret val to the result and push back to result spot
ADD R0, R1, R0	; result + ret val
STR R0, R6, #0	; store new result
STR R0, R6, #3	; store result local into the return value
RET
.END
