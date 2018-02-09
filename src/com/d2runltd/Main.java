package com.d2runltd;

import com.firebase.client.Firebase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/*The natural science people get counted again after the Communication lot, they are to be eliminated
	while making objects, part of it handled in changedDeptAccordingToExceptions() method*/

/*To put stuff on the Firebase DB, its rules were changed so that anyone can r/w on it.
	change that later when using the DB on the Android app*/

public class Main
{
	private static int i =1;

    public static void main(String[] args) throws IOException
    {
        Document doc = Jsoup.connect("http://snu.edu.in/theuniversity/faculties_school.aspx").get();

        Elements links = doc.select("a[href]");

        String dept = "";
        String imageLink;

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

				if(!address.startsWith("http:") && address.endsWith("aspx"))
				{
					address = "http://snu.edu.in" + address;
				}

                if(address.endsWith("profile.aspx") && !(link.text().contains("Director")||link.text().contains("Professor")||link.text().contains("Faculty")||link.text().contains("Fellow")||link.text().contains("Head")))
                {
					String name = link.text();

                	imageLink = getImageLink(address, name);

                    Professor professor = new Professor("", name, dept, address, imageLink);

                    professorList.add(professor);
                }
            }
        }

		LinkedHashSet<Professor> linkedHashSet = new LinkedHashSet<>(professorList);
        professorList = new ArrayList<>(linkedHashSet);

        putOnFirebaseDatabase(professorList);

    }

	private static String getImageLink(String address, String profName)
	{
		String imageLink = "";
		String firstName = getFirstName(profName);

		try
		{
			Document doc = Jsoup.connect(address).get();

			Elements img = doc.getElementsByTag("img");
			for (Element el : img)
			{
				String link = el.absUrl("src");

				if(link.startsWith("http://snu.edu.in/images/" + firstName)||link.startsWith("http://snu.edu.in/images/" + firstName.toLowerCase()))
				{
					imageLink = link;
					System.out.printf("\n%d.%s", i++, imageLink);
				}
			}
		} catch (Exception e)
		{
			System.out.println("Exception!");
			//e.printStackTrace();
		}

		return imageLink;
	}

	private static String getFirstName(String profName)
	{
		String firstName;

		int spacePos = profName.indexOf(" ");

		if(spacePos == -1)
			spacePos = profName.length();

		firstName = profName.substring(0, spacePos);

		return firstName;
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

	private static void putOnFirebaseDatabase(ArrayList<Professor> professors)
	{
		Firebase profDBRef = new Firebase("https://professorate-dc952.firebaseio.com/profs");

		for(Professor professor: professors)
		{
			String id = profDBRef.push().getKey();
			professor.setId(id);

			profDBRef.child(id).setValue(professor);
		}
	}

}
