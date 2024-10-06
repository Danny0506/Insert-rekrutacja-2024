package com.insert.recruitment.error;

import java.time.LocalDateTime;

public record ErrorMessage (String source, LocalDateTime dateTime, String reasonCode, String message) {}
