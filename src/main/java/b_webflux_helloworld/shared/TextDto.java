package b_webflux_helloworld.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class TextDto {

	private String text;

	@JsonCreator
	public TextDto(@JsonProperty("text") final String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final TextDto textDto = (TextDto) o;
		return Objects.equals(text, textDto.text);
	}

	@Override
	public int hashCode() {
		return Objects.hash(text);
	}

	@Override
	public String toString() {
		return "TextDto{" +
				"text='" + text + '\'' +
				'}';
	}
}
