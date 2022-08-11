package com.nobblecrafts.learn.redis.dbs.mapper;

import com.nobblecrafts.learn.redis.dbs.domain.RedisVote;
import com.nobblecrafts.learn.redis.dbs.domain.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class VoteMapper {

    public static final VoteMapper INSTANCE = Mappers.getMapper(VoteMapper.class);

    public abstract Vote toVote(RedisVote vote);
    public abstract  RedisVote toRedisVote(Vote vote);

}
