package com.getyourjeepdirty.jeep.models.data;

import com.getyourjeepdirty.jeep.models.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CommentDao extends CrudRepository<Comment, Integer> {

}
