package com.github.zengfr.project.data.datasource.vote.impl;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.github.zengfr.project.data.datasource.vote.VoteStrategy;

public class LoadBalanceVoteStrategy implements VoteStrategy {
	private AtomicInteger index = new AtomicInteger(0);
	@Override
	public String get(List<String> names) {
		return names.get(Math.abs(index.getAndAdd(1) % names.size()));
	}

}
