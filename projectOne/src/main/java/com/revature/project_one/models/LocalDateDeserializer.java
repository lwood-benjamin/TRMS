package com.revature.project_one.models;

import java.io.IOException;
import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class LocalDateDeserializer extends StdDeserializer<LocalDate> {

	public LocalDateDeserializer() {
		this(null);
	}

	public LocalDateDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public LocalDate deserialize(JsonParser jsonparser, DeserializationContext context) {
		String date = null;
		try {
			date = jsonparser.getText();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return LocalDate.parse(date);

	}
}
