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
    public static void main(String[] args) throws IOException
    {
        Document doc = Jsoup.connect("http://snu.edu.in/theuniversity/faculties_school.aspx").get();

        Elements links = doc.select("a[href]");

        String dept = "";

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
                    Professor professor = new Professor("", link.text(), dept, address);

                    professorList.add(professor);
                }
            }
        }

		LinkedHashSet<Professor> linkedHashSet = new LinkedHashSet<>(professorList);
        professorList = new ArrayList<>(linkedHashSet);

        putOnFirebaseDatabase(professorList);

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
