package com.tayfurunal.mentorship.repository.elasticsearch;

import com.tayfurunal.mentorship.domain.Mentor;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentorSearchRepository extends ElasticsearchRepository<Mentor, Long> {

    List<Mentor> findAll();

    List<Mentor> findBySubTopics(String subs);

    List<Mentor> findByThoughtsText(String thoughts);
}
