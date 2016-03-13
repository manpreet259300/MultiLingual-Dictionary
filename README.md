# MultiLingual-Dictionary
using Map Reduce the multilingual dictionary is obtained for he languages include French, German, Italian, Portuguese, Spanish, and Latiin.


# Do the sandbox installation
# preferably setup the mapr environment to support normal linux commands like cat, vi etc..
=====================
--------------------------------------E1 and E2----------------------------------------------------
use update-rebuild script to make classes from java files
use update-rerun script to run the mapper and redducer class using the driver class.

==========================================================================================
functionality of the programs
-------------------------E1--------------------------------
# applied regex to remove the lines of inpput that are not required
# Used composite keys on word (starting word in the line) and part of speech (present at the end of line).
# made composite value of language name and the meaning in that particular language( e.g.  french:hello).
# if that particular word is not present in that language then N/A is provided in front of the language.
# used 5 mappers(5 input file i.e 5 languages) and 1 reducers.
# output is pipe seperated

-------------------------E2--------------------------------
# new language latin is added in the output file
# Almost all work is done in mapper
# no need of reducer in this
# used 1 mapper
# latin.txt file is combined with output from E1.
# cache distribution is used to provide the details of latin.txt to E1 output.
# reges is applied to latin.txt to get all the valid records.
