# Base

I have covered below scenarios apart from happy flow:

We can add any number of columns/rows in CSV File 1 for sorting.

We can add any number of columns/rows in CSV File 2 for sorting.

We can pass any header value in the argument 2 & 4.

We can pass only 4 arguments when compile code. It will throw error if arguments are < 4 & > 4.

If input argument 2 does not exist in CSV file 1, it will show proper error message.

Likewise, If argument 4 does not exist in CSV file 2, it will show proper error message.

Exception handled properly.

Parameters to run this program:

java SortCSVFileService ./base_file.csv a ./sorting_file.csv d java [your-main-class] [file-to-be-sorted] [column-to-sort-on] [sort-order-d
