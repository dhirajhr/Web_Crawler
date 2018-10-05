# Web Crawler
The maximum pages to fetch can be set in crawler4j and is set to 20,000 to ensure a
reasonable execution time for this exercise. Also, maximum depth should be set to 16 to ensure that
we limit the crawling. <br/>

# fetch_NewsSite.csv 
The URLs it attempts to fetch, a two column spreadsheet, column 1 containing the URL and
column 2 containing the HTTP status code received. <br/>

2. the files it successfully downloads, a four column spreadsheet, column 1 containing the
URLs successfully downloaded, column 2 containing the size of the downloaded file (in
Bytes, or you can choose your own preferred unit (bytes,kb,mb)), column 3 containing
the # of outlinks found, and column 4 containing the resulting content-type; name the file
visit_NewsSite.csv; clearly the number of rows will be less than the number of rows in
fetch_NewsSite.csv
3. all of the URLs (including repeats) that were discovered and processed in some way; a two
column spreadsheet where column 1 contains the encountered URL and column two an
indicator of whether the URL a. resides in the website (OK), or b. points outside of the
website (N_OK). (A file points out of the website if its URL does not start with the initial
domain name, e.g. when crawling USA Today news website all inside URLs must start with
www.usatoday.com.) Name the file urls_NewsSite.csv. 
