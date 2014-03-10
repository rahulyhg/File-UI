FileUI webpage 
https://www.cs.drexel.edu/~qy44/FileUI/

What needs to add for future versions

	store pwd on a stack when doing any get, put rm, ls...
		scenario:
			I do a download, loading process UI is showing. If I click on any icon, exception occurs.
			This is because I cd inside the download recursive calls. By click on another icon, 
			the program cd into something else and thus not matching with the cd in download anymore


