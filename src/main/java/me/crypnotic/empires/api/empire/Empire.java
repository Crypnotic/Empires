package me.crypnotic.empires.api.empire;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Empire {

	@Getter
	private final String id;
	@Getter
	private final Territory territory;
}
