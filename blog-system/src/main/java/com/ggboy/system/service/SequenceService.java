package com.ggboy.system.service;

import com.ggboy.system.enums.SequenceName;
import com.ggboy.system.mapper.SequenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class SequenceService {

    @Autowired
    private SequenceMapper sequenceMapper;

    @Transactional
    public Long next(SequenceName sequenceName) {
        if (sequenceName == null)
            sequenceName = SequenceName.Default;

        Map<String, Object> result = sequenceMapper.lockSequenceName(sequenceName.toString());
        if (result == null) {
            sequenceMapper.insert(sequenceName.toString());
            result = sequenceMapper.lockSequenceName(sequenceName.toString());
        }
        sequenceMapper.update(sequenceName.toString());
        return (Long) result.get("value") + (Integer) result.get("inc");
    }
}