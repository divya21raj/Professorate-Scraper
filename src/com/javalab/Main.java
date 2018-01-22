package com.javalab;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        Document doc = Jsoup.connect("http://snu.edu.in/theuniversity/faculties_school.aspx").get();

        Elements links = doc.select("a[href]");

        String dept = "";

        for (Element link : links)
        {
            String address = link.attr("href");

            if(address.startsWith("javascript:animatedcollapse.toggle"))
                dept = link.text();

            else if(!link.text().isEmpty())
            {
				if(link.text().contains("Arindam"))
					dept = "School of Management and Entrepreneurship";

				if(!address.startsWith("http:"))
                    address = "http://snu.edu.in" + address;

                if(address.endsWith("profile.aspx") && !(link.text().contains("Director")||link.text().contains("Professor")||link.text().contains("Faculty")||link.text().contains("Fellow")||link.text().contains("Head")))
                {
                    System.out.print("Name: " + link.text());
                    System.out.print("\nLink: " + address);
                    System.out.print("\nDept: " + dept + "\n\n");
                }
            }
        }

    }
}
