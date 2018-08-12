/*
 * Copyright 2016-2018 shardingsphere.io.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingsphere.transaction.api;

import io.shardingsphere.core.constant.TransactionType;
import io.shardingsphere.transaction.api.local.LocalTransactionManager;
import io.shardingsphere.transaction.api.xa.XATransactionManagerSPILoader;
import io.shardingsphere.transaction.common.TransactionContext;
import io.shardingsphere.transaction.common.TransactionContextHolder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Sharding transaction manager factory.
 *
 * @author zhangliang
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ShardingTransactionManagerFactory {
    
    /**
     * Get sharding transaction manager.
     *
     * @param transactionType transaction type
     * @return sharding transaction manager
     */
    public static ShardingTransactionManager getTransactionManager(final TransactionType transactionType) {
        ShardingTransactionManager result;
        switch (transactionType) {
            case LOCAL:
                result = new LocalTransactionManager();
                break;
            case XA:
                result = XATransactionManagerSPILoader.getInstance().getTransactionManager();
                break;
            case BASE:
            default: 
                return null;
        }
        TransactionContextHolder.set(new TransactionContext(result, transactionType));
        return result;
    }
}