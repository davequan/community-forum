package com.coral.community.dao.elasticsearch;

import com.coral.community.entity.DiscussPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussPostRepository  extends ElasticsearchRepository<DiscussPost,Integer> {
}
