package webcrawler.Web_Crawler;
import java.io.*;
import java.util.List;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;



import org.apache.log4j.*;

import domain.DiscoveredURLNYTimes;
import domain.DownloadFileNYTimes;
import domain.URLFetchNYTimes;


public class Controller {
	public final static String CRAWL_SITE = "https://www.nytimes.com/";
	public final static String crawlStorageFolder = "C://Users/dramn/eclipse-workspace-2018/Web_Crawler/results";
	private static String fetchFile = "fetch.csv";
	private static String downloadFile = "visit.csv";
	private static String urlsFile = "url.csv";
	private static String reportFile = "report.txt";
	
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		
        int numberOfCrawlers = 5;
        int maxPagesToFetch = 20000;//20000
        int maxDepthOfCrawling = 16;
        int politenessDelay = 200;
        
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        // maximum page Set ---- 20000
        config.setMaxPagesToFetch(maxPagesToFetch);
        // maximum depth ---- 16
        config.setMaxDepthOfCrawling(maxDepthOfCrawling);
        // one request per four second ---- According to the Robots.txt
//        config.setPolitenessDelay(politenessDelay);
        config.setIncludeHttpsPages(true);
        config.setIncludeBinaryContentInCrawling(true);
        config.setFollowRedirects(true);
//        config.setResumableCrawling(true); // can resume from the last break point
        
        /*
         * Instantiate the controller for this crawl.
         */
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
		/*
		* For each crawl, you need to add some seed urls. These are the first
		* URLs that are fetched and then the crawler starts following links
		* which are found in these pages
		*/
		controller.addSeed(CRAWL_SITE);
		controller.addSeed("https://www.nytimes.com/"); // long time to response
		controller.addSeed("http://www.nytimes.com/");
		controller.addSeed("http://nytimes.com/");
		/*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(MyCrawler.class, numberOfCrawlers);
        
        
        List<URLFetchNYTimes> fetchUrlList = MyCrawler.fetchUrlList;
//        for (URLFetchNYTimes fetch: fetchUrlList) {
//	        	System.out.println(fetch.getUrl() + "    " + fetch.getHttpStatusCode());
//        }
        List<DownloadFileNYTimes> downloadFileList = MyCrawler.downloadFileList;
//        for (DownloadFileNYTimes download: downloadFileList) {
//        		System.out.println(download.getUrl() + "    " + 
//	        			download.getNumOfOutlink() + "    " + download.getFileSize() + 
//	        			"    " + download.getContentType());
//        }
        List<DiscoveredURLNYTimes> visitUrlList = MyCrawler.visitUrlList;
//        for (DiscoveredURLNYTimes dis: visitUrlList) {
//        		System.out.println(dis.getUrl() + "    " + dis.getIs_ok());
//        }
        
        // ----------------
        File fetch_CSpan = new File(crawlStorageFolder + "/" + fetchFile);
        BufferedWriter bw = new BufferedWriter(new FileWriter(fetch_CSpan, true));
        bw.write("url,httpStatusCode");
        bw.flush();
        bw.newLine();
        for (URLFetchNYTimes fetch: fetchUrlList) {
	    		bw.write(fetch.getUrl() + "," + fetch.getHttpStatusCode());
	    		bw.flush();
	    		bw.newLine();
	    }
        bw.close();
        
        File download_CSpan = new File(crawlStorageFolder + "/" + downloadFile);
        bw = new BufferedWriter(new FileWriter(download_CSpan, true));
        bw.write("url,FileSize,NumOfOutLink,ContentType");
        bw.flush();
        bw.newLine();
        
        for (DownloadFileNYTimes download: downloadFileList) {
	    		bw.write(download.getUrl() + ","  + download.getFileSize() + " KB" + "," 
	    				+ download.getNumOfOutlink() + "," + download.getContentType());
	    		bw.flush();
	    		bw.newLine();
	    }
        bw.close();
        
        File urls_CSpan = new File(crawlStorageFolder + "/" + urlsFile);
        bw = new BufferedWriter(new FileWriter(urls_CSpan, true));
        bw.write("url,is_ok");
        bw.flush();
        bw.newLine();
        for (DiscoveredURLNYTimes dis: visitUrlList) {
        		bw.write(dis.getUrl() + "," + dis.getIs_ok());
        		bw.flush();
        		bw.newLine();
        }
        bw.close();
        
        File report_CSpan = new File(crawlStorageFolder + "/" + reportFile);
        bw = new BufferedWriter(new FileWriter(report_CSpan, true));
        bw.write("Name: Qixiong Liu\n");
        bw.write("USC ID: 9502485887\n");
        bw.write("News site crawled: " + CRAWL_SITE + "\n");
        bw.newLine();
        bw.write("Fetch Statistics\n");
        bw.write("================\n");
        bw.write("# fetches attempted: " + fetchUrlList.size() + "\n");
        bw.write("# fetches succeeded: " + MyCrawler.fetch_succeeded + "\n");
        bw.write("# fetches failed or aborted: " + MyCrawler.fetch_failed_or_aborted + "\n");
        bw.newLine();
        bw.write("Outgoing URLs\n");
        bw.write("=============\n");
        bw.write("Total URLs extracted: " + MyCrawler.total_urls + "\n");
        bw.write("# unique URLs extracted: " + MyCrawler.unique_url + "\n");
        bw.write("# unique URLs within News Site: " + MyCrawler.unique_url_within + "\n");
        bw.write("# unique URLs outside News Site: " + MyCrawler.unique_url_outside + "\n");
        bw.newLine();
        bw.write("Status Codes\n");
        bw.write("============\n");
        bw.write("200 OK: " + MyCrawler.codeCount_200 + "\n");
        bw.write("301 Moved Permanently: " + MyCrawler.codeCount_301 + "\n");
        bw.write("302 Found: " + MyCrawler.codeCount_302 + "\n");
        bw.write("307 Temporary Redirect: " + MyCrawler.codeCount_307 + "\n");
        bw.write("404 Not Found: " + MyCrawler.codeCount_404 + "\n");
        bw.write("410 Gone: " + MyCrawler.codeCount_410 + "\n");
        bw.newLine();
        bw.write("File Sizes\n");
        bw.write("==========\n");
        bw.write("< 1KB: " + MyCrawler.fileCount_less_than_1KB + "\n");
        bw.write("1KB ~ <10KB: " + MyCrawler.fileCount_between_1KB_and_10KB + "\n");
        bw.write("10KB ~ <100KB: " + MyCrawler.fileCount_between_10KB_and_100KB + "\n");
        bw.write("100KB ~ <1MB: " + MyCrawler.fileCount_between_100KB_and_1024KB + "\n");
        bw.write(">= 1MB: " + MyCrawler.fileCount_more_than_1MB + "\n");
        bw.newLine();
        bw.write("Content Types\n");
        bw.write("=============\n");
        bw.write("text/html: " + MyCrawler.contentType_html + "\n");
        bw.write("image/gif: " + MyCrawler.contentType_gif + "\n");
        bw.write("image/tif: " + MyCrawler.contentType_tif + "\n");
        bw.write("image/jpeg: " + MyCrawler.contentType_jpeg + "\n");
        bw.write("image/png: " + MyCrawler.contentType_png + "\n");
        bw.write("application/pdf: " + MyCrawler.contentType_pdf + "\n");
        bw.close();
        
        
        
        
        long end = System.currentTimeMillis();
        System.out.println("Total Time USe: " + (end-start) / 1000 / 60 + "min");
	}
}