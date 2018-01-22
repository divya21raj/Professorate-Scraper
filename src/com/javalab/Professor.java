package com.javalab;

import java.util.ArrayList;

public class Professor
{
	private String name;
	private String dept;

	private String link;

	private ArrayList<Rating> ratings;

	public Professor(String name, String dept, String link, ArrayList<Rating> ratings)
	{
		this.name = name;
		this.dept = dept;
		this.link = link;
		this.ratings = ratings;
	}

	public Professor()
	{}

	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Professor && this.name.equals(((Professor) obj).name);
	}

	public String getName()
	{
		return name;
	}

	public String getDept()
	{
		return dept;
	}

	public String getLink()
	{
		return link;
	}

	public ArrayList<Rating> getRatings()
	{
		return ratings;
	}

	public void setRatings(ArrayList<Rating> ratings)
	{
		this.ratings = ratings;
	}
}
