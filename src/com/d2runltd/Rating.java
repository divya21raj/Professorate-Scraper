package com.d2runltd;

public class Rating
{
	private Float stars;

	private String review;

	private Course course;

	public Rating(Float stars, String review, Course course)
	{
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
}
