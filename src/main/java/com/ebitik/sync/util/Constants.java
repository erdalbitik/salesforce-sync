package com.ebitik.sync.util;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

	public static final OffsetDateTime MINIMUM_QUERY_DATE = OffsetDateTime.now().minusYears(25);

	public static final DateTimeFormatter SALESFORCE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

}
