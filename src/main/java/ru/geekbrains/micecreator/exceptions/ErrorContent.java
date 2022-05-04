package ru.geekbrains.micecreator.exceptions;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ErrorContent {

	private List<String> messages;
	private Date timestamp;

	public ErrorContent(String message) {
		this(List.of(message));
	}

	public ErrorContent(String... messages) {
		this(List.of(messages));
	}

	public ErrorContent(List<String> messages) {
		this.messages = new ArrayList<>(messages);
		this.timestamp = new Date();
	}
}
