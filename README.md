Installation equirements

1) Java 7
2) NetBeans
3) A Linux server
4) An account on the Linux server


Installation guide

1) Clone or fork this repo
2) Open with NetBeans
3) Make changes
4) Add, commit, push

FileUI webpage 
https://www.cs.drexel.edu/~qy44/FileUI/

What needs to add for future versions

	store pwd on a stack when doing any get, put rm, ls...
		scenario:
			I do a download, loading process UI is showing. If I click on any icon, exception occurs.
			This is because I cd inside the download recursive calls. By click on another icon, 
			the program cd into something else and thus not matching with the cd in download anymore


