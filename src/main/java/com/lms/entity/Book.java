package com.lms.entity;

import java.util.List;

public class Book {

	private int bookId;
	private String title;
	private int pubId;
	/**
	 * @return the pubId
	 */
	public int getPubId() {
		return pubId;
	}
	/**
	 * @param pubId the pubId to set
	 */
	public void setPubId(int pubId) {
		this.pubId = pubId;
	}
	private Publisher publisher;
	private List<Author> author;
	private List<Genre> genre;
	/**
	 * @return the bookId
	 */
	public int getBookId() {
		return bookId;
	}
	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the publisher
	 */
	public Publisher getPublisher() {
		return publisher;
	}
	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	/**
	 * @return the author
	 */
	public List<Author> getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(List<Author> author) {
		this.author = author;
	}
	/**
	 * @return the genre
	 */
	public List<Genre> getGenre() {
		return genre;
	}
	/**
	 * @param list the genre to set
	 */
	public void setGenre(List<Genre> genre) {
		this.genre = genre;
	}
}
