package org.onedatashare.server.module.http;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.onedatashare.server.model.core.Resource;
import org.onedatashare.server.model.core.Stat;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class HttpResource extends Resource<HttpSession, HttpResource> {
    private String uri;

    // Max recursion depth
    static final int maxDepth = 10;

    protected HttpResource(HttpSession session, String uri) {
        super(session);
        this.uri = uri;
    }

    @Override
    public Mono<HttpResource> select(String path) {
        return null;
    }

    public Mono<Stat> stat() {
        return Mono.just(onStat());
    }

    public Stat onStat() {
        Stat stat = new Stat();
        URI uriType = URI.create(uri);

        // Get the hostname from the uri
        stat.name = uriType.toString();

        Document document = null;

        try {
            document = Jsoup.connect(uri).get();
            document.select("th").remove();
        } catch (IOException e) {
            return stat;
        }

        Elements table = document.select("tr");

        if (table.size() >= 2) {
            //Remove the header and 1st row of the table
            table.remove(0);
            table.remove(1);

            Stat contentStat;
            ArrayList<Stat> contents = new ArrayList<>(table.size());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

            for (Element row : table) {
                try {
                    Elements rowContent = row.select("td");
                    if (rowContent.size() == 0)
                        continue;
                    contentStat = new Stat();
                    String fileName = rowContent.get(1).text();
                    String dateString = rowContent.get(2).text();
                    if (fileName.endsWith("/")) {
                        contentStat.name = fileName.substring(0, fileName.length() - 1);
                        contentStat.dir = true;
                        contentStat.file = false;
                    } else {
                        contentStat.name = fileName;
                        contentStat.dir = false;
                        contentStat.file = true;
                        contentStat.size = SizeParser.getBytes(rowContent.get(3).text());
                    }

                    if (!dateString.equals("")) {
                        Date d = sdf.parse(dateString);
                        contentStat.time = d.getTime() / 1000L;
                    }
                    contents.add(contentStat);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
            stat.setFiles(contents);
        } else {
            Elements links = document.select("a[href]");
            Stat contentStat;
            ArrayList<Stat> contents = new ArrayList<>(links.size());
            for (Element link : links) {
                contentStat = new Stat();
                String fileName = link.text();
                if (fileName.endsWith("/")) {
                    contentStat.name = fileName.substring(0, fileName.length() - 1);
                    contentStat.dir = true;
                    contentStat.file = false;
                } else {
                    contentStat.name = fileName;
                    contentStat.dir = false;
                    contentStat.file = true;
                }
                contents.add(contentStat);
            }
            stat.setFiles(contents);
        }
        return stat;
    }

    @Override
    public Mono<Stat> getTransferStat() {
//        Stat stat = new Stat();
//        URI uriType = URI.create(uri);
//
//        // Get the hostname from the uri
//        stat.name = uriType.toString();
//
//        Document document = null;
//
//        LinkedList<String> linksToVisit = new LinkedList<>();
//
//        try {
//            document = Jsoup.connect(uri).get();
//            document.select("th").remove();
//        } catch (IOException e) {
//            return Mono.just(null);
//        }
//
//        Elements table = document.select("tr");
//
//        if (table.size() >= 2) {
//            //Remove the header and 1st row of the table
//            table.remove(0);
//            table.remove(1);
//
//            Stat contentStat;
//            ArrayList<Stat> contents = new ArrayList<>(table.size());
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//
//            for (Element row : table) {
//                try {
//                    Elements rowContent = row.select("td");
//                    if (rowContent.size() == 0)
//                        continue;
//                    contentStat = new Stat();
//                    String fileName = rowContent.get(1).text();
//                    String dateString = rowContent.get(2).text();
//                    if (fileName.endsWith("/")) {
//                        contentStat.name = fileName.substring(0, fileName.length() - 1);
//                        contentStat.dir = true;
//                        contentStat.file = false;
//                    } else {
//                        contentStat.name = fileName;
//                        contentStat.dir = false;
//                        contentStat.file = true;
//                        contentStat.size = SizeParser.getBytes(rowContent.get(3).text());
//                    }
//
//                    if (!dateString.equals("")) {
//                        Date d = sdf.parse(dateString);
//                        contentStat.time = d.getTime() / 1000L;
//                    }
//                    contents.add(contentStat);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    break;
//                }
//            }
//            stat.setFiles(contents);
//        } else {
//            Elements links = document.select("a[href]");
//            Stat contentStat;
//            ArrayList<Stat> contents = new ArrayList<>(links.size());
//            for (Element link : links) {
//                contentStat = new Stat();
//                String fileName = link.text();
//                if (fileName.endsWith("/")) {
//                    contentStat.name = fileName.substring(0, fileName.length() - 1);
//                    contentStat.dir = true;
//                    contentStat.file = false;
//                } else {
//                    contentStat.name = fileName;
//                    contentStat.dir = false;
//                    contentStat.file = true;
//                }
//                contents.add(contentStat);
//            }
//            stat.setFiles(contents);
//        }
//        return Mono.just(stat);
        System.out.println("get transfer stat called");
        return Mono.just(null);
    }


    private static class SizeParser {

        public static long getBytes(String sizeString) {

            String multiplier = sizeString.replaceAll("\\D+", "");
            String digits = sizeString.replaceAll("[^\\d]", "");

            // No size information available
            if (digits.equals(""))
                return 0;

            float size = Float.parseFloat(digits);

            final long K_FACTOR = 1024;
            final long M_FACTOR = 1024 * K_FACTOR;
            final long G_FACTOR = 1024 * M_FACTOR;

            switch (multiplier) {
                case "G":
                    size *= G_FACTOR;
                    break;
                case "M":
                    size *= M_FACTOR;
                    break;
                case "K":
                    size *= K_FACTOR;
                    break;
            }
            return Math.round(size);
        }
    }
}
