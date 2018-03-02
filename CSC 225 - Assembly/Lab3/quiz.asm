 ; CSC 225
 ; Gabriella Santiago, Jackson Fallin

        .ORIG x3000

; --------------------Main------------------------

	; Q1
	LDI R0, Q1STR
	AND R3, R3, #0
	JSR GETQ
	LD R1, Q1PTS
	JSR GETP
	
	; Q2
	LDI R0, Q2STR
	JSR GETQ
	LD R1, Q2PTS
	JSR GETP

	; Q3
	LDI R0, Q3STR
	JSR GETQ
	LD R1, Q3PTS
	JSR GETP
	LEA R0, RES
	PUTS
	LD R0, RESULTS
	JSR SCORE
	ADD R0, R1, R0
	LDR R0, R0, #0
	PUTS
	
        HALT
ANS	.STRINGZ "\nAnswer: "
RES	.STRINGZ "\nResult: "
Q1STR   .FILL x3500
Q1PTS   .FILL x3501
Q2STR   .FILL x3505
Q2PTS   .FILL x3506
Q3STR   .FILL x350A
Q3PTS   .FILL x350B
RESULTS .FILL x350F

; -----------------GETQ-------------------------
; GETQ prints the question string and answer prompt

GETQ	ST R7, SAVER7
	PUTS
	LEA R0, ANS
	PUTS
	TRAP x26
	LD R7, SAVER7
	RET
SAVER7	.FILL x0000

; -----------------GETP--------------------------
; GETP gets the point value of the question and 
; stores the point value into R3

GETP	ST R1, SAVER1
	ST R2, SAVER2

	ADD R0, R0, x-1
	ADD R1, R0, R1
	LDR R2, R1, #0 ; loads the address of where the point value is
	ADD R3, R2, R3

	LD R1, SAVER1
	LD R2, SAVER2

	RET
SAVER1	.FILL x0000
SAVER2	.FILL x0000

; ------------------SCORE------------------------
; Score checks if the user did well, passed, or failed the quiz

SCORE	ST R2, SAVE_2
	ST R4, SAVE_4

	LD R2, NUM1
	ADD R4, R3, R2
	LD R1, FAIL
	ADD R4, R4, #0
	BRnz DONE
	LD R2, NUM2
	ADD R4, R3, R2
	LD R1, PASS
	ADD R4, R4, #0
	BRnz DONE
	LD R1, GOOD

DONE	LD R2, SAVE_2
	LD R4, SAVE_4
	RET
NUM1	.FILL x-14
NUM2	.FILL x-1A
FAIL	.FILL x0002
PASS	.FILL x0001
GOOD	.FILL x0000
SAVE_2	.FILL x0000
SAVE_4	.FILL x0000

	.END