package com.saturday.sequence.service;

import com.saturday.sequence.enums.SequenceName;
import com.saturday.sequence.mapper.SequenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class SequenceService {

    @Autowired
    private SequenceMapper sequenceMapper;

    @Transactional
    public long next(SequenceName sequenceName) {
        if (sequenceName == null)
            sequenceName = SequenceName.Default;

        Map<String, Object> result = sequenceMapper.lockSequenceName(sequenceName.toString());
        if (result == null) {
            sequenceMapper.insert(sequenceName.toString());
            result = sequenceMapper.lockSequenceName(sequenceName.toString());
        }
        sequenceMapper.update(sequenceName.toString());
        return (long) result.get("value") + (int) result.get("inc");
    }
}