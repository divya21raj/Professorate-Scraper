package com.d2runltd;

public class Professor
{
	private String id;

	private String name;
	private String dept;

	private String link;

	Professor(String id, String name, String dept, String link)
	{
		this.id = id;
		this.name = name;
		this.dept = dept;
		this.link = link;
	}

	public Professor()
	{}

	@Override
	public int hashCode()
	{
		final int prime = 31;

		return prime* this.link.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Professor && this.name.equals(((Professor) obj).name);
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
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

}
