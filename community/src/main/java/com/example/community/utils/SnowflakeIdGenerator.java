package com.example.community.utils;
public class SnowflakeIdGenerator {
    // 起始的时间戳 (2024-01-01 00:00:00 UTC+8)
    private final long START_TIMESTAMP = 1672521600000L;

    // 每一部分占用的位数
    private final long SEQUENCE_BIT = 12; // 序列号占用的位数
    private final long MACHINE_BIT = 5;   // 机器标识占用的位数
    private final long DATA_CENTER_BIT = 5;// 数据中心占用的位数

    // 每一部分的最大值
    private final long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);
    private final long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final long MAX_DATA_CENTER_NUM = -1L ^ (-1L << DATA_CENTER_BIT);

    // 每一部分向左的位移
    private final long MACHINE_LEFT = SEQUENCE_BIT;
    private final long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

    private long dataCenterId = (long) 0;  // 数据中心
    private long machineId = (long) 0;     // 机器标识
    private long sequence = 0L; // 序列号
    private long lastTimestamp = -1L;// 上一次时间戳

    public SnowflakeIdGenerator() {

    }

    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - START_TIMESTAMP) << TIMESTAMP_LEFT) |
                (dataCenterId << DATA_CENTER_LEFT) |
                (machineId << MACHINE_LEFT) |
                sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

}

