package com.getyourjeepdirty.jeep.models.data;

import com.getyourjeepdirty.jeep.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserDao extends CrudRepository<User, Integer> {

}
