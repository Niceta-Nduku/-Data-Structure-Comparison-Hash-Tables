# -Data-Structure-Comparison-Hash-Tables

To run the PowerHashApp, in NDKNIC001_PRAC3
	
	make
	cd bin
	java PowerHashApp 700 l cleaned_data.csv 40 

the first argument is the table size, the second is the collision resolution
to be used, the third is the file name and last is the number of search keys. 

If it is the experiment, simply run

	java PowerHashApp test cleaned_data.csv 

The output can be redirected to a file. e.g.

	java PowerHashApp test cleaned_data.csv >../Experiment.txt


	
