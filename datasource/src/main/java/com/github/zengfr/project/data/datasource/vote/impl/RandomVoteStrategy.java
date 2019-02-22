package com.github.zengfr.project.data.datasource.vote.impl;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.github.zengfr.project.data.datasource.vote.VoteStrategy;

public class RandomVoteStrategy implements VoteStrategy {
	@Override
	public String get(List<String> names) {
		return names.get(ThreadLocalRandom.current().nextInt(names.size()));
	}

}
