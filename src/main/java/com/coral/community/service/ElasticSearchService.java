package com.coral.community.service;

import com.coral.community.dao.elasticsearch.DiscussPostRepository;
import com.coral.community.entity.DiscussPost;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ElasticSearchService {
    @Autowired
    private DiscussPostRepository discussPostRepository;

    @Autowired
    private ElasticsearchTemplate template;
    public void saveDiscussPost(DiscussPost post){
        discussPostRepository.save(post);
    }
    public void deleteDiscussPost(int id){
        discussPostRepository.deleteById(id);
    }
    public Page<DiscussPost> searchDiscussPost(String keyword, int current,int limit){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyword,"title","content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(current,limit))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();
        return template.queryForPage(searchQuery, DiscussPost.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
                SearchHits hits = response.getHits();
                if(hits.getTotalHits() <= 0){
                    return null;
                }
                List<DiscussPost> list = new ArrayList<>();
                for(SearchHit hit :hits){
                    DiscussPost post = new DiscussPost();
                    String id = hit.getSourceAsMap().get("id").toString();
                    post.setId(Integer.valueOf(id));
                    String userId = hit.getSourceAsMap().get("userId").toString();
                    post.setUserId(Integer.valueOf(userId));
                    String title = hit.getSourceAsMap().get("title").toString();
                    post.setTitle(title);
                    String content = hit.getSourceAsMap().get("content").toString();
                    post.setContent(content);
                    String status = hit.getSourceAsMap().get("status").toString();
                    post.setStatus(Integer.valueOf(status));
                    String createTime = hit.getSourceAsMap().get("createTime").toString();
                    post.setCreateTime(new Date(Long.valueOf(createTime)));
                    String commentCount = hit.getSourceAsMap().get("commentCount").toString();
                    post.setCommentCount(Integer.valueOf(commentCount));

                    // deal with highlight result
                    HighlightField hightTile = hit.getHighlightFields().get("title");
                    if(hightTile != null){
                        post.setTitle(hightTile.getFragments()[0].toString());
                    }
                    HighlightField highlightContent = hit.getHighlightFields().get("content");
                    if(highlightContent != null){
                        post.setContent(highlightContent.getFragments()[0].toString());
                    }
                    list.add(post);
                }

                return new AggregatedPageImpl(list,pageable,
                        hits.getTotalHits(),response.getAggregations(),response.getScrollId(),
                        hits.getMaxScore());
            }
        });

    }
}
