        .ORIG x3500

        ; Question 1
Q1STR   .FILL Q1  ; Address of prompt
Q1PTS   .FILL #5 ; Point value for option 1
        .FILL #10  ; Point value for option 2
        .FILL #2  ; Point value for option 3
        .FILL #0  ; Point value for option 4

        ; Question 2
Q2STR   .FILL Q2
Q2PTS   .FILL #1
        .FILL #10
        .FILL #0
        .FILL #2

        ; Question 3
Q3STR   .FILL Q3
Q3PTS   .FILL #5
        .FILL #7
        .FILL #0
        .FILL #10

        ; Results
RESULTS .FILL GOODMSG
        .FILL PASSMSG
        .FILL FAILMSG

        ; Strings must be declared separately because their lengths are variable.
Q1      .STRINGZ "\nWhat is the meaning of life, the universe, and everything?\n    1) Love\n    2) 42\n    3) There is no meaning\n    4) I don't care\n"
Q2      .STRINGZ "\nWhat’s the name of Captain Mal’s ship from the show Firefly?\n    1) Harmony\n    2) Serenity\n    3) The Jolly Roger\n    4) The Enterprise\n"
Q3      .STRINGZ "\nWhat are the two races that go to war in the show Battlestar Galactica?\n    1) Humans and Animals\n    2) Gorgons and Cylons\n    3) Freaks and Geeks\n    4) Humans and Cylons\n"

GOODMSG .STRINGZ "NEEEEEEEERRRD."
PASSMSG .STRINGZ "Congrates, you're average."
FAILMSG .STRINGZ "YoU fAiLeD!"

        .END