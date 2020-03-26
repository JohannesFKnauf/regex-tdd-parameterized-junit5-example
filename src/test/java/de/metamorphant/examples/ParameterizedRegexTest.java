package de.metamorphant.examples;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ParameterizedRegexTest {
	private static final String REGEX = "[-+]?\\d+(\\.\\d+)?([eE][-+]?\\d+)?";

	static Stream<Arguments> testCases() {
		return Stream.of(
				Arguments.of("", false, "empty string"),
				Arguments.of("a", false, "single non-digit"),
				Arguments.of("1", true, "single digit"),
				Arguments.of("123", true, "integer"),
				Arguments.of("-123", true, "integer, negative sign"),
				Arguments.of("+123", true, "integer, positive sign"),
				Arguments.of("123.12", true, "float"),
				Arguments.of("123.12e", false, "float with exponent extension but no value"),
				Arguments.of("123.12e12", true, "float with exponent"),
				Arguments.of("123.12E12", true, "float with uppercase exponent"),
				Arguments.of("123.12e12.12", false, "float with non-integer exponent"),
				Arguments.of("123.12e+12", true, "float with exponent, positive sign"),
				Arguments.of("123.12e-12", true, "float with exponent, negative sign")
			);
	}

	@ParameterizedTest(name = "{index} ==> {2}: is {0} well-formed? {1}")
	@MethodSource("testCases")
	public void regexTest(String input, boolean isMatchExpected, String description) {
		Boolean matches = input.matches(REGEX);
		assertEquals(isMatchExpected, matches);
	}
}
