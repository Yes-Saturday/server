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

    @Transactional(rollbackFor = Exception.class)
    public String next(SequenceName sequenceName) {
        sequenceName = sequenceName == null ? SequenceName.Default : sequenceName;

        var result = sequenceMapper.lockSequenceName(sequenceName.toString());
        if (result == null) {
            sequenceMapper.insert(sequenceName.toString());
            result = sequenceMapper.lockSequenceName(sequenceName.toString());
        }
        sequenceMapper.update(sequenceName.toString());
        return convert(sequenceName.getCode(), result.get("value") + result.get("inc"));
    }

    public final static String convert(String flag, long number) {
        return (flag == null ? "" : flag) + (System.currentTimeMillis() / 1000) + String.format("%05d", number);
    }
}