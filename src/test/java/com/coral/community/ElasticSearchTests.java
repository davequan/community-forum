package com.coral.community;

import com.coral.community.dao.DiscussPostMapper;
import com.coral.community.dao.elasticsearch.DiscussPostRepository;
import com.coral.community.entity.DiscussPost;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class ElasticSearchTests {
    @Autowired
    private DiscussPostMapper discussPostMapper;
    @Autowired
    private DiscussPostRepository discussPostRepository;

    @Autowired
    private ElasticsearchTemplate template;

    @Test
    public  void testInsert(){
        discussPostRepository.save(discussPostMapper.selectDiscussPostById(241));
        discussPostRepository.save(discussPostMapper.selectDiscussPostById(242));
        discussPostRepository.save(discussPostMapper.selectDiscussPostById(243));
    }

    @Test
    public  void testInsertList(){
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(101,0,100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(102,0,100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(103,0,100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(111,0,100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(112,0,100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(131,0,100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(132,0,100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(133,0,100));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(134,0,100));
    }

    @Test
    public void testUpdate(){
        DiscussPost post = discussPostMapper.selectDiscussPostById(231);
        post.setContent("我是新人");
        discussPostRepository.save(post);
    }
    @Test
    public  void testDelete(){
        discussPostRepository.deleteById(231);
    }
    @Test
    public void testSearch(){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                                    .withQuery(QueryBuilders.multiMatchQuery("offer","title","content"))
                                    .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                                    .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                                    .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                                    .withPageable(PageRequest.of(0,10))
                                    .withHighlightFields(
                                            new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                                            new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                                    ).build();
        Page<DiscussPost> page = discussPostRepository.search(searchQuery);
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        System.out.println(page.getNumber());
        System.out.println(page.getSize());
        for(DiscussPost post : page){
            System.out.println(post);
        }
    }
    @Test
    public  void testSearchByTemplate(){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery("offer","title","content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(0,10))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();
         Page<DiscussPost> page =template.queryForPage(searchQuery, DiscussPost.class, new SearchResultMapper() {
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
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        System.out.println(page.getNumber());
        System.out.println(page.getSize());
        for(DiscussPost post : page){
            System.out.println(post);
        }
    }
}


