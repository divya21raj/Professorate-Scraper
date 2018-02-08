package com.d2runltd;

public class Rating
{
	private String id;

	private String profId;

	private Float stars;

	private String review;

	private Course course;

	public Rating(String id, String profId, Float stars, String review, Course course)
	{
		this.id = id;
		this.profId = profId;
		this.stars = stars;
		this.review = review;
		this.course = course;
	}

	public Rating()
	{}

	public Float getStars()
	{
		return stars;
	}

	public void setStars(Float stars)
	{
		this.stars = stars;
	}

	public String getReview()
	{
		return review;
	}

	public void setReview(String review)
	{
		this.review = review;
	}

	public Course getCourse()
	{
		return course;
	}

	public void setCourse(Course course)
	{
		this.course = course;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getProfId()
	{
		return profId;
	}

	public void setProfId(String profId)
	{
		this.profId = profId;
	}
}
