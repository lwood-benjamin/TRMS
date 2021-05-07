package com.revature.project_one.models;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class LocalDateSerializer extends StdSerializer<LocalDate>{
	
	//private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	public LocalDateSerializer () {
		this(null);
	}


	public LocalDateSerializer (Class<LocalDate> vc) {
		super(vc);
	}
	


	@Override
	public void serialize(LocalDate value, com.fasterxml.jackson.core.JsonGenerator gen, SerializerProvider provider)
			throws IOException {
		gen.writeString(value.toString());
	}
}
