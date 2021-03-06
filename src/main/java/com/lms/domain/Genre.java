package com.lms.domain;

import java.util.List;

public class Genre {

	private int genre_id;
	private String genre_name;
	private List<Book> book;
	/**
	 * @return the genre_id
	 */
	public int getGenre_id() {
		return genre_id;
	}
	/**
	 * @param genre_id the genre_id to set
	 */
	public void setGenre_id(int genre_id) {
		this.genre_id = genre_id;
	}
	/**
	 * @return the genre_name
	 */
	public String getGenre_name() {
		return genre_name;
	}
	/**
	 * @param genre_name the genre_name to set
	 */
	public void setGenre_name(String genre_name) {
		this.genre_name = genre_name;
	}
	/**
	 * @return the book
	 */
	public List<Book> getBook() {
		return book;
	}
	/**
	 * @param book the book to set
	 */
	public void setBook(List<Book> book) {
		this.book = book;
	}

	
}
