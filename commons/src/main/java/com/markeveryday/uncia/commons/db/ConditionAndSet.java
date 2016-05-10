/*
 * Copyright (c) the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.markeveryday.uncia.commons.db;

/**
 * 与关系的 {@link ConditionSet}
 *
 * @author liming
 */
public class ConditionAndSet extends ConditionSet {

    /**
     * 静态构造方法
     *
     * @param key
     * @param value
     *
     * @return
     */
    public static ConditionAndSet newInstance(String key, Object value) {
        return new ConditionAndSet(key, value);
    }

    /**
     * 静态构造方法
     *
     * @return
     */
    public static ConditionAndSet newInstance() {
        return new ConditionAndSet();
    }

    /**
     * 构造函数
     */
    public ConditionAndSet() {
        super();
    }

    /**
     * 构造函数，构建一个条件
     *
     * @param key
     * @param value
     */
    private ConditionAndSet(String key, Object value) {
        super(key, value);
    }

}
