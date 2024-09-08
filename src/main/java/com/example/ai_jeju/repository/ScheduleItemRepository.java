package com.example.ai_jeju.repository;

import com.example.ai_jeju.domain.Child;
import com.example.ai_jeju.domain.ScheduleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Repository
public interface ScheduleItemRepository extends JpaRepository<ScheduleItem, Long> {

    List<ScheduleItem> findAllByChild(Child child);

}
