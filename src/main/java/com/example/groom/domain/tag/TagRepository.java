package com.example.groom.domain.tag;


import com.example.groom.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long>,TagRepositoryCustom {
}
