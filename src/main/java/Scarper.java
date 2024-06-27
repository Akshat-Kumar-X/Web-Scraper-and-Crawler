import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

class Scraper {
    private static final int MAX_DEPTH = 1;
    private Set<String> visitedUrls = new HashSet<>();

    public static void main(String[] args) {
        Scraper scraper = new Scraper();
        scraper.crawl("https://www.amazon.in/Redgear-MP35-Speed-Type-Gaming-Mousepad/dp/B01J1CFO5I/", 0);
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
            Thread.sleep(1000);
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
