package com.ztesoft.net.cache.redis.jedis;

import static com.ztesoft.net.cache.redis.jedis.Protocol.Keyword.COUNT;
import static com.ztesoft.net.cache.redis.jedis.Protocol.Keyword.MATCH;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.ztesoft.net.cache.redis.util.SafeEncoder;

public class ScanParams {
    private List<byte[]> params = new ArrayList<byte[]>();

    public void match(final String pattern) {
	params.add(MATCH.raw);
	params.add(SafeEncoder.encode(pattern));
    }

    public void count(final int count) {
	params.add(COUNT.raw);
	params.add(Protocol.toByteArray(count));
    }

    public Collection<byte[]> getParams() {
	return Collections.unmodifiableCollection(params);
    }
}
