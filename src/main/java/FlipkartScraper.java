import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FlipkartScraper {
    private static final int MAX_DEPTH = 1;
    private Set<String> visitedUrls = new HashSet<>();

    public static void main(String[] args) {
        FlipkartScraper scraper = new FlipkartScraper();
        scraper.crawl("https://www.flipkart.com/tyy/4io/~cs-6ky41lperx/pr?sid=tyy%2C4io&collection-tab-name=POCO+M6+Pro+5G&param=3243&ctx=eyJjYXJkQ29udGV4dCI6eyJhdHRyaWJ1dGVzIjp7InZhbHVlQ2FsbG91dCI6eyJtdWx0aVZhbHVlZEF0dHJpYnV0ZSI6eyJrZXkiOiJ2YWx1ZUNhbGxvdXQiLCJpbmZlcmVuY2VUeXBlIjoiVkFMVUVfQ0FMTE9VVCIsInZhbHVlcyI6WyJGcm9tIOKCuTksMjQ5KiJdLCJ2YWx1ZVR5cGUiOiJNVUxUSV9WQUxVRUQifX0sInRpdGxlIjp7Im11bHRpVmFsdWVkQXR0cmlidXRlIjp7ImtleSI6InRpdGxlIiwiaW5mZXJlbmNlVHlwZSI6IlRJVExFIiwidmFsdWVzIjpbIlBvY28gTTYgUHJvIDVHIl0sInZhbHVlVHlwZSI6Ik1VTFRJX1ZBTFVFRCJ9fSwiaGVyb1BpZCI6eyJzaW5nbGVWYWx1ZUF0dHJpYnV0ZSI6eyJrZXkiOiJoZXJvUGlkIiwiaW5mZXJlbmNlVHlwZSI6IlBJRCIsInZhbHVlIjoiTU9CR1JOWjNFUjROM0s0RiIsInZhbHVlVHlwZSI6IlNJTkdMRV9WQUxVRUQifX19fX0%3D", 0);
    }

    public void crawl(String url, int depth) {
        if (depth > MAX_DEPTH || visitedUrls.contains(url)) {
            return;
        }

        try {
            visitedUrls.add(url);
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .referrer("http://www.google.com")
                    .timeout(10000)  // Increase timeout to 10 seconds to scrape the page.
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
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void scrapePage(Document document) {
        Elements productElements = document.select(".KzDlHZ");
        Elements priceElements = document.select(".hl05eU ._4b5DiR");

        List<String> products = new ArrayList<>();
        List<String> prices = new ArrayList<>();

        for (Element productElement : productElements) {
            products.add(productElement.text());
        }

        for (Element priceElement : priceElements) {
            prices.add(priceElement.text());
        }

        for (int i = 0; i < products.size() && i < prices.size(); i++) {
            System.out.println("Product: " + products.get(i) + " Price: " + prices.get(i));
        }

        if (products.size() != prices.size()) {
            System.out.println("Mismatch between the number of products and prices.");
        }
    }
}
