import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AmazonScraper {
    private static final int MAX_DEPTH = 2;
    private Set<String> visitedUrls = new HashSet<>();

    public static void main(String[] args) {
        AmazonScraper scraper = new AmazonScraper();
        scraper.crawl("https://www.amazon.in/dp/B0D1FVLVN6/ref=sspa_dk_detail_1?psc=1&pd_rd_i=B0D1FVLVN6&pd_rd_w=o8d1f&content-id=amzn1.sym.9f1cb690-f0b7-44de-b6ff-1bad1e37d3f0&pf_rd_p=9f1cb690-f0b7-44de-b6ff-1bad1e37d3f0&pf_rd_r=EY9K60AVQ8HTTRPD920N&pd_rd_wg=K3X8g&pd_rd_r=4f0818eb-a1bc-4c2f-85d0-45befc3f8538&sp_csd=d2lkZ2V0TmFtZT1zcF9kZXRhaWxfdGhlbWF0aWM", 0);
    }

    public void crawl(String url, int depth) {
        if (depth > MAX_DEPTH || visitedUrls.contains(url)) {
            return;
        }

        try {
            visitedUrls.add(url);
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .get();
            System.out.println("Crawling URL: " + url);

            // Scrape the current page
            scrapePage(document);

            // Find all links on the current page and crawl them
            Elements links = document.select("a[href]");
            for (Element link : links) {
                String subUrl = link.attr("abs:href");  // Get absolute URL
                crawl(subUrl, depth + 1);
            }
        } catch (IOException e) {
            System.err.println("Error fetching the URL: " + url);
            e.printStackTrace();
        }

        // Delay between requests to avoid being blocked
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void scrapePage(Document document) {
        String title = document.select("#productTitle").text();
        String price = document.select(".priceToPay span.a-price-whole").text();
        if (!title.isEmpty() && !price.isEmpty()) {
            System.out.println("Product: " + title + " Price: ₹" + price);
        } else {
            System.out.println("NO Product");
        }
    }
}