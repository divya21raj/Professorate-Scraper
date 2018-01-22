package com.d2runltd;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/*The natural science people get counted again after the Communication lot, they are to be eliminated
	while making objects, part of it handled in changedDeptAccordingToExceptions() method*/

public class Main
{
    public static void main(String[] args) throws IOException
    {
        Document doc = Jsoup.connect("http://snu.edu.in/theuniversity/faculties_school.aspx").get();

        Elements links = doc.select("a[href]");

        String dept = "";

        Course dummyCourse = new Course("LOL", "LOLTitle");
        Rating dummyRating = new Rating(0f, "", dummyCourse);
		ArrayList<Rating> dummyRatingList = new ArrayList<>();
		dummyRatingList.add(dummyRating);

		ArrayList<Professor> professorList= new ArrayList<>();

        for (Element link : links)
        {
            String address = link.attr("href");

            if(address.startsWith("javascript:animatedcollapse.toggle"))
                dept = link.text();

            else if(!link.text().isEmpty())
            {
				String exception = changedDeptAccordingToExceptions(link);
				if(exception != null)
					dept = exception;

				if(!address.startsWith("http:"))
                    address = "http://snu.edu.in" + address;

                if(address.endsWith("profile.aspx") && !(link.text().contains("Director")||link.text().contains("Professor")||link.text().contains("Faculty")||link.text().contains("Fellow")||link.text().contains("Head")))
                {
                    Professor professor = new Professor("", link.text(), dept, address, dummyRatingList);

                    professorList.add(professor);
                }
            }
        }

		LinkedHashSet<Professor> linkedHashSet = new LinkedHashSet<>(professorList);
        professorList = new ArrayList<>(linkedHashSet);


    }

	private static String changedDeptAccordingToExceptions(Element link)
	{
		String dept = null;

		if(link.text().equals("Sonali Bhandari"))
			dept = "Department of Natural Sciences";

		if(link.text().equals("Shubhro Sen"))
			dept = "School of Extended Education and Professional Development";

		if(link.text().contains("Arindam"))
			dept = "School of Management and Entrepreneurship";

		return dept;
	}


}
