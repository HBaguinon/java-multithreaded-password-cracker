/*******************************************************************************
* NAME: HAROLD DANE C. BAGUINON                                                *
* DATE: 11/09/2016                                                             *
* DATE DUE: 12/14/2016 06:00:00 PM                                             *
* COURSE: CSC543                                                               *
* PROFESSOR: DR. PARSON                                                        *
* PROJECT: #4                                                                  *
* FILENAME: Readme.txt                                                         *
* PURPOSE: This program is the fourth assignment.                              *
*          Students were asked to write a program which uses multithreading.   *
*          As one of my focuses in the PSM Track is Security, I decided to     *
*          engineer a brute-force password cracker. The four types of crackers *
*          which I have coded are:                                             *
*          1. SingleCracker: Single-threaded, randomized word-building.        *
*          2. MultiCracker: Multi-threaded, randomized word-building.          *
*          3. SeqCracker: Single-threaded, sequential word-building. "a to z"  *
*          4. SplitCracker: Multi-threaded, sequential word-building. "a to z" *
*******************************************************************************/

These are not actually password crackers. They only simulate the brute-force
method of cracking a password. There are two single-threaded versions and two
multi-threaded versions. And of each threading type, there is one sequential
version, which goes through the alphabet systematically, and there is one
randomized word-building. The sequential ones have generally consistent time to
completion and are somewhat predictable. The randomized ones are not predictable
and as such, occasionally solve the password faster than the sequential type.
Oh average, however, the sequential ones are faster, more consistent, and thus,
more reliable.